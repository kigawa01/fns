package net.kigawa.fns.frontend.service

import kotlinx.coroutines.await
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.kigawa.fns.frontend.util.HttpMethod
import net.kigawa.fns.frontend.util.KutilUrl
import net.kigawa.fns.share.ErrID
import net.kigawa.fns.share.ErrIDException
import net.kigawa.fns.share.ErrResponseException
import net.kigawa.fns.share.json.auth.LoginInfo
import net.kigawa.fns.share.json.auth.Tokens
import net.kigawa.fns.share.json.user.UserInfo
import net.kigawa.fns.share.util.io.DefaultIo
import org.w3c.fetch.RequestInit
import web.url.URL
import web.url.URLSearchParams
import kotlin.js.json

object ApiClient {
  suspend fun login(loginInfo: LoginInfo): Result<Tokens> = fetchJson(
    "/api/auth/login",
    body = loginInfo,
    method = HttpMethod.POST,
  )

  suspend fun register(userInfo: UserInfo): Result<Tokens> = fetchJson(
    "/api/user/register",
    body = userInfo,
    method = HttpMethod.POST,
  )

  suspend fun refresh(refreshToken: String): Result<Tokens> = fetchJson(
    "/api/auth/refresh",
    token = refreshToken,
    method = HttpMethod.POST,
  )

  suspend fun userInfo(): Result<UserInfo> = authedFetchJson(
    "/api/user",
    method = HttpMethod.GET,
  )

  suspend inline fun <reified T : Any?> authedFetchJson(
    url: String,
    method: HttpMethod? = null,
    searchParamsMap: Map<String, String>? = null,
  ) = authedFetchJson<T, Any?>(
    url, method = method, body = null,
    searchParamsMap = searchParamsMap
  )

  suspend inline fun <reified T : Any?> fetchJson(
    url: String,
    method: HttpMethod? = null,
    token: String? = null,
    searchParamsMap: Map<String, String>? = null,
  ) = fetchJson<T>(
    KutilUrl.createURL(url),
    method = method,
    token = token,
    searchParamsMap = searchParamsMap,
  )

  suspend inline fun <reified T : Any?, reified B : Any?> fetchJson(
    url: String,
    method: HttpMethod? = null,
    token: String? = null,
    body: B? = null,
    searchParamsMap: Map<String, String>? = null,
  ) = fetchJson<T, B>(
    KutilUrl.createURL(url),
    method = method,
    token = token,
    body = body,
    searchParamsMap = searchParamsMap
  )

  suspend inline fun <reified T : Any?> fetchJson(
    url: URL,
    method: HttpMethod? = null,
    searchParams: URLSearchParams? = null,
    requestInit: RequestInit = RequestInit(),
    token: String? = null,
    headers: MutableMap<String, String>? = null,
    searchParamsMap: Map<String, String>? = null,
  ) = fetchJson<T, Any?>(
    url, method = method, searchParams = searchParams, requestInit = requestInit, headers = headers, token = token,
    body = null,
    searchParamsMap = searchParamsMap
  )

  suspend inline fun <reified T : Any?, reified B : Any?> authedFetchJson(
    url: String,
    method: HttpMethod? = null,
    body: B? = null,
    searchParamsMap: Map<String, String>? = null,
  ): Result<T> {
    val firstResult = TokenManager.accessToken?.let {
      fetchJson<T, B>(
        url,
        method = method,
        body = body,
        token = it,
        searchParamsMap = searchParamsMap
      )
    }
    if (firstResult?.isSuccess == true) return firstResult
    val err = firstResult?.exceptionOrNull()
    if (err != null) {
      if (err !is ErrResponseException) return firstResult
      if (err.errID != ErrID.ExpiresToken) return firstResult
    }
    val second = TokenManager.refresh()
    val accessToken = second.getOrNull()?.access ?: return Result.failure(second.exceptionOrNull()!!)

    return fetchJson(
      url,
      method = method,
      body = body,
      token = accessToken,
    )
  }

  suspend inline fun <reified T : Any?, reified B : Any?> fetchJson(
    url: URL,
    method: HttpMethod? = null,
    searchParams: URLSearchParams? = null,
    searchParamsMap: Map<String, String>? = null,
    requestInit: RequestInit = RequestInit(),
    headers: Map<String, String>? = null,
    token: String? = null,
    body: B? = null,
  ): Result<T> {
    console.info("fetch", url, searchParams, requestInit)
    searchParams?.forEach { value, key ->
      url.searchParams.append(key, value)
    }
    searchParamsMap?.forEach {
      url.searchParams.append(it.key, it.value)
    }

    requestInit.headers = (headers?.toMutableMap() ?: mutableMapOf()).apply {
      put("Content-Type", "application/json")
      token?.let { put("Authorization", "Bearer ${it.trim()}") }
    }.let { map -> json(*map.entries.map { it.key to it.value }.toTypedArray()) }
    requestInit.method = method?.name ?: requestInit.method ?: HttpMethod.GET.name
    console.info(body, body?.let { it::class })
    requestInit.body = body?.let { Json.encodeToString(it) } ?: requestInit.body

    val res = kotlinx.browser.window.fetch(url, requestInit).await()
    console.info(res, url, requestInit)
    val text = res.text().await()
    try {
      if (res.ok) {
        return Result.success(Json.decodeFromString<T>(text))
      }
      return Result.failure(ErrResponseException(Json.decodeFromString(text)))
    } catch (e: SerializationException) {
      DefaultIo.warn.writeLine("failed to deserialize response")
      DefaultIo.warn.writeLine(text)
      DefaultIo.warn.writeLine(e.message ?: e.toString())
      return Result.failure(ErrIDException(ErrID.DeserializeFailed))
    } catch (e: Exception) {
      return Result.failure(e)
    }
  }
}
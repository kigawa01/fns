package net.kigawa.fns.frontend.service

import kotlinx.coroutines.await
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.kigawa.fns.frontend.util.HttpMethod
import net.kigawa.fns.frontend.util.KutilUrl
import net.kigawa.fns.share.ErrID
import net.kigawa.fns.share.ErrResponseException
import net.kigawa.fns.share.json.auth.LoginInfo
import net.kigawa.fns.share.json.auth.Tokens
import net.kigawa.fns.share.json.user.UserInfo
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
  ) = authedFetchJson<T, Any?>(
    url, method = method, body = null
  )

  private suspend inline fun <reified T : Any?> fetchJson(
    url: String,
    method: HttpMethod? = null,
    token: String? = null,
  ) = fetchJson<T>(
    KutilUrl.createURL(url),
    method = method,
    token = token,
  )

  suspend inline fun <reified T : Any?, reified B : Any?> fetchJson(
    url: String,
    method: HttpMethod? = null,
    token: String? = null,
    body: B? = null,
  ) = fetchJson<T, B>(
    KutilUrl.createURL(url),
    method = method,
    token = token,
    body = body,
  )

  private suspend inline fun <reified T : Any?> fetchJson(
    url: URL,
    method: HttpMethod? = null,
    searchParams: URLSearchParams? = null,
    requestInit: RequestInit = RequestInit(),
    token: String? = null,
    headers: MutableMap<String, String>? = null,
  ) = fetchJson<T, Any?>(
    url, method = method, searchParams = searchParams, requestInit = requestInit, headers = headers, token = token,
    body = null
  )

  suspend inline fun <reified T : Any?, reified B : Any?> authedFetchJson(
    url: String,
    method: HttpMethod? = null,
    body: B? = null,
  ): Result<T> {
    val firstResult = TokenManager.accessToken?.let {
      fetchJson<T, B>(
        url,
        method = method,
        body = body,
        token = it,
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
    requestInit: RequestInit = RequestInit(),
    headers: Map<String, String>? = null,
    token: String? = null,
    body: B? = null,
  ): Result<T> {
    console.info("fetch", url, searchParams, requestInit)
    searchParams?.forEach { value, key ->
      url.searchParams.append(key, value)
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
    } catch (e: Exception) {
      console.info("text", text)
      e.printStackTrace()
      return Result.failure(e)
    }
  }
}
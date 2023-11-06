package net.kigawa.fns.frontend.service

import kotlinx.coroutines.await
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.kigawa.fns.frontend.util.HttpMethod
import net.kigawa.fns.frontend.util.UrlUtil
import net.kigawa.fns.share.ErrResponseException
import net.kigawa.fns.share.json.auth.LoginInfo
import net.kigawa.fns.share.json.auth.Tokens
import org.w3c.fetch.RequestInit
import web.url.URL
import web.url.URLSearchParams
import kotlin.js.json

object ApiClient {
  suspend fun login(loginInfo: LoginInfo): Result<Tokens> {
    return fetchJson(
      UrlUtil.createURL("/api/auth/login"),
      body = loginInfo,
      method = HttpMethod.POST
    )
  }

  private suspend inline fun <reified T : Any?, reified B : Any?> fetchJson(
    url: URL,
    method: HttpMethod? = null,
    body: B? = null,
    searchParams: URLSearchParams? = null,
    requestInit: RequestInit = RequestInit(),
  ): Result<T> {
    console.info("fetch", url, searchParams, requestInit)
    searchParams?.forEach { value, key ->
      url.searchParams.append(key, value)
    }

    requestInit.headers = requestInit.headers ?: json(
      "Content-Type" to "application/json"
    )
    requestInit.method = method?.name
      ?: requestInit.method
          ?: HttpMethod.GET.name
    requestInit.body = body?.let { Json.encodeToString(it) }
      ?: requestInit.body

    val res = kotlinx.browser.window.fetch(url, requestInit).await()
    console.info(res, url, requestInit)
    try {
      if (res.ok) {
        return Result.success(Json.decodeFromString<T>(res.text().await()))
      }
      return Result.failure(ErrResponseException(Json.decodeFromString(res.text().await())))
    } catch (e: Exception) {
      e.printStackTrace()
      return Result.failure(e)
    }
  }
}
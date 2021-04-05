package io.unthrottled.themed.components.integrations

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.util.io.HttpRequests
import io.unthrottled.themed.components.integrations.RestTools.performRequest
import io.unthrottled.themed.components.util.readAllTheBytes
import io.unthrottled.themed.components.util.toOptional
import java.io.InputStream
import java.util.Optional
import java.util.concurrent.Callable

object RestClient {

  fun performGet(url: String): Optional<String> =
    ApplicationManager.getApplication().executeOnPooledThread(
      Callable {
        performRequest(url) { responseBody ->
          String(responseBody.readAllTheBytes())
        }
      }
    ).get()
}

object RestTools {
  private val log = Logger.getInstance(this::class.java)

  fun <T> performRequest(
    url: String,
    bodyExtractor: (InputStream) -> T
  ): Optional<T> {
    log.info("Attempting to download remote asset: $url")
    return HttpRequests.request(url)
      .connect { request ->
        try {
          val body = bodyExtractor(request.inputStream)
          log.info("Asset has responded for remote asset: $url")
          body.toOptional()
        } catch (e: HttpRequests.HttpStatusException) {
          log.warn("Unable to get remote asset: $url for raisins", e)
          Optional.empty<T>()
        }
      }
  }
}

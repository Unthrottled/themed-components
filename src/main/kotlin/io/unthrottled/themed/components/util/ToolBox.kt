package io.unthrottled.themed.components.util

import com.intellij.ui.ColorUtil
import java.awt.Color
import java.util.Optional
import java.util.concurrent.Callable
import java.util.stream.Stream

fun <T> getSafely(callable: Callable<T>): Optional<T> =
  try {
    callable.call().toOptional()
  } catch (e: Throwable) {
    Optional.empty()
  }

fun runSafely(
  runner: () -> Unit,
  onError: (Throwable) -> Unit = {}
): Unit =
  try {
    runner()
  } catch (e: Throwable) {
    onError(e)
  }

fun <T> T?.toOptional() = Optional.ofNullable(this)

fun <T> T?.toStream(): Stream<T> = Stream.of(this)

fun Color.toHexString() = "#${ColorUtil.toHex(this)}"

fun String.toColor() = ColorUtil.fromHex(this)

package io.unthrottled.themed.components.util

import com.intellij.ui.ColorUtil
import org.apache.commons.io.IOUtils
import java.awt.Color
import java.io.InputStream
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

object ToolBox {
  @JvmStatic
  fun toColor(colorString: String?): Optional<Color> = colorString.toOptional()
    .flatMap { it.toColor() }
  @JvmStatic
  fun toHex(color: Color): String = color.toHexString()
}

fun <T> T?.toOptional() = Optional.ofNullable(this)

fun <T> T?.toStream(): Stream<T> = Stream.of(this)

fun Color.toHexString() = "#${ColorUtil.toHex(this)}"

fun String.toColor(): Optional<Color> =
  getSafely { ColorUtil.fromHex(this) }

fun InputStream.readAllTheBytes(): ByteArray = IOUtils.toByteArray(this)

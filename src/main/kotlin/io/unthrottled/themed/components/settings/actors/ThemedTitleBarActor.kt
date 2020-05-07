package io.unthrottled.themed.components.settings.actors

import io.unthrottled.themed.components.notification.UpdateNotification
import io.unthrottled.themed.components.settings.Configurations

object ThemedTitleBarActor {
  fun enableThemedTitleBar(enabled: Boolean) {
    if (enabled != Configurations.instance.isThemedTitleBar) {
      Configurations.instance.isThemedTitleBar = enabled
      UpdateNotification.displayRestartMessage()
    }
  }
}
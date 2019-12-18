package io.acari.themed.components.settings.actors

import io.acari.themed.components.notification.UpdateNotification
import io.acari.themed.components.settings.Configurations

object ThemedTitleBarActor {
  fun enableThemedTitleBar(enabled: Boolean) {
    if (enabled != Configurations.instance.isThemedTitleBar) {
      Configurations.instance.isThemedTitleBar = enabled
      UpdateNotification.displayRestartMessage()
    }
  }
}
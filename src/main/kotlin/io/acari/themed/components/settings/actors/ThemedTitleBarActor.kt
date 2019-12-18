package io.acari.themed.components.settings.actors

import io.acari.doki.config.ThemeConfig
import io.acari.doki.notification.UpdateNotification

object ThemedTitleBarActor {
  fun enableThemedTitleBar(enabled: Boolean) {
    if (enabled != ThemeConfig.instance.isThemedTitleBar) {
      ThemeConfig.instance.isThemedTitleBar = enabled
      UpdateNotification.displayRestartMessage()
    }
  }
}
package io.unthrottled.themed.components.notification

import com.intellij.notification.*
import com.intellij.openapi.project.Project

val UPDATE_MESSAGE: String = """
      What's New?<br>
      <ul>
      <li>2020.2 Build Support.</li>
      </ul>
      <br>Please see the <a href="https://github.com/Unthrottled/themed-components/blob/master/changelog/CHANGELOG.md">Changelog</a> for more details.
      <br>
      Thanks again for downloading <b>Themed Components</b>! •‿•<br>
""".trimIndent()

object UpdateNotification {

  private val notificationManager by lazy {
    SingletonNotificationManager(
      NotificationGroup("Themed Components Updates",
      NotificationDisplayType.STICKY_BALLOON, true),
      NotificationType.INFORMATION)
  }

  fun display(project: Project, currentVersion: String) {
    notificationManager.notify(
      "Themed Components updated to v$currentVersion",
      UPDATE_MESSAGE,
      project,
      NotificationListener.URL_OPENING_LISTENER
    )
  }

  fun displayRestartMessage() {
    notificationManager.notify(
      "Please restart your IDE",
      "In order for the change to take effect, please restart your IDE. Thanks! ~"
    )
  }

    fun displayAnimationInstallMessage() {
      notificationManager.notify(
        "Theme Transition Animation Enabled",
        """The animations will remain in your IDE after uninstalling the plugin.
          |To remove them, un-check this action or remove them at "Help -> Find Action -> ide.intellij.laf.enable.animation". 
        """.trimMargin()
      )
    }
}
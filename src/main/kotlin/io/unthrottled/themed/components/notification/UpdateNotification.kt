package io.unthrottled.themed.components.notification

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationListener
import com.intellij.notification.NotificationType
import com.intellij.notification.SingletonNotificationManager
import com.intellij.openapi.project.Project
import org.intellij.lang.annotations.Language

@Language("HTML")
val UPDATE_MESSAGE: String =
  """
      What's New?<br>
      <ul>
      <li>2021.3 Build Support</li>
      </ul>
      <br>Please see the <a href="https://github.com/Unthrottled/themed-components/blob/master/changelog/CHANGELOG.md">Changelog</a> for more details.
      <br>
      Thanks again for downloading <b>Themed Components</b>! •‿•<br>
  """.trimIndent()

object UpdateNotification {

  private val notificationManager by lazy {
    SingletonNotificationManager(
      NotificationGroupManager.getInstance().getNotificationGroup("Themed Components Updates"),
      NotificationType.INFORMATION
    )
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
}

package io.unthrottled.themed.components.notification

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationListener
import com.intellij.notification.NotificationType
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

  private val notificationGroup =
    NotificationGroupManager.getInstance()
      .getNotificationGroup("Themed Components Updates")

  fun display(project: Project, currentVersion: String) {
    val notification = notificationGroup.createNotification(
      UPDATE_MESSAGE,
      NotificationType.INFORMATION,
    ).setTitle("Themed Components updated to v$currentVersion")
      .setListener(NotificationListener.URL_OPENING_LISTENER)
    notification.notify(project)
  }

  fun displayRestartMessage() {
    notificationGroup.createNotification(
      "In order for the change to take effect, please restart your IDE. Thanks! ~",
      NotificationType.INFORMATION,
    ).setTitle(
      "Please restart your IDE",
    ).notify(null)
  }
}

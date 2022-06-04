package io.unthrottled.themed.components.legacy

import com.intellij.openapi.application.ex.ApplicationInfoEx
import com.intellij.openapi.util.BuildNumber
import com.intellij.openapi.util.SystemInfo
import io.unthrottled.themed.components.settings.Configurations

object LegacyMigration {
  fun migrateIfNecessary() {
    migrateUsersAwayFromTitlePane()
  }

  private val nativeTitlePaneBuild = BuildNumber.fromString("222.2680.4")
  private fun migrateUsersAwayFromTitlePane() {
    val build = ApplicationInfoEx.getInstanceEx().build
    if (SystemInfo.isMac &&
      Configurations.instance.isThemedTitleBar &&
      // is the current build greater that the
      // build that has native titlepane support
      (nativeTitlePaneBuild?.compareTo(build) ?: 0) <= 0
    ) {
      Configurations.instance.isThemedTitleBar = false
    }
  }
}

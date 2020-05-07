package io.unthrottled.themed.components.settings

import com.intellij.ide.BrowserUtil.browse
import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.layout.panel
import io.unthrottled.themed.components.settings.actors.ThemedTitleBarActor
import java.net.URI
import javax.swing.JComponent

data class SettingsModel(
  var isThemedTitleBar: Boolean
)

class ThemeSettings : SearchableConfigurable {

  companion object {
    const val THEME_SETTINGS_DISPLAY_NAME = "Themed Components Settings"
    val CHANGELOG_URI =
      URI("https://github.com/cyclic-reference/themed-components/blob/master/changelog/CHANGELOG.md")
    val ISSUES_URI = URI("https://github.com/cyclic-reference/themed-components/issues")
    // todo: restore this.
    val MARKETPLACE_URI = URI("https://https://github.com/cyclic-reference/themed-components/issues")
  }

  override fun getId(): String = "io.unthrottled.themed.components.settings.Settings"

  override fun getDisplayName(): String =
    THEME_SETTINGS_DISPLAY_NAME

  private val initialSettingsModel = SettingsModel(
    Configurations.instance.isThemedTitleBar
  )

  private val themeSettingsModel = initialSettingsModel.copy()

  override fun isModified(): Boolean {
    return initialSettingsModel != themeSettingsModel
  }

  override fun apply() {
    ThemedTitleBarActor.enableThemedTitleBar(themeSettingsModel.isThemedTitleBar)
  }

  override fun createComponent(): JComponent? = createSettingsPane()

  private fun createSettingsPane(): DialogPanel =
    panel {
    titledRow("Main Settings") {
      row {
        checkBox(
          "Themed Title Bar",
          themeSettingsModel.isThemedTitleBar,
          comment = "Feature only works on MacOS",
          actionListener = { _, component ->
            themeSettingsModel.isThemedTitleBar = component.isSelected
          }
        )
      }
    }
    titledRow("Miscellaneous Items") {
      row {
        cell {
          button("View Issues") {
            browse(ISSUES_URI)
          }
          button("View Changelog") {
            browse(CHANGELOG_URI)
          }
//          button("Marketplace Homepage") {
//            browse(MARKETPLACE_URI)
//          }
        }
      }
    }
  }
}
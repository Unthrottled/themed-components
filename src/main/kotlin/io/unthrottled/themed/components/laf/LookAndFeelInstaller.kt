package io.unthrottled.themed.components.laf

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.wm.impl.IdeBackgroundUtil
import io.unthrottled.themed.components.legacy.LegacyMigration
import io.unthrottled.themed.components.settings.Configurations
import io.unthrottled.themed.components.ui.TitlePaneUI
import io.unthrottled.themed.components.util.Constants.COMPLETION_SELECTION_ACTIVE
import io.unthrottled.themed.components.util.Constants.COMPLETION_SELECTION_INACTIVE
import io.unthrottled.themed.components.util.Constants.TITLE_PANE_INACTIVE_PROP
import io.unthrottled.themed.components.util.Constants.TITLE_PANE_PROP
import io.unthrottled.themed.components.util.toColor
import javax.swing.UIManager

object LookAndFeelInstaller {
  init {
    LegacyMigration.migrateIfNecessary()
    installAllUIComponents()
  }

  fun installAllUIComponents() {
    installTitlePane()
    installCustomColors()
  }

  private fun installCustomColors() {
    if (Configurations.instance.isCustomColors.not()) return

    ApplicationManager.getApplication().invokeLater {
      val lookAndFeelDefaults = UIManager.getLookAndFeelDefaults()
      Configurations.instance.titleForegroundColor.toColor()
        .ifPresent {
          lookAndFeelDefaults.remove(TITLE_PANE_PROP)
          lookAndFeelDefaults[TITLE_PANE_PROP] = it
        }
      Configurations.instance.titleInactiveForegroundColor.toColor()
        .ifPresent {
          lookAndFeelDefaults.remove(TITLE_PANE_INACTIVE_PROP)
          lookAndFeelDefaults[TITLE_PANE_INACTIVE_PROP] = it
        }

      val colors = Configurations.getCustomColors()
      listOf(
        COMPLETION_SELECTION_INACTIVE,
        COMPLETION_SELECTION_ACTIVE,
      )
        .filter { colors.containsKey(it) }
        .forEach {
          colors[it]?.toColor()?.ifPresent { color ->
            lookAndFeelDefaults.remove(it)
            lookAndFeelDefaults[it] = color
          }
        }

      IdeBackgroundUtil.repaintAllWindows()
    }
  }

  private fun installTitlePane() {
    if (Configurations.instance.isThemedTitleBar.not()) return

    val defaults = UIManager.getLookAndFeelDefaults()
    defaults["RootPaneUI"] = TitlePaneUI::class.java.name
    defaults[TitlePaneUI::class.java.name] = TitlePaneUI::class.java
  }
}

package io.unthrottled.themed.components.laf

import io.unthrottled.themed.components.settings.Configurations
import io.unthrottled.themed.components.ui.TitlePaneUI
import io.unthrottled.themed.components.util.Constants
import io.unthrottled.themed.components.util.toColor
import javax.swing.UIManager

object LookAndFeelInstaller {
  init {
    installAllUIComponents()
  }

  fun installAllUIComponents() {
    installTitlePane()
    installCustomColors()
  }

  private fun installCustomColors() {
    if(Configurations.instance.isCustomColors.not()) return

    val lookAndFeelDefaults = UIManager.getLookAndFeelDefaults()
    Configurations.instance.titleForegroundColor.toColor()
      .ifPresent { lookAndFeelDefaults[Constants.TITLE_PANE_PROP] = it }
    Configurations.instance.titleInactiveForegroundColor.toColor()
      .ifPresent { lookAndFeelDefaults[Constants.TITLE_PANE_INACTIVE_PROP] = it }

  }

  private fun installTitlePane() {
    if(Configurations.instance.isThemedTitleBar.not()) return

    val defaults = UIManager.getLookAndFeelDefaults()
    defaults["RootPaneUI"] = TitlePaneUI::class.java.name
    defaults[TitlePaneUI::class.java.name] = TitlePaneUI::class.java
  }
}

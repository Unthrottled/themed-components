package io.unthrottled.themed.components.laf

import io.unthrottled.themed.components.ui.TitlePaneUI
import javax.swing.UIManager

object LookAndFeelInstaller {
  init {
    installAllUIComponents()
  }

  fun installAllUIComponents() {
    installTitlePane()
  }

  private fun installTitlePane() {
    val defaults = UIManager.getLookAndFeelDefaults()
    defaults["RootPaneUI"] = TitlePaneUI::class.java.name
    defaults[TitlePaneUI::class.java.name] = TitlePaneUI::class.java
  }
}

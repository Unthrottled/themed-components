package io.unthrottled.themed.components.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import io.unthrottled.themed.components.settings.Configurations
import io.unthrottled.themed.components.settings.actors.ThemedTitleBarActor

class ThemedTitleBarAction : BaseToggleAction() {
  override fun isSelected(e: AnActionEvent): Boolean =
    Configurations.instance.isThemedTitleBar

  override fun setSelected(e: AnActionEvent, state: Boolean) {
    ThemedTitleBarActor.enableThemedTitleBar(state)
  }
}
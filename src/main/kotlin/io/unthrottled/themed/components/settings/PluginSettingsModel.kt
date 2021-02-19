package io.unthrottled.themed.components.settings

data class PluginSettingsModel(
  var titleForegroundColor: String,
  var titleInactiveForegroundColor: String,
  var isCustomColors: Boolean,
) {
  fun duplicate(): PluginSettingsModel = copy()
}

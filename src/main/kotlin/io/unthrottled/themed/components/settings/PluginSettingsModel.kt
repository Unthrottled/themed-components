package io.unthrottled.themed.components.settings

data class PluginSettingsModel(
  var titleForegroundColor: String,
  var titleInactiveForegroundColor: String,
  var isCustomColors: Boolean,
  var isThemedTitleBar: Boolean,
  var customColoring: MutableMap<String, String>,
) {
  fun duplicate(): PluginSettingsModel = copy(customColoring = HashMap(customColoring))
}

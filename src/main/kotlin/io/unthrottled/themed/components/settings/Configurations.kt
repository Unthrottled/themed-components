package io.unthrottled.themed.components.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
  name = "ThemedComponentsConfig",
  storages = [Storage("themed_components.xml")]
)
class Configurations : PersistentStateComponent<Configurations>, Cloneable {
  companion object {
    @JvmStatic
    val instance: Configurations
      get() = ServiceManager.getService(Configurations::class.java)

    @JvmStatic
    fun getInitialSettings(): PluginSettingsModel =
      PluginSettingsModel(
        titleForegroundColor = instance.titleForegroundColor,
        titleInactiveForegroundColor = instance.titleInactiveForegroundColor,
        isCustomColors = instance.isCustomColors,
        isThemedTitleBar = instance.isThemedTitleBar,
      )
  }

  var isThemedTitleBar: Boolean = true
  var isCustomColors: Boolean = false
  var titleForegroundColor: String = ""
  var titleInactiveForegroundColor: String = ""
  var version: String = "0.0.0"
  var userId: String = ""

  override fun getState(): Configurations? =
    XmlSerializerUtil.createCopy(this)

  override fun loadState(state: Configurations) {
    XmlSerializerUtil.copyBean(state, this)
  }
}

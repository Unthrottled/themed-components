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
        val instance: Configurations
            get() = ServiceManager.getService(Configurations::class.java)
    }

    var isThemedTitleBar: Boolean = true
    var version: String = "0.0.0"

    override fun getState(): Configurations? =
        XmlSerializerUtil.createCopy(this)

    override fun loadState(state: Configurations) {
        XmlSerializerUtil.copyBean(state, this)
    }

    fun asJson(): Map<String, Any> = mapOf(
        "isThemedTitleBar" to isThemedTitleBar
    )
}
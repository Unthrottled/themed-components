package io.unthrottled.themed.components

import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.ide.ui.LafManagerListener
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.project.ProjectManagerListener
import io.unthrottled.themed.components.notification.UpdateNotification
import io.unthrottled.themed.components.laf.LookAndFeelInstaller.installAllUIComponents
import io.unthrottled.themed.components.settings.Configurations
import io.unthrottled.themed.components.util.toOptional
import java.util.*

class ThemedComponents : Disposable {
    private val connection = ApplicationManager.getApplication().messageBus.connect()

    companion object {
      private const val PLUGIN_ID = "io.unthrottled.themed-components"
    }
    init {
        installAllUIComponents()

        connection.subscribe(LafManagerListener.TOPIC, LafManagerListener {
            installAllUIComponents()
        })
        connection.subscribe(ProjectManager.TOPIC, object : ProjectManagerListener {
            override fun projectOpened(project: Project) {
              getVersion()
                .filter { it != Configurations.instance.version }
                .ifPresent {
                  Configurations.instance.version = it
                  UpdateNotification.display(project, it)
                }
            }
        })
    }

  private fun getVersion(): Optional<String> =
    PluginManagerCore.getPlugin(PluginId.getId(PLUGIN_ID)).toOptional()
      .map { it.version }

  override fun dispose() {
        connection.dispose()
    }
}
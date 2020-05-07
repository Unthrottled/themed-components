package io.unthrottled.themed.components

import com.intellij.ide.ui.LafManagerListener
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.impl.ProjectLifecycleListener
import io.unthrottled.themed.components.notification.CURRENT_VERSION
import io.unthrottled.themed.components.notification.UpdateNotification
import io.unthrottled.themed.components.laf.LookAndFeelInstaller.installAllUIComponents
import io.unthrottled.themed.components.settings.Configurations

class ThemedComponents : Disposable {
    private val connection = ApplicationManager.getApplication().messageBus.connect()

    init {
        installAllUIComponents()

        connection.subscribe(LafManagerListener.TOPIC, LafManagerListener {
            installAllUIComponents()
        })
        connection.subscribe(ProjectLifecycleListener.TOPIC, object : ProjectLifecycleListener {
            override fun projectComponentsInitialized(project: Project) {
                if (Configurations.instance.version != CURRENT_VERSION) {
                    Configurations.instance.version =
                        CURRENT_VERSION
                    UpdateNotification.display(project)
                }
            }
        })
    }

    override fun dispose() {
        connection.dispose()
    }
}
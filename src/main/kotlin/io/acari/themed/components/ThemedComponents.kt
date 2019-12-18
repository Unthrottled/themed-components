package io.acari.themed.components

import com.intellij.ide.ui.LafManagerListener
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import io.acari.themed.components.laf.LookAndFeelInstaller.installAllUIComponents

class ThemedComponents : Disposable {
    private val connection = ApplicationManager.getApplication().messageBus.connect()

    init {
        connection.subscribe(LafManagerListener.TOPIC, LafManagerListener {
            installAllUIComponents()
        })

    }

    override fun dispose() {
        connection.dispose()
    }
}
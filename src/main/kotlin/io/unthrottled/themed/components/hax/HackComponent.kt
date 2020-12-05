package io.unthrottled.themed.components.hax

import com.intellij.openapi.diagnostic.Logger
import com.intellij.ui.messages.JBMacMessages
import io.unthrottled.themed.components.ui.TitlePaneUI.Companion.LOL_NOPE
import io.unthrottled.themed.components.util.runSafely
import javassist.ClassClassPath
import javassist.ClassPool
import javassist.expr.ExprEditor
import javassist.expr.NewExpr
import javax.swing.JDialog

object HackComponent {
  private val log = Logger.getInstance(javaClass)

  init {
    enableTitlePaneConsistency()
  }


  private fun enableTitlePaneConsistency() {
    hackSheetMessage()
  }

  private fun hackSheetMessage() {
    runSafely({
      val cp = ClassPool(true)
      cp.insertClassPath(ClassClassPath(JBMacMessages::class.java))
      val ctClass = cp.get("com.intellij.ui.messages.SheetMessage")
      ctClass.declaredConstructors
        .forEach { constructorDude ->
          constructorDude.instrument(
            object : ExprEditor() {
              override fun edit(e: NewExpr?) {
                if (e?.className == JDialog::class.java.name) {
                  e?.replace("{ \$2 = \"$LOL_NOPE\"; \$_ = \$proceed(\$\$); }")
                }
              }
            }
          )
        }
      ctClass.toClass()
    }) {
      log.warn("Unable to hackSheetMessage for reasons.")
    }
  }
}

package org.dochub.idea.arch.markline

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProvider
import com.intellij.icons.AllIcons
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiElement
import org.apache.xerces.impl.dv.util.Base64
import org.dochub.idea.arch.utils.YAMLUtil
import org.jetbrains.yaml.psi.YAMLKeyValue
import org.jetbrains.yaml.psi.YAMLScalar

class InjectedJSONataLineMarkerProvider: LineMarkerProvider {
    private val injectedJSONataRegex = "[(][^+]*[)]\\s*".toRegex()
    override fun getLineMarkerInfo(element: PsiElement) =
        (element as? YAMLScalar)?.run {
            if (element.textValue.matches(injectedJSONataRegex))
                LineMarkerInfo(
                    element.firstChild,
                    element.textRange,
                    AllIcons.Actions.Execute,
                    { "Выполнить" },
                    { _, elt ->
                        if ((elt.parent.parent as? YAMLKeyValue)?.keyText == "source") {
                            (elt.parent.parent.parent.parent as? YAMLKeyValue)?.run {
                                elt.project
                                    .messageBus
                                    .syncPublisher(LineMarkerNavigator.ON_NAVIGATE_MESSAGE)
                                    .go("devtool", "source:${YAMLUtil.getConfigFullName(this)}")
                            }
                        } else {
                            elt.project
                                .messageBus
                                .syncPublisher(LineMarkerNavigator.ON_NAVIGATE_MESSAGE)
                                .go("devtool", "element:${Base64.encode(element.textValue.toByteArray())}")
                        }
                    },
                    GutterIconRenderer.Alignment.LEFT
                ) { "DocHub" }
            else null
        }
}
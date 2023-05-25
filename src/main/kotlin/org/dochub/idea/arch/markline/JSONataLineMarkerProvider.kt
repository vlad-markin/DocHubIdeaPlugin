package org.dochub.idea.arch.markline

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProvider
import com.intellij.icons.AllIcons
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiElement
import org.apache.xerces.impl.dv.util.Base64
import org.dochub.idea.arch.jsonata.JSONataFile

class JSONataLineMarkerProvider: LineMarkerProvider {
    override fun getLineMarkerInfo(element: PsiElement) =
        (element as? JSONataFile)?.run {
            LineMarkerInfo(
                element,
                element.textRange,
                AllIcons.Actions.Execute,
                { "Выполнить" },
                { _ , elt -> elt.project
                    .messageBus
                    .syncPublisher(LineMarkerNavigator.ON_NAVIGATE_MESSAGE)
                    .go("devtool", "file:${Base64.encode(elt.text.toByteArray())}")
                },
                GutterIconRenderer.Alignment.LEFT
            ) { "DocHub" }
        }

}
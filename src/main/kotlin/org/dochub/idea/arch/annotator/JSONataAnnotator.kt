package org.dochub.idea.arch.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.psi.PsiElement
import org.intellij.sdk.language.psi.JSONataCall
import org.intellij.sdk.language.psi.JSONataDeep
import org.intellij.sdk.language.psi.JSONataSysFunction

class JSONataAnnotator: Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {

        val isJsonataFunction = element is JSONataSysFunction
        val isCallFunction = element is JSONataCall
        val firstChild = element.firstChild

        if (isJsonataFunction || isCallFunction) {
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(firstChild)
                .textAttributes(DefaultLanguageHighlighterColors.FUNCTION_DECLARATION)
                .create()
        }
    }
}
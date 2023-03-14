package org.dochub.idea.arch.completion

import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.patterns.PsiElementPattern
import com.intellij.psi.PsiElement
import org.intellij.sdk.language.psi.JSONataArguments
import org.intellij.sdk.language.psi.JSONataDeclFunction


object JSONataPatterns {

    const val ASSIGN = ":="

    object ExpressionPatterns {
        fun inPattern(): PsiElementPattern.Capture<PsiElement> {
            return psiElement().andOr(
                psiElement().atStartOf(psiElement(JSONataArguments::class.java)))
        }

        fun orPattern(): PsiElementPattern.Capture<PsiElement> {
            return psiElement().andOr(
                psiElement().atStartOf(psiElement(JSONataArguments::class.java)))
        }

        fun functionPattern(): PsiElementPattern.Capture<PsiElement> {
            return psiElement().andOr(
                psiElement().atStartOf(psiElement(JSONataDeclFunction::class.java)),
                psiElement().afterLeaf(ASSIGN))
        }


        fun andPattern(): PsiElementPattern.Capture<PsiElement> {
            return psiElement().andOr(
                psiElement().atStartOf(psiElement(JSONataArguments::class.java)))
        }
    }

    object DeclPatterns {
        fun declPatter(): PsiElementPattern.Capture<PsiElement> {
            return psiElement().andOr(
                psiElement().atStartOf(psiElement())
            )
        }
    }
}
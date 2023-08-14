package org.dochub.idea.arch.completion

import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.patterns.PsiElementPattern
import com.intellij.psi.PsiElement
import org.intellij.sdk.language.psi.JSONataDefFunctionArguments


object JSONataPatterns {


    object ExpressionPatterns {
        fun inPattern(): PsiElementPattern.Capture<PsiElement> {
            return psiElement()
        }

        fun orPattern(): PsiElementPattern.Capture<PsiElement> {
            return psiElement()
        }

        fun functionPattern(): PsiElementPattern.Capture<PsiElement> {
            return psiElement()
        }


        fun andPattern(): PsiElementPattern.Capture<PsiElement> {
            return psiElement().andOr(
                psiElement().atStartOf(psiElement(JSONataDefFunctionArguments::class.java)))
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

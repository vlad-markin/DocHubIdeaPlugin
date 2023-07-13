package org.dochub.idea.arch.completion

import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.patterns.PsiElementPattern
import com.intellij.psi.PsiElement


object JSONataPatterns {

    const val ASSIGN = ":="

    object ExpressionPatterns {

    }

    object DeclPatterns {
        fun declPatter(): PsiElementPattern.Capture<PsiElement> {
            return psiElement().andOr(
                psiElement().atStartOf(psiElement())
            )
        }
    }
}
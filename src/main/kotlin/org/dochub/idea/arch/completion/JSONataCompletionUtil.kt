package org.dochub.idea.arch.completion

import com.intellij.codeInsight.completion.CompletionUtilCore
import com.intellij.psi.PsiElement

object JSONataCompletionUtil {

    fun shouldComplete(elemetn: PsiElement): Boolean = elemetn.text != CompletionUtilCore.DUMMY_IDENTIFIER_TRIMMED
}
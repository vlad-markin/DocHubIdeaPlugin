package org.dochub.idea.arch.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.ElementPattern
import com.intellij.psi.PsiElement

class JSONataKeywordCompletionContributor: CompletionContributor() {


    init {

    }

    private fun registerStandardCompletion(pattern: ElementPattern<out PsiElement?>, needSpace: Boolean, vararg keywords: String) {
        extend(
            CompletionType.BASIC,
            pattern,
            JSONataKeywordCompletionProvider(needSpace, listOf(*keywords))
        )
    }
}
package org.dochub.idea.arch.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.ElementPattern
import com.intellij.psi.PsiElement
import org.dochub.idea.arch.completion.JSONataKeywords.JSONATA_FUNCTIONS

class JSONataKeywordCompletionContributor: CompletionContributor() {


    init {
        registerStandardCompletion(JSONataPatterns.ExpressionPatterns.inPattern(), true, JSONataKeywords.IN)
        registerStandardCompletion(JSONataPatterns.ExpressionPatterns.orPattern(), true, JSONataKeywords.OR)
        registerStandardCompletion(JSONataPatterns.ExpressionPatterns.andPattern(), true, JSONataKeywords.AND)
        registerStandardCompletion(JSONataPatterns.ExpressionPatterns.functionPattern(), true, JSONataKeywords.FUNCTION)
        registerStandardCompletion(JSONataPatterns.ExpressionPatterns.commonPattern(), true, JSONataKeywords.AT)
        registerStandardCompletion(JSONataPatterns.ExpressionPatterns.commonPattern(), true, JSONataKeywords.SELF)
        JSONATA_FUNCTIONS.forEach {
            registerStandardCompletion(JSONataPatterns.ExpressionPatterns.commonPattern(), true, it)
        }
    }

    private fun registerStandardCompletion(pattern: ElementPattern<out PsiElement?>, needSpace: Boolean, vararg keywords: String) {
        extend(
            CompletionType.BASIC,
            pattern,
            JSONataKeywordCompletionProvider(needSpace, listOf(*keywords))
        )
    }
}

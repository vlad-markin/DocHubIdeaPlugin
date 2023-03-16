package org.dochub.idea.arch.editor

import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import org.dochub.idea.arch.jsonata.JSONataParserDefinition
import org.dochub.idea.arch.jsonata.psi.JSONataTypes

class JSONataBraceMatcher: PairedBraceMatcher {

    override fun getPairs(): Array<BracePair> {
        return bracePairs
    }

    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?): Boolean {
        return !JSONataParserDefinition.STRING_LITERALS.contains(contextType) &&
                contextType != JSONataTypes.LPARENTH &&
                contextType != JSONataTypes.LBRACKET &&
                contextType != JSONataTypes.LBRACE
    }

    override fun getCodeConstructStart(file: PsiFile?, openingBraceOffset: Int): Int {
        return openingBraceOffset
    }


    companion object {
        val bracePairs = arrayOf(
            BracePair(JSONataTypes.LPARENTH, JSONataTypes.RPARENTH, true),
            BracePair(JSONataTypes.LBRACKET, JSONataTypes.RBRACKET, false),
            BracePair(JSONataTypes.LBRACE, JSONataTypes.RBRACE, false)
        )
    }
}
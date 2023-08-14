package org.dochub.idea.arch.wordScanner

import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import org.dochub.idea.arch.jsonata.JSONataLexerAdapter
import org.dochub.idea.arch.jsonata.JSONataParserDefinition

class JSONataWordScanner : DefaultWordsScanner(
    JSONataLexerAdapter(),
    JSONataParserDefinition.VARIABLES,
    JSONataParserDefinition.COMMENTS,
    JSONataParserDefinition.STRING_LITERALS,
)

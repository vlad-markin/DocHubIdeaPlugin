package org.dochub.idea.arch.jsonata;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.dochub.idea.arch.jsonata.psi.JSONataTypes;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class JSONataSyntaxHighlighter extends SyntaxHighlighterBase {

    private static final TextAttributesKey[] STRING_KEYS
            = new TextAttributesKey[]{DefaultLanguageHighlighterColors.STRING};
    private static final TextAttributesKey[] VARIABLE_KEYS
            = new TextAttributesKey[]{DefaultLanguageHighlighterColors.KEYWORD};
    private static final TextAttributesKey[] COMMENT_KEYS
            = new TextAttributesKey[]{DefaultLanguageHighlighterColors.LINE_COMMENT};
    private static final TextAttributesKey[] EMPTY_KEYS
            = new TextAttributesKey[0];

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new JSONataLexerAdapter();
    }

    @Override
    public TextAttributesKey @NotNull [] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(JSONataTypes.COMMENT)) {
            return COMMENT_KEYS;
        } else if (tokenType.equals(JSONataTypes.VARIABLE)) {
            return VARIABLE_KEYS;
        }  else if (tokenType.equals(JSONataTypes.STRING)) {
            return STRING_KEYS;
        }
        return EMPTY_KEYS;
    }
}

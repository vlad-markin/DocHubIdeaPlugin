package org.dochub.idea.arch.annotators;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class SyntaxHighlighter {
    public static final TextAttributesKey SEPARATOR =
            createTextAttributesKey("DOCHUB_ANT_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey IDENTIFIER =
            createTextAttributesKey("DOCHUB_ANT_ID", DefaultLanguageHighlighterColors.IDENTIFIER);
}

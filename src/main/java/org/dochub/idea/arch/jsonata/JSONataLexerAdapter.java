package org.dochub.idea.arch.jsonata;

import com.intellij.lexer.FlexAdapter;
import org.dochub.idea.arch.jsonata.lexer.JSONataLexer;


public class JSONataLexerAdapter extends FlexAdapter {
    public JSONataLexerAdapter() {
        super(new JSONataLexer(null));
    }
}

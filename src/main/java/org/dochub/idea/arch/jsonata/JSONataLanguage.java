package org.dochub.idea.arch.jsonata;

import com.intellij.lang.Language;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public class JSONataLanguage extends Language {
    public static final JSONataLanguage INSTANCE = new JSONataLanguage();
    public static final Icon ICON = IconLoader.getIcon("/images/jsonata.png", JSONataLanguage.class);
    JSONataLanguage() {
        super("JSONata");
    }
}

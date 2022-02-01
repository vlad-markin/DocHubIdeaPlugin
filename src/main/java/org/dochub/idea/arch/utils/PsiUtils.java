package org.dochub.idea.arch.utils;

import com.intellij.psi.PsiElement;

public class PsiUtils {
    public static String getText(PsiElement element) {
        return element.getText().replaceFirst("IntellijIdeaRulezzz", "");
    }
}

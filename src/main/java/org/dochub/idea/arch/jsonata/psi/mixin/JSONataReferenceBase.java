package org.dochub.idea.arch.jsonata.psi.mixin;


import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiReferenceBase;
import org.jetbrains.annotations.NotNull;

public abstract class JSONataReferenceBase extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {

    public JSONataReferenceBase(@NotNull PsiElement element, TextRange rangeInElement) {
        super(element, rangeInElement);
    }
}

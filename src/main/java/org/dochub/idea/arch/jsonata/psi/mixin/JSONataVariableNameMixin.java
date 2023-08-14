package org.dochub.idea.arch.jsonata.psi.mixin;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiReference;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import org.dochub.idea.arch.jsonata.JSONataLanguage;
import org.dochub.idea.arch.jsonata.psi.JSONataTypes;
import org.intellij.sdk.language.psi.JSONataBinding;
import org.intellij.sdk.language.psi.JSONataUseOfVariableOrFunction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class JSONataVariableNameMixin extends ASTWrapperPsiElement implements PsiElement, PsiIdentifier{

    public JSONataVariableNameMixin(ASTNode astNode) {
        super(astNode);
    }

    @Override
    public IElementType getTokenType() {
        return JSONataTypes.VARIABLE;
    }


    @Override
    public PsiReference getReference() {

        return new PsiReference() {
            @Override
            public @NotNull PsiElement getElement() {
                return JSONataVariableNameMixin.this;
            }

            @Override
            public @NotNull TextRange getRangeInElement() {
                return TextRange.from(0, getElement().getTextLength());
            }

            @Override
            public Object @NotNull [] getVariants() {
                return PsiTreeUtil.findChildrenOfType(getContainingFile(), JSONataBinding.class).stream().map(
                        elem -> LookupElementBuilder.create(elem).withIcon(JSONataLanguage.ICON)
                                .withTypeText(elem.getContainingFile().getName())
                ).toArray();
            }

            @Override
            public @Nullable PsiElement resolve() {

                return PsiTreeUtil.findChildrenOfType(getContainingFile(), JSONataBinding.class).stream()
                        .filter(e -> e.getFirstChild().getText().equals(getElement().getText()))
                        .findFirst()
                        .orElse(null);
            }

            @Override
            public @NotNull @NlsSafe String getCanonicalText() {
                return getElement().getText();
            }

            @Override
            public PsiElement handleElementRename(@NotNull String newElementName) throws IncorrectOperationException {
                return getFirstChild().replace(
                        Objects.requireNonNull(PsiTreeUtil.findChildOfType(
                                PsiFileFactory.getInstance(getProject()).createFileFromText("test.jsonata", JSONataLanguage.INSTANCE, newElementName),
                                JSONataUseOfVariableOrFunction.class
                        ))
                );
            }

            @Override
            public PsiElement bindToElement(@NotNull PsiElement element) throws IncorrectOperationException {
                return getElement();
            }

            @Override
            public boolean isReferenceTo(@NotNull PsiElement element) {
                return Comparing.equal(element, resolve());
            }

            @Override
            public boolean isSoft() {
                return false;
            }
        };
    }
}

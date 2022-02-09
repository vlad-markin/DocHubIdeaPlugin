package org.dochub.idea.arch.quickfix;

import com.intellij.codeInsight.intention.impl.BaseIntentionAction;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.lang.annotation.AnnotationBuilder;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

public abstract class BaseQuickFix extends BaseIntentionAction {

    protected PsiElement element;

    public abstract ElementPattern<? extends PsiElement> getFixPattern(PsiElement element);

    public abstract void makeFix(@NotNull PsiElement element, @NotNull AnnotationHolder holder);

    public BaseQuickFix(PsiElement element) {
        this.element = element;
    }

    public BaseQuickFix() {
        this.element = null;
    }

    @Override
    public @NotNull @IntentionFamilyName String getFamilyName() { return  null; }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
        return false;
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile file) throws IncorrectOperationException {
    }

    public static <T extends PsiElement> PsiElementPattern.Capture<T> psi(final Class<T> aClass) {
        return PlatformPatterns.psiElement(aClass);
    }

    public static PsiElementPattern.Capture<PsiElement> psi(IElementType type) {
        return PlatformPatterns.psiElement(type);
    }
}

package info.dochub.idea.arch.jsonata.psi;

import com.intellij.lang.refactoring.RefactoringSupportProvider;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JSONataRefactoringSupportProvider extends RefactoringSupportProvider {
    @Override
    public boolean isMemberInplaceRenameAvailable(@NotNull PsiElement element, @Nullable PsiElement context) {
        return element instanceof JSONataNamedElement;
    }

    @Override
    public boolean isSafeDeleteAvailable(@NotNull PsiElement element) {
        return true;
    }
}

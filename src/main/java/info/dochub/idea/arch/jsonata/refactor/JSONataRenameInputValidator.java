package info.dochub.idea.arch.jsonata.refactor;

import com.intellij.patterns.ElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.refactoring.rename.RenameInputValidator;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class JSONataRenameInputValidator implements RenameInputValidator {
    @Override
    public @NotNull ElementPattern<? extends PsiElement> getPattern() {
        return null;
    }

    @Override
    public boolean isInputValid(@NotNull String newName, @NotNull PsiElement element, @NotNull ProcessingContext context) {
        return false;
    }
}

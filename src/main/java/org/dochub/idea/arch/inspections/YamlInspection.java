package org.dochub.idea.arch.inspections;

import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.lang.injection.InjectedLanguageManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class YamlInspection extends LocalInspectionTool {
    private static final Logger LOG = Logger.getInstance(YamlInspection.class);

    @Override
    public ProblemDescriptor[] checkFile(@NotNull final PsiFile psiFile,
                                         @NotNull final InspectionManager manager,
                                         final boolean isOnTheFly) {
        if (InjectedLanguageManager.getInstance(manager.getProject()).isInjectedFragment(psiFile)) {
            LOG.debug("Ignoring file as it is an injected fragment: " + psiFile);
            return noProblemsFound();
        }

        final Module module = moduleOf(psiFile);

        LOG.debug("YamlInspection is touched");
        return noProblemsFound();
    }

    @NotNull
    private ProblemDescriptor[] noProblemsFound() {
        return ProblemDescriptor.EMPTY_ARRAY;
    }

    @Nullable
    private Module moduleOf(@NotNull final PsiFile psiFile) {
        return ModuleUtil.findModuleForPsiElement(psiFile);
    }

}

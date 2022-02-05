package org.dochub.idea.arch.references;

import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import org.dochub.idea.arch.references.providers.BaseReferencesProvider;
import org.dochub.idea.arch.references.providers.imports.ReferencesProvider;
import org.jetbrains.annotations.NotNull;

public class ReferenceContributor extends PsiReferenceContributor {

    private static final BaseReferencesProvider[] providers = {
            new ReferencesProvider()
    };

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        for (BaseReferencesProvider provider : providers) {
            registrar.registerReferenceProvider(
                    provider.getPattern(),
                    provider
            );
        }
    }
}

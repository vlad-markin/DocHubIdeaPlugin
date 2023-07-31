package info.dochub.idea.arch.references;

import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import info.dochub.idea.arch.references.providers.*;
import info.dochub.idea.arch.references.providers.*;
import org.jetbrains.annotations.NotNull;

public class ReferenceContributor extends PsiReferenceContributor {
    private static final BaseReferencesProvider[] providers = {
            new RefImportsSource(),
            new RefComponentID(),
            new RefAspectID(),
            new RefDocsID(),
            new RefDocSource(),
            new RefContextID()
    };

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        for (BaseReferencesProvider provider : providers) {
            registrar.registerReferenceProvider(
                    provider.getRefPattern(),
                    provider
            );
        }
    }
}

package info.dochub.idea.arch.completions.providers.aspects;

import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import info.dochub.idea.arch.completions.providers.Aspects;
import info.dochub.idea.arch.completions.providers.suggets.LocationSuggestAspects;
import org.jetbrains.yaml.psi.YAMLKeyValue;

public class AspectLocation extends LocationSuggestAspects {
    private static String keyword = "location";

    @Override
    protected ElementPattern<? extends PsiElement> getPattern() {
        return PlatformPatterns.or(
                PlatformPatterns.psiElement()
                        .withSuperParent(2,
                                psi(YAMLKeyValue.class)
                                        .withName(PlatformPatterns.string().equalTo(keyword))
                                        .and(Aspects.rootPattern)
                        ),
                PlatformPatterns.psiElement()
                        .withSuperParent(3,
                                psi(YAMLKeyValue.class)
                                        .withName(PlatformPatterns.string().equalTo(keyword))
                                        .and(Aspects.rootPattern)
                        )
        );
    }
}

package org.dochub.idea.arch.jsonata;

import com.intellij.lang.injection.general.*;
import com.intellij.psi.*;
import org.jetbrains.annotations.*;
import org.jetbrains.yaml.psi.*;

public class JSONataInjector implements LanguageInjectionContributor {
    @Override
    public @Nullable Injection getInjection(@NotNull PsiElement context) {
        if(context instanceof YAMLScalar) {
            YAMLScalar scalar = YAMLScalar.class.cast(context);
            String rawString = scalar.getTextValue();
            if(rawString.matches("[(][^+]*[)]\\s*")) {
                return new SimpleInjection(JSONataLanguage.INSTANCE,"", "", "JSONata");
            }
        }
        return null;
    }

}

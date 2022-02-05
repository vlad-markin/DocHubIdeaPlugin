package org.dochub.idea.arch.completions.providers.suggets;

import com.intellij.openapi.project.Project;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import org.dochub.idea.arch.completions.providers.CustomProvider;
import org.dochub.idea.arch.indexing.CacheBuilder;

import java.util.Map;

public class BaseSuggest extends CustomProvider {
    protected ElementPattern<? extends PsiElement> getPattern() {
        return PlatformPatterns.psiElement();
    }

    protected Map<String, Object> getProjectCache(Project project) {
        return CacheBuilder.getProjectCache(project);
    }
}

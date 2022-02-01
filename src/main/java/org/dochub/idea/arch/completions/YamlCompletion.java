package org.dochub.idea.arch.completions;

import com.intellij.codeInsight.completion.*;
import com.intellij.openapi.diagnostic.Logger;
import org.dochub.idea.arch.completions.providers.*;
import org.dochub.idea.arch.completions.providers.components.ComponentAspects;
import org.dochub.idea.arch.completions.providers.components.Links;
import org.dochub.idea.arch.completions.providers.contexts.ContextComponents;
import org.dochub.idea.arch.completions.providers.contexts.Uml;
import org.dochub.idea.arch.completions.providers.contexts.UmlNotations;
import org.dochub.idea.arch.completions.providers.contexts.Extralinks;
import org.dochub.idea.arch.completions.providers.docs.DocRef;
import org.dochub.idea.arch.completions.providers.docs.DocSource;
import org.dochub.idea.arch.completions.providers.forms.FormItem;
import org.dochub.idea.arch.completions.providers.forms.FormItemField;
import org.dochub.idea.arch.completions.providers.forms.FormItemFieldRequired;
import org.dochub.idea.arch.completions.providers.imports.ImportItem;
import org.dochub.idea.arch.completions.providers.technologies.ItemsItem;
import org.dochub.idea.arch.completions.providers.technologies.SectionItem;
import org.dochub.idea.arch.inspections.YamlInspection;

public class YamlCompletion extends CompletionContributor {
    private static final Logger LOG = Logger.getInstance(YamlInspection.class);
    private static final CustomProvider[] providers = {
            new Root(),
            new Contexts(),
                new Extralinks(),
                new Uml(),
                    new UmlNotations(),
                new ContextComponents(),
            new Components(),
                new Links(),
                new ComponentAspects(),
            new Forms(),
                new FormItem(),
                    new FormItemField(),
                        new FormItemFieldRequired(),
            new Aspects(),
            new Namespaces(),
            new Docs(),
                new DocSource(),
                new DocRef(),
            new Technologies(),
                new SectionItem(),
                new ItemsItem(),
            new Imports(),
                new ImportItem()
    };

    public YamlCompletion() {
        for (final CustomProvider provider : providers) {
            provider.appendToCompletion(this);
        }
    }
}

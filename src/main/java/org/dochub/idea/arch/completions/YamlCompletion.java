package org.dochub.idea.arch.completions;

import com.intellij.codeInsight.completion.*;
import com.intellij.openapi.diagnostic.Logger;
import org.dochub.idea.arch.completions.providers.*;
import org.dochub.idea.arch.completions.providers.components.Links;
import org.dochub.idea.arch.completions.providers.contexts.extralinks;
import org.dochub.idea.arch.completions.providers.contexts.uml;
import org.dochub.idea.arch.completions.providers.contexts.umlNotations;
import org.dochub.idea.arch.completions.providers.forms.FormItem;
import org.dochub.idea.arch.completions.providers.forms.FormItemField;
import org.dochub.idea.arch.completions.providers.forms.FormItemFieldRequired;
import org.dochub.idea.arch.completions.providers.technologies.ItemsItem;
import org.dochub.idea.arch.completions.providers.technologies.SectionItem;
import org.dochub.idea.arch.inspections.YamlInspection;

public class YamlCompletion extends CompletionContributor {
    private static final Logger LOG = Logger.getInstance(YamlInspection.class);
    private static final CustomProvider[] providers = {
            new Root(),
            new Contexts(),
                new extralinks(),
                new uml(),
                    new umlNotations(),
            new Components(),
                new Links(),
            new Forms(),
                new FormItem(),
                    new FormItemField(),
                        new FormItemFieldRequired(),
            new Aspects(),
            new Namespaces(),
            new Docs(),
            new Technologies(),
                new SectionItem(),
                new ItemsItem()
    };

    public YamlCompletion() {
        for (final CustomProvider provider : providers) {
            provider.appendToCompletion(this);
        }
    }
}

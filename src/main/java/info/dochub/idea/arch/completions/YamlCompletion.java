package info.dochub.idea.arch.completions;

import com.intellij.codeInsight.completion.*;
import info.dochub.idea.arch.completions.providers.*;
import info.dochub.idea.arch.completions.providers.components.*;
import info.dochub.idea.arch.completions.providers.contexts.*;
import info.dochub.idea.arch.completions.providers.datasets.DatasetOrigin;
import info.dochub.idea.arch.completions.providers.docs.*;
import info.dochub.idea.arch.completions.providers.namespaces.NamespaceID;
import info.dochub.idea.arch.completions.providers.technologies.ItemsItem;
import info.dochub.idea.arch.completions.providers.technologies.SectionItem;
import info.dochub.idea.arch.completions.providers.*;
import info.dochub.idea.arch.completions.providers.aspects.AspectID;
import info.dochub.idea.arch.completions.providers.aspects.AspectLocation;
import info.dochub.idea.arch.completions.providers.components.*;
import info.dochub.idea.arch.completions.providers.contexts.*;
import info.dochub.idea.arch.completions.providers.docs.*;
import info.dochub.idea.arch.completions.providers.forms.FormItem;
import info.dochub.idea.arch.completions.providers.forms.FormItemField;
import info.dochub.idea.arch.completions.providers.forms.FormItemFieldRequired;
import info.dochub.idea.arch.completions.providers.imports.ImportItem;

public class YamlCompletion extends CompletionContributor {
    private static final CustomProvider[] providers = {
            new Root(),
            new Contexts(),
                new ContextID(),
                new Extralinks(),
                new Uml(),
                    new UmlNotations(),
                new ContextComponents(),
                new ContextLocation(),
            new Components(),
                new ComponentID(),
                new ComponentEntity(),
                new ComponentLinks(),
                    new ComponentLinksID(),
                    new ComponentLinksDirection(),
                new ComponentAspects(),
                new ComponentContractID(),
            new Forms(),
                new FormItem(),
                    new FormItemField(),
                        new FormItemFieldRequired(),
            new Aspects(),
                new AspectID(),
                new AspectLocation(),
            new Namespaces(),
                new NamespaceID(),
            new Docs(),
                new DocID(),
                new DocSource(),
                new DocSubjects(),
                new DocType(),
                new DocLocation(),
                new DocOrigin(),
            new Technologies(),
                new SectionItem(),
                new ItemsItem(),
            new Imports(),
                new ImportItem(),
            new Dataset(),
                new DatasetOrigin()
    };

    public YamlCompletion() {
        for (final CustomProvider provider : providers) {
            provider.appendToCompletion(this);
        }
    }
}

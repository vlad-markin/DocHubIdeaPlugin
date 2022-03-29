package org.dochub.idea.arch.tools;

import com.intellij.codeHighlighting.Pass;
import com.intellij.codeInsight.daemon.*;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiElement;
import com.intellij.util.Function;
import org.dochub.idea.arch.references.providers.RefComponentID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLSequenceItem;

import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.List;

public class LineMarkerProvider extends LineMarkerProviderDescriptor {

    @Override
    public final void collectSlowLineMarkers(@NotNull List<? extends PsiElement> elements, @NotNull Collection<? super LineMarkerInfo<?>> result) {
        for (int i = 0, size = elements.size(); i < size; i++) {
            PsiElement element = elements.get(i);
            collectNavigationMarkers(element, result);
        }
    }

    protected void collectNavigationMarkers(
            @NotNull PsiElement element,
            @NotNull Collection<? super RelatedItemLineMarkerInfo<?>> result
    ) {
//        NavigationGutterIconBuilder<PsiElement> builder =
//                NavigationGutterIconBuilder.create(AllIcons.Actions.Preview)
//                        .setTarget(element)
//                        .setTooltipText("Показать ... ");
//        result.add(builder.createLineMarkerInfo(element));
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public LineMarkerInfo<?> getLineMarkerInfo(@NotNull PsiElement element) {
        LineMarkerInfo<?> result = null;
        if (RefComponentID.pattern().accepts(element)) {
            //if (id.matches("[-a-zA-Z0-9\\_](\\.[-a-zA-Z0-9\\_])*")) {
            PsiElement markElement = element;
            while (element != null && !(element instanceof YAMLKeyValue) && !(element instanceof YAMLSequenceItem)) {
                element = element.getParent();
            }
            result = new LineMarkerInfo<>(
                    markElement,
                    markElement.getTextRange(),
                    AllIcons.Actions.Preview,
                    Pass.UPDATE_ALL,
                    new Function<PsiElement, String>() {
                        @Override
                        public String fun(PsiElement element) {
                            return "Показать ... 1";
                        }
                    },
                    new GutterIconNavigationHandler<PsiElement>() {
                        @Override
                        public void navigate(MouseEvent e, PsiElement elt) {
                            int a = 10;
                        }
                    },
                    GutterIconRenderer.Alignment.LEFT
            );
            //}
        }
        return result;
    }
}

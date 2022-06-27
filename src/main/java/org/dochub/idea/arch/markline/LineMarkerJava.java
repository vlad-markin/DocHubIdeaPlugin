package org.dochub.idea.arch.markline;

import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import org.dochub.idea.arch.annotators.DocHubAnnotator;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

import static org.dochub.idea.arch.markline.LineMarkerNavigator.*;

public class LineMarkerJava extends LineMarkerProviderDescriptor {

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
    }

    @Override
    public String getName() {
        return null;
    }

    private LineMarkerInfo getLineMarkerInfoForAnnotator(@NotNull PsiElement element) {
        LineMarkerInfo result = null;
        List<DocHubAnnotator.EntityRef> refs = DocHubAnnotator.parseComment(element);
        if (refs.size() > 0) {
            DocHubAnnotator.EntityRef ref = refs.get(0);
            result = makeLineMarkerInfo(
                    new DocHubNavigationHandler(ref.entity, ref.id),
                    element
            );
        }
        return result;
    }

    @Override
    public LineMarkerInfo<?> getLineMarkerInfo(@NotNull PsiElement element) {
        LineMarkerInfo result = null;
        if (element instanceof PsiComment) {
            result = getLineMarkerInfoForAnnotator(element);
        }
        return result;
    }
}

package info.dochub.idea.arch.annotators;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JavaAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (!(element instanceof PsiComment)) {
            return;
        }
        List<DocHubAnnotator.EntityRef> refs = DocHubAnnotator.parseComment(element);
        for (int i = 0; i < refs.size(); i++) {
            DocHubAnnotator.EntityRef ref = refs.get(i);
            int offset = element.getTextRange().getStartOffset() + ref.start;
            TextRange prefixRange = TextRange.from(offset, ref.prefix.length());
            TextRange idRange = TextRange.from(offset, ref.prefix.length() + ref.entity.length() + ref.id.length() + 1);
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(prefixRange).textAttributes(DefaultLanguageHighlighterColors.KEYWORD).create();
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(idRange).textAttributes(SyntaxHighlighter.IDENTIFIER).create();
        }
    }
}

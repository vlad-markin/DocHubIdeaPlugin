package info.dochub.idea.arch.utils;

import com.intellij.psi.PsiElement;
import com.intellij.util.ObjectUtils;
import org.jetbrains.yaml.psi.YAMLDocument;

public class PsiUtils {
    public static String getText(PsiElement element) {
        return element == null ? "" : element.getText().replaceFirst("IntellijIdeaRulezzz", "");
    }

    public static PsiElement getYamlDocumentByPsiElement(PsiElement element) {
        PsiElement document = element;
        while (document != null) {
            if (ObjectUtils.tryCast(document,  YAMLDocument.class) != null)
                break;
            document = document.getParent();
        }
        return document;
    }
}

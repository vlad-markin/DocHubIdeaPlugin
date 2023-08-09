package info.dochub.idea.arch.quickfix;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.patterns.ElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiParserFacade;
import com.intellij.util.IncorrectOperationException;
import info.dochub.idea.arch.utils.PsiUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.YAMLElementGenerator;
import org.jetbrains.yaml.psi.YAMLFile;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseStructureQuickFix extends BaseQuickFix {
    protected String[] getRequiredStructure() {
        return null;
    }
    List<String> needToAppendProps = null;

    public BaseStructureQuickFix(PsiElement element, List<String> needToAppendProps) {
        super(element);
        this.needToAppendProps = needToAppendProps;
    }

    public BaseStructureQuickFix() {
        super();
    }

    @Override
    public ElementPattern<? extends PsiElement> getFixPattern(PsiElement element) {
        return null;
    }

    @Override
    public void makeFix(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        PsiElement componentID = element.getParent();
        PsiElement props[] = componentID.getChildren();
        List<String> result = new ArrayList<>();
        List<String> foundProps = new ArrayList<>();
        if (props.length == 1) {
            props = props[0].getChildren();
            for (PsiElement prop: props) {
                if (prop instanceof YAMLKeyValue) {
                    foundProps.add(PsiUtils.getText(((YAMLKeyValue)prop).getKey()));
                }
            }
            for (String require : getRequiredStructure()) {
                if (foundProps.indexOf(require) < 0) {
                    result.add(require);
                }
            }
        } else result = Arrays.asList(getRequiredStructure());


        if (result.size() > 0) {
            holder.newAnnotation(HighlightSeverity.ERROR, "Lost properties")
                    .range(element)
                    .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                    .withFix(new BaseStructureQuickFix(componentID, result))
                    .create();
        }

    }

    @Override
    public String getText() {
        return "Create properties";
    }

    @Override
    public @NotNull @IntentionFamilyName String getFamilyName() {
        return "Create properties";
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
        return true;
    }

    private PsiElement getLastKeyFromMap(PsiElement map) {
        PsiElement result = null;
        PsiElement[] keyMapChildren = map.getChildren();
        for (
                Integer index = keyMapChildren.length - 1;
                !(result instanceof YAMLKeyValue) && index >= 0;
                index--
        ) {
            result = keyMapChildren[index];
        }
        return result;
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile file) throws IncorrectOperationException {
        final StringBuilder builder = new StringBuilder("\n");
        for (String prop : needToAppendProps) {
            builder.append("  " + prop + ": \n");
        }

        final YAMLFile yamlFile = YAMLElementGenerator.getInstance(file.getProject()).createDummyYamlWithText(builder.toString());
        final YAMLMapping mapping = (YAMLMapping) yamlFile.getDocuments().get(0).getTopLevelValue();
        assert mapping != null;
        if (element.getChildren().length > 0) {
            PsiElement keyMap = element.getLastChild();
            final PsiElement newLineNode =
                    PsiParserFacade.SERVICE.getInstance(project).createWhiteSpaceFromText("\n");
            PsiElement lastKey = getLastKeyFromMap(keyMap);

            if (lastKey == null) {
                element.addBefore(mapping, element.getLastChild());
            } else {
                PsiElement insertKeys[] = mapping.getChildren();
                for (Integer index = insertKeys.length - 1; index >= 0; index--) {
                    PsiElement key = insertKeys[index];
                    key.add(newLineNode);
                    keyMap.addBefore(key, lastKey);
                }
            }
        } else element.add(mapping);
    }
}

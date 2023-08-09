package info.dochub.idea.arch.markline;

import com.intellij.codeInsight.daemon.GutterIconNavigationHandler;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiElement;
import com.intellij.util.Function;
import com.intellij.util.messages.Topic;
import org.jetbrains.annotations.NotNull;

import java.awt.event.MouseEvent;
import java.util.function.Supplier;
public class LineMarkerNavigator {
    public static Topic<NavigateMessage> ON_NAVIGATE_MESSAGE = Topic.create("Navigate to", NavigateMessage.class);
    public interface NavigateMessage {
        void go(String entity, String id);
    }
    public static class DocHubNavigationHandler implements GutterIconNavigationHandler {
        private String entity = null;
        private String id = null;
        public DocHubNavigationHandler(String entity, String id) {
            super();
            this.entity = entity;
            this.id = id;
        }
        @Override
        public void navigate(MouseEvent e, PsiElement elt) {
            NavigateMessage publisher = elt.getProject().getMessageBus().syncPublisher(ON_NAVIGATE_MESSAGE);
            publisher.go(entity, id);
        }
    }
    public static LineMarkerInfo makeLineMarkerInfo(
            @NotNull DocHubNavigationHandler naviHandler,
            @NotNull PsiElement element) {
        return new LineMarkerInfo<>(
                element,
                element.getTextRange(),
                AllIcons.Actions.Preview,
                new Function<PsiElement, String>() {
                    @Override
                    public String fun(PsiElement element) {
                        return "Показать в DocHub";
                    }
                },
                naviHandler,
                GutterIconRenderer.Alignment.LEFT,
                new Supplier<String>() {
                    @Override
                    public String get() {
                        return "DocHub";
                    }
                }
        );
    }
}

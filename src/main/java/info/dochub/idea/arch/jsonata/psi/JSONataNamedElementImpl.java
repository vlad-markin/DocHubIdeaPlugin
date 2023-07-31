package info.dochub.idea.arch.jsonata.psi;


import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public abstract class JSONataNamedElementImpl extends ASTWrapperPsiElement implements JSONataNamedElement{
    public JSONataNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}

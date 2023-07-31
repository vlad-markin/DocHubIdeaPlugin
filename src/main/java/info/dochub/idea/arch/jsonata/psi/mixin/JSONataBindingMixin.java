package info.dochub.idea.arch.jsonata.psi.mixin;


import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import info.dochub.idea.arch.jsonata.psi.JSONataNamedElement;
import info.dochub.idea.arch.jsonata.psi.JSONataPsiVariable;
import info.dochub.idea.arch.jsonata.JSONataLanguage;
import org.intellij.sdk.language.psi.JSONataBinding;
import org.intellij.sdk.language.psi.JSONataExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;



public abstract class JSONataBindingMixin extends ASTWrapperPsiElement implements PsiElement, JSONataPsiVariable, JSONataNamedElement, JSONataBinding, PsiNamedElement {
    public JSONataBindingMixin(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public @NotNull JSONataExpression getExpression() {
        return (JSONataExpression) getLastChild();
    }

    @Override
    public @Nullable PsiElement getNameIdentifier() {
        return getFirstChild();
    }


    @Override
    public String getName() {
        return getFirstChild().getText().replaceAll("\\\\ ", "");
    }

    @Override
    public PsiElement setName(@NlsSafe @NotNull String name) throws IncorrectOperationException {

        return getFirstChild().replace(PsiTreeUtil.findChildOfType(PsiFileFactory.getInstance(getProject()).createFileFromText("test.jsonata", JSONataLanguage.INSTANCE, StringUtil.trim ("(" + name + ":=0; )"), false, false), JSONataBinding.class).getFirstChild());
    }

}

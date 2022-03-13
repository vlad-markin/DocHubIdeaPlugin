// This is a generated file. Not intended for manual editing.
package org.intellij.sdk.language.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.dochub.idea.arch.jsonata.psi.JSONataTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import org.intellij.sdk.language.psi.*;

public class JSONataExpressionImpl extends ASTWrapperPsiElement implements JSONataExpression {

  public JSONataExpressionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull JSONataVisitor visitor) {
    visitor.visitExpression(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JSONataVisitor) accept((JSONataVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<JSONataArguments> getArgumentsList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JSONataArguments.class);
  }

  @Override
  @NotNull
  public List<JSONataOperators> getOperatorsList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JSONataOperators.class);
  }

  @Override
  @Nullable
  public PsiElement getRegex() {
    return findChildByType(REGEX);
  }

}

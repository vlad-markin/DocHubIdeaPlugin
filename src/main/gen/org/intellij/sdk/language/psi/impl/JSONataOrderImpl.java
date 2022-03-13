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

public class JSONataOrderImpl extends ASTWrapperPsiElement implements JSONataOrder {

  public JSONataOrderImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull JSONataVisitor visitor) {
    visitor.visitOrder(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JSONataVisitor) accept((JSONataVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<JSONataDeepProp> getDeepPropList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JSONataDeepProp.class);
  }

  @Override
  @NotNull
  public List<JSONataFilter> getFilterList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JSONataFilter.class);
  }

  @Override
  @NotNull
  public List<JSONataOrderParams> getOrderParamsList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JSONataOrderParams.class);
  }

}

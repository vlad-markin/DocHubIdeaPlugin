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

public class JSONataChainImpl extends ASTWrapperPsiElement implements JSONataChain {

  public JSONataChainImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull JSONataVisitor visitor) {
    visitor.visitChain(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JSONataVisitor) accept((JSONataVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<JSONataChain> getChainList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JSONataChain.class);
  }

  @Override
  @NotNull
  public JSONataChainSimple getChainSimple() {
    return findNotNullChildByClass(JSONataChainSimple.class);
  }

  @Override
  @NotNull
  public List<JSONataTransform> getTransformList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JSONataTransform.class);
  }

}

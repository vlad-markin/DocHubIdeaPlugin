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

public class JSONataDeepPropImpl extends ASTWrapperPsiElement implements JSONataDeepProp {

  public JSONataDeepPropImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull JSONataVisitor visitor) {
    visitor.visitDeepProp(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JSONataVisitor) accept((JSONataVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public JSONataBlock getBlock() {
    return findChildByClass(JSONataBlock.class);
  }

  @Override
  @NotNull
  public List<JSONataJname> getJnameList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JSONataJname.class);
  }

  @Override
  @NotNull
  public List<JSONataJson> getJsonList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JSONataJson.class);
  }

  @Override
  @NotNull
  public List<JSONataMethods> getMethodsList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JSONataMethods.class);
  }

  @Override
  @Nullable
  public PsiElement getRange() {
    return findChildByType(RANGE);
  }

  @Override
  @Nullable
  public PsiElement getSysVariable() {
    return findChildByType(SYS_VARIABLE);
  }

}

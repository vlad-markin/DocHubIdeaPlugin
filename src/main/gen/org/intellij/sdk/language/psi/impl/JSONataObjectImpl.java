// This is a generated file. Not intended for manual editing.
package org.intellij.sdk.language.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.dochub.idea.arch.jsonata.psi.JSONataTypes.*;
import org.intellij.sdk.language.psi.*;

public class JSONataObjectImpl extends JSONataJsonImpl implements JSONataObject {

  public JSONataObjectImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull JSONataVisitor visitor) {
    visitor.visitObject(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JSONataVisitor) accept((JSONataVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<JSONataProp> getPropList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JSONataProp.class);
  }

}

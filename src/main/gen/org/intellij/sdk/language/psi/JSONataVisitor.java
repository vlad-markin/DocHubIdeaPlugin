// This is a generated file. Not intended for manual editing.
package org.intellij.sdk.language.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;

public class JSONataVisitor extends PsiElementVisitor {

  public void visitArguments(@NotNull JSONataArguments o) {
    visitPsiElement(o);
  }

  public void visitArray(@NotNull JSONataArray o) {
    visitJson(o);
  }

  public void visitBlock(@NotNull JSONataBlock o) {
    visitPsiElement(o);
  }

  public void visitChain(@NotNull JSONataChain o) {
    visitPsiElement(o);
  }

  public void visitChainSimple(@NotNull JSONataChainSimple o) {
    visitPsiElement(o);
  }

  public void visitDeepProp(@NotNull JSONataDeepProp o) {
    visitPsiElement(o);
  }

  public void visitExpression(@NotNull JSONataExpression o) {
    visitPsiElement(o);
  }

  public void visitFilter(@NotNull JSONataFilter o) {
    visitPsiElement(o);
  }

  public void visitFunction(@NotNull JSONataFunction o) {
    visitPsiElement(o);
  }

  public void visitFunctionCall(@NotNull JSONataFunctionCall o) {
    visitPsiElement(o);
  }

  public void visitFunctionCallParams(@NotNull JSONataFunctionCallParams o) {
    visitPsiElement(o);
  }

  public void visitJname(@NotNull JSONataJname o) {
    visitPsiElement(o);
  }

  public void visitJson(@NotNull JSONataJson o) {
    visitValue(o);
  }

  public void visitJsonata(@NotNull JSONataJsonata o) {
    visitPsiElement(o);
  }

  public void visitMethodParams(@NotNull JSONataMethodParams o) {
    visitPsiElement(o);
  }

  public void visitMethods(@NotNull JSONataMethods o) {
    visitPsiElement(o);
  }

  public void visitObject(@NotNull JSONataObject o) {
    visitJson(o);
  }

  public void visitOperators(@NotNull JSONataOperators o) {
    visitPsiElement(o);
  }

  public void visitOrder(@NotNull JSONataOrder o) {
    visitPsiElement(o);
  }

  public void visitOrderParams(@NotNull JSONataOrderParams o) {
    visitPsiElement(o);
  }

  public void visitProp(@NotNull JSONataProp o) {
    visitPsiElement(o);
  }

  public void visitTransform(@NotNull JSONataTransform o) {
    visitPsiElement(o);
  }

  public void visitTransformCall(@NotNull JSONataTransformCall o) {
    visitPsiElement(o);
  }

  public void visitTransformLocation(@NotNull JSONataTransformLocation o) {
    visitPsiElement(o);
  }

  public void visitValue(@NotNull JSONataValue o) {
    visitPsiElement(o);
  }

  public void visitVariableSet(@NotNull JSONataVariableSet o) {
    visitPsiElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}

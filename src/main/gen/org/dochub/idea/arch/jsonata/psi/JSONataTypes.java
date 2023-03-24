// This is a generated file. Not intended for manual editing.
package org.dochub.idea.arch.jsonata.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import org.intellij.sdk.language.psi.impl.*;

public interface JSONataTypes {

  IElementType ARGUMENTS = new JSONataElementType("ARGUMENTS");
  IElementType ARRAY = new JSONataElementType("ARRAY");
  IElementType BLOCK = new JSONataElementType("BLOCK");
  IElementType BLOCK_BODY = new JSONataElementType("BLOCK_BODY");
  IElementType BLOCK_ITEM = new JSONataElementType("BLOCK_ITEM");
  IElementType CALL = new JSONataElementType("CALL");
  IElementType CALL_PARAMS = new JSONataElementType("CALL_PARAMS");
  IElementType DECL_FUNCTION = new JSONataElementType("DECL_FUNCTION");
  IElementType DEEP = new JSONataElementType("DEEP");
  IElementType FUNC_PARAMS = new JSONataElementType("FUNC_PARAMS");
  IElementType JSONATA = new JSONataElementType("JSONATA");
  IElementType MODIFIER = new JSONataElementType("MODIFIER");
  IElementType OBJECT = new JSONataElementType("OBJECT");
  IElementType OPERATORS = new JSONataElementType("OPERATORS");
  IElementType PROP = new JSONataElementType("PROP");
  IElementType RANGE = new JSONataElementType("RANGE");
  IElementType SET_VARIABLE = new JSONataElementType("SET_VARIABLE");
  IElementType SYS_FUNCTION = new JSONataElementType("SYS_FUNCTION");
  IElementType TRANSFORM = new JSONataElementType("TRANSFORM");
  IElementType TRANSFORM_DO = new JSONataElementType("TRANSFORM_DO");
  IElementType TRANSFORM_LOCATION = new JSONataElementType("TRANSFORM_LOCATION");
  IElementType UNUSED_IN_BNF = new JSONataElementType("UNUSED_IN_BNF");

  IElementType AND = new JSONataTokenType("AND");
  IElementType ASSIGN = new JSONataTokenType("ASSIGN");
  IElementType BACKSLASH = new JSONataTokenType("BACKSLASH");
  IElementType BACKTICK = new JSONataTokenType("BACKTICK");
  IElementType BOOLEAN = new JSONataTokenType("BOOLEAN");
  IElementType CHAIN = new JSONataTokenType("CHAIN");
  IElementType COLON = new JSONataTokenType("COLON");
  IElementType COMMA = new JSONataTokenType("COMMA");
  IElementType COMMENT = new JSONataTokenType("COMMENT");
  IElementType CONCAT = new JSONataTokenType("CONCAT");
  IElementType CONTEXT = new JSONataTokenType("CONTEXT");
  IElementType DOT = new JSONataTokenType("DOT");
  IElementType DOUBLE_DOT = new JSONataTokenType("DOUBLE_DOT");
  IElementType DOUBLE_QUOTE = new JSONataTokenType("DOUBLE_QUOTE");
  IElementType EQ = new JSONataTokenType("EQ");
  IElementType FUNCTION = new JSONataTokenType("FUNCTION");
  IElementType GREATER = new JSONataTokenType("GREATER");
  IElementType GREATER_OR_EQUAL = new JSONataTokenType("GREATER_OR_EQUAL");
  IElementType ID = new JSONataTokenType("ID");
  IElementType IN = new JSONataTokenType("IN");
  IElementType LBRACE = new JSONataTokenType("LBRACE");
  IElementType LBRACKET = new JSONataTokenType("LBRACKET");
  IElementType LESS = new JSONataTokenType("LESS");
  IElementType LESS_OR_EQUAL = new JSONataTokenType("LESS_OR_EQUAL");
  IElementType LPARENTH = new JSONataTokenType("LPARENTH");
  IElementType MINUS = new JSONataTokenType("MINUS");
  IElementType NEW_LINE = new JSONataTokenType("NEW_LINE");
  IElementType NOT_EQ = new JSONataTokenType("NOT_EQ");
  IElementType NUMBER = new JSONataTokenType("NUMBER");
  IElementType OR = new JSONataTokenType("OR");
  IElementType ORDER_BY = new JSONataTokenType("ORDER_BY");
  IElementType PLUS = new JSONataTokenType("PLUS");
  IElementType POSITIONAL = new JSONataTokenType("POSITIONAL");
  IElementType QUESTION_MARK = new JSONataTokenType("QUESTION_MARK");
  IElementType QUOTE = new JSONataTokenType("QUOTE");
  IElementType QUOTIENT = new JSONataTokenType("QUOTIENT");
  IElementType RBRACE = new JSONataTokenType("RBRACE");
  IElementType RBRACKET = new JSONataTokenType("RBRACKET");
  IElementType REGEX = new JSONataTokenType("REGEX");
  IElementType REMAINDER = new JSONataTokenType("REMAINDER");
  IElementType RPARENTH = new JSONataTokenType("RPARENTH");
  IElementType SEMICOLON = new JSONataTokenType("SEMICOLON");
  IElementType STRING = new JSONataTokenType("STRING");
  IElementType SYS_VARIABLE = new JSONataTokenType("SYS_VARIABLE");
  IElementType VARIABLE = new JSONataTokenType("VARIABLE");
  IElementType VERTICAL_BAR = new JSONataTokenType("VERTICAL_BAR");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ARGUMENTS) {
        return new JSONataArgumentsImpl(node);
      }
      else if (type == ARRAY) {
        return new JSONataArrayImpl(node);
      }
      else if (type == BLOCK) {
        return new JSONataBlockImpl(node);
      }
      else if (type == BLOCK_BODY) {
        return new JSONataBlockBodyImpl(node);
      }
      else if (type == BLOCK_ITEM) {
        return new JSONataBlockItemImpl(node);
      }
      else if (type == CALL) {
        return new JSONataCallImpl(node);
      }
      else if (type == CALL_PARAMS) {
        return new JSONataCallParamsImpl(node);
      }
      else if (type == DECL_FUNCTION) {
        return new JSONataDeclFunctionImpl(node);
      }
      else if (type == DEEP) {
        return new JSONataDeepImpl(node);
      }
      else if (type == FUNC_PARAMS) {
        return new JSONataFuncParamsImpl(node);
      }
      else if (type == JSONATA) {
        return new JSONataJsonataImpl(node);
      }
      else if (type == MODIFIER) {
        return new JSONataModifierImpl(node);
      }
      else if (type == OBJECT) {
        return new JSONataObjectImpl(node);
      }
      else if (type == OPERATORS) {
        return new JSONataOperatorsImpl(node);
      }
      else if (type == PROP) {
        return new JSONataPropImpl(node);
      }
      else if (type == RANGE) {
        return new JSONataRangeImpl(node);
      }
      else if (type == SET_VARIABLE) {
        return new JSONataSetVariableImpl(node);
      }
      else if (type == SYS_FUNCTION) {
        return new JSONataSysFunctionImpl(node);
      }
      else if (type == TRANSFORM) {
        return new JSONataTransformImpl(node);
      }
      else if (type == TRANSFORM_DO) {
        return new JSONataTransformDoImpl(node);
      }
      else if (type == TRANSFORM_LOCATION) {
        return new JSONataTransformLocationImpl(node);
      }
      else if (type == UNUSED_IN_BNF) {
        return new JSONataUnusedInBnfImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}

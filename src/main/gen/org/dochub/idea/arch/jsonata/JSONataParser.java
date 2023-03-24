// This is a generated file. Not intended for manual editing.
package org.dochub.idea.arch.jsonata;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static org.dochub.idea.arch.jsonata.psi.JSONataTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class JSONataParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType root_, PsiBuilder builder_) {
    parseLight(root_, builder_);
    return builder_.getTreeBuilt();
  }

  public void parseLight(IElementType root_, PsiBuilder builder_) {
    boolean result_;
    builder_ = adapt_builder_(root_, builder_, this, null);
    Marker marker_ = enter_section_(builder_, 0, _COLLAPSE_, null);
    result_ = parse_root_(root_, builder_);
    exit_section_(builder_, 0, marker_, root_, result_, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType root_, PsiBuilder builder_) {
    return parse_root_(root_, builder_, 0);
  }

  static boolean parse_root_(IElementType root_, PsiBuilder builder_, int level_) {
    return root(builder_, level_ + 1);
  }

  /* ********************************************************** */
  // (NUMBER | call | deep | block | object) [modifier]
  public static boolean arguments(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "arguments")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, ARGUMENTS, "<arguments>");
    result_ = arguments_0(builder_, level_ + 1);
    result_ = result_ && arguments_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // NUMBER | call | deep | block | object
  private static boolean arguments_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "arguments_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, NUMBER);
    if (!result_) result_ = call(builder_, level_ + 1);
    if (!result_) result_ = deep(builder_, level_ + 1);
    if (!result_) result_ = block(builder_, level_ + 1);
    if (!result_) result_ = object(builder_, level_ + 1);
    return result_;
  }

  // [modifier]
  private static boolean arguments_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "arguments_1")) return false;
    modifier(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // '[' [!']' jsonata (!']' [COMMA] jsonata) *] ']'
  public static boolean array(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "array")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, ARRAY, "<array>");
    result_ = consumeToken(builder_, "[");
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, array_1(builder_, level_ + 1));
    result_ = pinned_ && consumeToken(builder_, "]") && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // [!']' jsonata (!']' [COMMA] jsonata) *]
  private static boolean array_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "array_1")) return false;
    array_1_0(builder_, level_ + 1);
    return true;
  }

  // !']' jsonata (!']' [COMMA] jsonata) *
  private static boolean array_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "array_1_0")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = array_1_0_0(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, jsonata(builder_, level_ + 1));
    result_ = pinned_ && array_1_0_2(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // !']'
  private static boolean array_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "array_1_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, "]");
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (!']' [COMMA] jsonata) *
  private static boolean array_1_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "array_1_0_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!array_1_0_2_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "array_1_0_2", pos_)) break;
    }
    return true;
  }

  // !']' [COMMA] jsonata
  private static boolean array_1_0_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "array_1_0_2_0")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = array_1_0_2_0_0(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, array_1_0_2_0_1(builder_, level_ + 1));
    result_ = pinned_ && jsonata(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // !']'
  private static boolean array_1_0_2_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "array_1_0_2_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, "]");
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // [COMMA]
  private static boolean array_1_0_2_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "array_1_0_2_0_1")) return false;
    consumeToken(builder_, COMMA);
    return true;
  }

  /* ********************************************************** */
  // LBRACKET jsonata DOUBLE_DOT jsonata RBRACKET
  static boolean base_range(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "base_range")) return false;
    if (!nextTokenIs(builder_, LBRACKET)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LBRACKET);
    result_ = result_ && jsonata(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, DOUBLE_DOT);
    result_ = result_ && jsonata(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RBRACKET);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // LPARENTH [block_body] [SEMICOLON] RPARENTH
  public static boolean block(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "block")) return false;
    if (!nextTokenIs(builder_, LPARENTH)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPARENTH);
    result_ = result_ && block_1(builder_, level_ + 1);
    result_ = result_ && block_2(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPARENTH);
    exit_section_(builder_, marker_, BLOCK, result_);
    return result_;
  }

  // [block_body]
  private static boolean block_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "block_1")) return false;
    block_body(builder_, level_ + 1);
    return true;
  }

  // [SEMICOLON]
  private static boolean block_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "block_2")) return false;
    consumeToken(builder_, SEMICOLON);
    return true;
  }

  /* ********************************************************** */
  // block_item ( [SEMICOLON] block_item )*
  public static boolean block_body(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "block_body")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, BLOCK_BODY, "<block body>");
    result_ = block_item(builder_, level_ + 1);
    result_ = result_ && block_body_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // ( [SEMICOLON] block_item )*
  private static boolean block_body_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "block_body_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!block_body_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "block_body_1", pos_)) break;
    }
    return true;
  }

  // [SEMICOLON] block_item
  private static boolean block_body_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "block_body_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = block_body_1_0_0(builder_, level_ + 1);
    result_ = result_ && block_item(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // [SEMICOLON]
  private static boolean block_body_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "block_body_1_0_0")) return false;
    consumeToken(builder_, SEMICOLON);
    return true;
  }

  /* ********************************************************** */
  // decl_function | set_variable | sys_function | jsonata
  public static boolean block_item(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "block_item")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, BLOCK_ITEM, "<block item>");
    result_ = decl_function(builder_, level_ + 1);
    if (!result_) result_ = set_variable(builder_, level_ + 1);
    if (!result_) result_ = sys_function(builder_, level_ + 1);
    if (!result_) result_ = jsonata(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // variable + '(' call_params ')'
  public static boolean call(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "call")) return false;
    if (!nextTokenIs(builder_, VARIABLE)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = call_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, "(");
    result_ = result_ && call_params(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, ")");
    exit_section_(builder_, marker_, CALL, result_);
    return result_;
  }

  // variable +
  private static boolean call_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "call_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, VARIABLE);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!consumeToken(builder_, VARIABLE)) break;
      if (!empty_element_parsed_guard_(builder_, "call_0", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // [jsonata (',' jsonata) *]
  public static boolean call_params(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "call_params")) return false;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, CALL_PARAMS, "<call params>");
    call_params_0(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, true, false, null);
    return true;
  }

  // jsonata (',' jsonata) *
  private static boolean call_params_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "call_params_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = jsonata(builder_, level_ + 1);
    result_ = result_ && call_params_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (',' jsonata) *
  private static boolean call_params_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "call_params_0_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!call_params_0_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "call_params_0_1", pos_)) break;
    }
    return true;
  }

  // ',' jsonata
  private static boolean call_params_0_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "call_params_0_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ",");
    result_ = result_ && jsonata(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // EQ | NOT_EQ | GREATER | LESS | GREATER_OR_EQUAL | LESS_OR_EQUAL | IN | AND | OR
  static boolean comp_operators(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "comp_operators")) return false;
    boolean result_;
    result_ = consumeToken(builder_, EQ);
    if (!result_) result_ = consumeToken(builder_, NOT_EQ);
    if (!result_) result_ = consumeToken(builder_, GREATER);
    if (!result_) result_ = consumeToken(builder_, LESS);
    if (!result_) result_ = consumeToken(builder_, GREATER_OR_EQUAL);
    if (!result_) result_ = consumeToken(builder_, LESS_OR_EQUAL);
    if (!result_) result_ = consumeToken(builder_, IN);
    if (!result_) result_ = consumeToken(builder_, AND);
    if (!result_) result_ = consumeToken(builder_, OR);
    return result_;
  }

  /* ********************************************************** */
  // LBRACKET jsonata DOUBLE_DOT sys_function RBRACKET
  static boolean complex_range(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "complex_range")) return false;
    if (!nextTokenIs(builder_, LBRACKET)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LBRACKET);
    result_ = result_ && jsonata(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, DOUBLE_DOT);
    result_ = result_ && sys_function(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RBRACKET);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // CONCAT
  static boolean concat_operators(PsiBuilder builder_, int level_) {
    return consumeToken(builder_, CONCAT);
  }

  /* ********************************************************** */
  // '?' jsonata ':' [jsonata]
  static boolean cond_operators(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "cond_operators")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, "?");
    result_ = result_ && jsonata(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, ":");
    result_ = result_ && cond_operators_3(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // [jsonata]
  private static boolean cond_operators_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "cond_operators_3")) return false;
    jsonata(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // CONTEXT VARIABLE
  static boolean context_bind_self(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "context_bind_self")) return false;
    if (!nextTokenIs(builder_, CONTEXT)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, CONTEXT, VARIABLE);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // variable ':=' FUNCTION '(' func_params ')' '{' jsonata '}'
  public static boolean decl_function(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "decl_function")) return false;
    if (!nextTokenIs(builder_, VARIABLE)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, VARIABLE);
    result_ = result_ && consumeToken(builder_, ":=");
    result_ = result_ && consumeToken(builder_, FUNCTION);
    result_ = result_ && consumeToken(builder_, "(");
    result_ = result_ && func_params(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, ")");
    result_ = result_ && consumeToken(builder_, "{");
    result_ = result_ && jsonata(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, "}");
    exit_section_(builder_, marker_, DECL_FUNCTION, result_);
    return result_;
  }

  /* ********************************************************** */
  // deep_left ('.' ['('] (id | SYS_VARIABLE | call | array | object ) [modifier])*
  public static boolean deep(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "deep")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, DEEP, "<deep>");
    result_ = deep_left(builder_, level_ + 1);
    result_ = result_ && deep_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // ('.' ['('] (id | SYS_VARIABLE | call | array | object ) [modifier])*
  private static boolean deep_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "deep_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!deep_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "deep_1", pos_)) break;
    }
    return true;
  }

  // '.' ['('] (id | SYS_VARIABLE | call | array | object ) [modifier]
  private static boolean deep_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "deep_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ".");
    result_ = result_ && deep_1_0_1(builder_, level_ + 1);
    result_ = result_ && deep_1_0_2(builder_, level_ + 1);
    result_ = result_ && deep_1_0_3(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ['(']
  private static boolean deep_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "deep_1_0_1")) return false;
    consumeToken(builder_, "(");
    return true;
  }

  // id | SYS_VARIABLE | call | array | object
  private static boolean deep_1_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "deep_1_0_2")) return false;
    boolean result_;
    result_ = consumeToken(builder_, ID);
    if (!result_) result_ = consumeToken(builder_, SYS_VARIABLE);
    if (!result_) result_ = call(builder_, level_ + 1);
    if (!result_) result_ = array(builder_, level_ + 1);
    if (!result_) result_ = object(builder_, level_ + 1);
    return result_;
  }

  // [modifier]
  private static boolean deep_1_0_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "deep_1_0_3")) return false;
    modifier(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // (ID | VARIABLE | SYS_VARIABLE | STRING | block | call | range | array | object) [modifier]
  static boolean deep_left(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "deep_left")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = deep_left_0(builder_, level_ + 1);
    result_ = result_ && deep_left_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ID | VARIABLE | SYS_VARIABLE | STRING | block | call | range | array | object
  private static boolean deep_left_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "deep_left_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, ID);
    if (!result_) result_ = consumeToken(builder_, VARIABLE);
    if (!result_) result_ = consumeToken(builder_, SYS_VARIABLE);
    if (!result_) result_ = consumeToken(builder_, STRING);
    if (!result_) result_ = block(builder_, level_ + 1);
    if (!result_) result_ = call(builder_, level_ + 1);
    if (!result_) result_ = range(builder_, level_ + 1);
    if (!result_) result_ = array(builder_, level_ + 1);
    if (!result_) result_ = object(builder_, level_ + 1);
    return result_;
  }

  // [modifier]
  private static boolean deep_left_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "deep_left_1")) return false;
    modifier(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // '*''*'
  static boolean descendants(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "descendants")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, "*");
    result_ = result_ && consumeToken(builder_, "*");
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // [variable (',' variable) *]
  public static boolean func_params(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_params")) return false;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, FUNC_PARAMS, "<func params>");
    func_params_0(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, true, false, null);
    return true;
  }

  // variable (',' variable) *
  private static boolean func_params_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_params_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, VARIABLE);
    result_ = result_ && func_params_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (',' variable) *
  private static boolean func_params_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_params_0_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!func_params_0_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "func_params_0_1", pos_)) break;
    }
    return true;
  }

  // ',' variable
  private static boolean func_params_0_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_params_0_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ",");
    result_ = result_ && consumeToken(builder_, VARIABLE);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // REGEX | CHAIN | transform | arguments (operators arguments | transform_do)*
  public static boolean jsonata(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "jsonata")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, JSONATA, "<jsonata>");
    result_ = consumeToken(builder_, REGEX);
    if (!result_) result_ = consumeToken(builder_, CHAIN);
    if (!result_) result_ = transform(builder_, level_ + 1);
    if (!result_) result_ = jsonata_3(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // arguments (operators arguments | transform_do)*
  private static boolean jsonata_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "jsonata_3")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = arguments(builder_, level_ + 1);
    result_ = result_ && jsonata_3_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (operators arguments | transform_do)*
  private static boolean jsonata_3_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "jsonata_3_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!jsonata_3_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "jsonata_3_1", pos_)) break;
    }
    return true;
  }

  // operators arguments | transform_do
  private static boolean jsonata_3_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "jsonata_3_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = jsonata_3_1_0_0(builder_, level_ + 1);
    if (!result_) result_ = transform_do(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // operators arguments
  private static boolean jsonata_3_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "jsonata_3_1_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = operators(builder_, level_ + 1);
    result_ = result_ && arguments(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // (orderBy | descendants | parent | positional_bind | context_bind_self) *
  public static boolean modifier(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "modifier")) return false;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, MODIFIER, "<modifier>");
    while (true) {
      int pos_ = current_position_(builder_);
      if (!modifier_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "modifier", pos_)) break;
    }
    exit_section_(builder_, level_, marker_, true, false, null);
    return true;
  }

  // orderBy | descendants | parent | positional_bind | context_bind_self
  private static boolean modifier_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "modifier_0")) return false;
    boolean result_;
    result_ = orderBy(builder_, level_ + 1);
    if (!result_) result_ = descendants(builder_, level_ + 1);
    if (!result_) result_ = parent(builder_, level_ + 1);
    if (!result_) result_ = positional_bind(builder_, level_ + 1);
    if (!result_) result_ = context_bind_self(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // PLUS | MINUS | '*' | QUOTIENT | REMAINDER | range
  static boolean numeric_operators(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "numeric_operators")) return false;
    boolean result_;
    result_ = consumeToken(builder_, PLUS);
    if (!result_) result_ = consumeToken(builder_, MINUS);
    if (!result_) result_ = consumeToken(builder_, "*");
    if (!result_) result_ = consumeToken(builder_, QUOTIENT);
    if (!result_) result_ = consumeToken(builder_, REMAINDER);
    if (!result_) result_ = range(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // LBRACE [!RBRACE prop  (!RBRACE COMMA prop) *] RBRACE
  public static boolean object(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "object")) return false;
    if (!nextTokenIs(builder_, LBRACE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, OBJECT, null);
    result_ = consumeToken(builder_, LBRACE);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, object_1(builder_, level_ + 1));
    result_ = pinned_ && consumeToken(builder_, RBRACE) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // [!RBRACE prop  (!RBRACE COMMA prop) *]
  private static boolean object_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "object_1")) return false;
    object_1_0(builder_, level_ + 1);
    return true;
  }

  // !RBRACE prop  (!RBRACE COMMA prop) *
  private static boolean object_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "object_1_0")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = object_1_0_0(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, prop(builder_, level_ + 1));
    result_ = pinned_ && object_1_0_2(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // !RBRACE
  private static boolean object_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "object_1_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, RBRACE);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (!RBRACE COMMA prop) *
  private static boolean object_1_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "object_1_0_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!object_1_0_2_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "object_1_0_2", pos_)) break;
    }
    return true;
  }

  // !RBRACE COMMA prop
  private static boolean object_1_0_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "object_1_0_2_0")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = object_1_0_2_0_0(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, consumeToken(builder_, COMMA));
    result_ = pinned_ && prop(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // !RBRACE
  private static boolean object_1_0_2_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "object_1_0_2_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, RBRACE);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // cond_operators | numeric_operators | comp_operators | concat_operators | COLON
  public static boolean operators(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "operators")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, OPERATORS, "<operators>");
    result_ = cond_operators(builder_, level_ + 1);
    if (!result_) result_ = numeric_operators(builder_, level_ + 1);
    if (!result_) result_ = comp_operators(builder_, level_ + 1);
    if (!result_) result_ = concat_operators(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, COLON);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // ORDER_BY LPARENTH (['>'|'<'] jsonata (COMMA ['>'|'<'] jsonata)*) RPARENTH
  static boolean orderBy(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "orderBy")) return false;
    if (!nextTokenIs(builder_, ORDER_BY)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, ORDER_BY, LPARENTH);
    result_ = result_ && orderBy_2(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPARENTH);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ['>'|'<'] jsonata (COMMA ['>'|'<'] jsonata)*
  private static boolean orderBy_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "orderBy_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = orderBy_2_0(builder_, level_ + 1);
    result_ = result_ && jsonata(builder_, level_ + 1);
    result_ = result_ && orderBy_2_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ['>'|'<']
  private static boolean orderBy_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "orderBy_2_0")) return false;
    orderBy_2_0_0(builder_, level_ + 1);
    return true;
  }

  // '>'|'<'
  private static boolean orderBy_2_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "orderBy_2_0_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, ">");
    if (!result_) result_ = consumeToken(builder_, "<");
    return result_;
  }

  // (COMMA ['>'|'<'] jsonata)*
  private static boolean orderBy_2_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "orderBy_2_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!orderBy_2_2_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "orderBy_2_2", pos_)) break;
    }
    return true;
  }

  // COMMA ['>'|'<'] jsonata
  private static boolean orderBy_2_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "orderBy_2_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && orderBy_2_2_0_1(builder_, level_ + 1);
    result_ = result_ && jsonata(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ['>'|'<']
  private static boolean orderBy_2_2_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "orderBy_2_2_0_1")) return false;
    orderBy_2_2_0_1_0(builder_, level_ + 1);
    return true;
  }

  // '>'|'<'
  private static boolean orderBy_2_2_0_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "orderBy_2_2_0_1_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, ">");
    if (!result_) result_ = consumeToken(builder_, "<");
    return result_;
  }

  /* ********************************************************** */
  // '%' DOT jsonata
  static boolean parent(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "parent")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, "%");
    result_ = result_ && consumeToken(builder_, DOT);
    result_ = result_ && jsonata(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // POSITIONAL VARIABLE
  static boolean positional_bind(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "positional_bind")) return false;
    if (!nextTokenIs(builder_, POSITIONAL)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, POSITIONAL, VARIABLE);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // [] (ID | STRING | call) COLON (jsonata)*
  public static boolean prop(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "prop")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, PROP, "<prop>");
    result_ = prop_0(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, prop_1(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, consumeToken(builder_, COLON)) && result_;
    result_ = pinned_ && prop_3(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // []
  private static boolean prop_0(PsiBuilder builder_, int level_) {
    return true;
  }

  // ID | STRING | call
  private static boolean prop_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "prop_1")) return false;
    boolean result_;
    result_ = consumeToken(builder_, ID);
    if (!result_) result_ = consumeToken(builder_, STRING);
    if (!result_) result_ = call(builder_, level_ + 1);
    return result_;
  }

  // (jsonata)*
  private static boolean prop_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "prop_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!prop_3_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "prop_3", pos_)) break;
    }
    return true;
  }

  // (jsonata)
  private static boolean prop_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "prop_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = jsonata(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // base_range | simple_range | complex_range
  public static boolean range(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "range")) return false;
    if (!nextTokenIs(builder_, LBRACKET)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = base_range(builder_, level_ + 1);
    if (!result_) result_ = simple_range(builder_, level_ + 1);
    if (!result_) result_ = complex_range(builder_, level_ + 1);
    exit_section_(builder_, marker_, RANGE, result_);
    return result_;
  }

  /* ********************************************************** */
  // !<<eof>> jsonata
  static boolean root(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "root")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = root_0(builder_, level_ + 1);
    result_ = result_ && jsonata(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !<<eof>>
  private static boolean root_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "root_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !eof(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // VARIABLE ASSIGN jsonata
  public static boolean set_variable(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "set_variable")) return false;
    if (!nextTokenIs(builder_, VARIABLE)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, VARIABLE, ASSIGN);
    result_ = result_ && jsonata(builder_, level_ + 1);
    exit_section_(builder_, marker_, SET_VARIABLE, result_);
    return result_;
  }

  /* ********************************************************** */
  // LBRACKET jsonata DOUBLE_DOT jsonata (COMMA jsonata DOUBLE_DOT jsonata)* RBRACKET
  static boolean simple_range(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "simple_range")) return false;
    if (!nextTokenIs(builder_, LBRACKET)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LBRACKET);
    result_ = result_ && jsonata(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, DOUBLE_DOT);
    result_ = result_ && jsonata(builder_, level_ + 1);
    result_ = result_ && simple_range_4(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RBRACKET);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (COMMA jsonata DOUBLE_DOT jsonata)*
  private static boolean simple_range_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "simple_range_4")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!simple_range_4_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "simple_range_4", pos_)) break;
    }
    return true;
  }

  // COMMA jsonata DOUBLE_DOT jsonata
  private static boolean simple_range_4_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "simple_range_4_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && jsonata(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, DOUBLE_DOT);
    result_ = result_ && jsonata(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // VARIABLE LPARENTH [jsonata] RPARENTH
  public static boolean sys_function(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sys_function")) return false;
    if (!nextTokenIs(builder_, VARIABLE)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, VARIABLE, LPARENTH);
    result_ = result_ && sys_function_2(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPARENTH);
    exit_section_(builder_, marker_, SYS_FUNCTION, result_);
    return result_;
  }

  // [jsonata]
  private static boolean sys_function_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "sys_function_2")) return false;
    jsonata(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // VERTICAL_BAR transform_location VERTICAL_BAR jsonata [COMMA  array] VERTICAL_BAR
  public static boolean transform(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "transform")) return false;
    if (!nextTokenIs(builder_, VERTICAL_BAR)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, VERTICAL_BAR);
    result_ = result_ && transform_location(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, VERTICAL_BAR);
    result_ = result_ && jsonata(builder_, level_ + 1);
    result_ = result_ && transform_4(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, VERTICAL_BAR);
    exit_section_(builder_, marker_, TRANSFORM, result_);
    return result_;
  }

  // [COMMA  array]
  private static boolean transform_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "transform_4")) return false;
    transform_4_0(builder_, level_ + 1);
    return true;
  }

  // COMMA  array
  private static boolean transform_4_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "transform_4_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && array(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // CHAIN (transform | call | variable)
  public static boolean transform_do(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "transform_do")) return false;
    if (!nextTokenIs(builder_, CHAIN)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, CHAIN);
    result_ = result_ && transform_do_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, TRANSFORM_DO, result_);
    return result_;
  }

  // transform | call | variable
  private static boolean transform_do_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "transform_do_1")) return false;
    boolean result_;
    result_ = transform(builder_, level_ + 1);
    if (!result_) result_ = call(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, VARIABLE);
    return result_;
  }

  /* ********************************************************** */
  // id ('.' id)*
  public static boolean transform_location(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "transform_location")) return false;
    if (!nextTokenIs(builder_, ID)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ID);
    result_ = result_ && transform_location_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, TRANSFORM_LOCATION, result_);
    return result_;
  }

  // ('.' id)*
  private static boolean transform_location_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "transform_location_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!transform_location_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "transform_location_1", pos_)) break;
    }
    return true;
  }

  // '.' id
  private static boolean transform_location_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "transform_location_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ".");
    result_ = result_ && consumeToken(builder_, ID);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // COMMENT | NEW_LINE | DOUBLE_QUOTE | BACKSLASH | QUOTE | BACKTICK | BOOLEAN | QUESTION_MARK
  public static boolean unusedInBnf(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "unusedInBnf")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, UNUSED_IN_BNF, "<unused in bnf>");
    result_ = consumeToken(builder_, COMMENT);
    if (!result_) result_ = consumeToken(builder_, NEW_LINE);
    if (!result_) result_ = consumeToken(builder_, DOUBLE_QUOTE);
    if (!result_) result_ = consumeToken(builder_, BACKSLASH);
    if (!result_) result_ = consumeToken(builder_, QUOTE);
    if (!result_) result_ = consumeToken(builder_, BACKTICK);
    if (!result_) result_ = consumeToken(builder_, BOOLEAN);
    if (!result_) result_ = consumeToken(builder_, QUESTION_MARK);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

}

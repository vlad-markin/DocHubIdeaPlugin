package org.dochub.idea.arch.jsonata.lexer;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;

import com.intellij.lexer.FlexLexer;

import org.dochub.idea.arch.jsonata.psi.JSONataTypes;



%%

%{
  public JSONataLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class JSONataLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode



white_space         = \s+
/* comment */
comment             = "/*"~"*/"

space               = [ \t\n\x0B\f\r]+
/* string literal */
escapeSequence      = {backslash}\S
string              = \"([^\"\\] | {escapeSequence})*\"|'([^\'\\] | {escapeSequence})*'|\`([^\`\\] | {escapeSequence})*\`
/* number literal */
number              = (0|[1-9][[:digit:]]*)(\.[[:digit:]]+)?([eE][+-]?[[:digit:]]+)?
boolean             = true|false|null
/* identifier */
id                  = [a-zA-Z_0-9]+
variable            = \${id}?
/* regex literal */
regex               = \/([^\/\\\n\r] | {escapeSequence})*\/[im]{0,2}

/* operators */
colon               = :
semicolon           = ;
dot                 = \.
double_dot          = \.\.
comma               = \,
concat              = &
order_by            = "^"
positional          = "#"
context             = "@"
chain               = "~>"
root                = \$\$
assign              = :=
question_mark       = \?
plus                = \+
minus               = \-
descendants         = "**"
mul                 = "*"
quotient            = \/
remainder           = \%
vertical_bar        = \|
backslash           = \\
eq                  = "="
not_eq              = "!="
less                = <
greater             = >
less_or_equal       = <=
greater_or_equal    = >=

/* parentheses */
lparenth            = \(
rparenth            = \)
lbracket            = \[
rbracket            = \]
lbrace              = \{
rbrace              = \}

/* function */
function            = "function" | \u03BB


%%
    <YYINITIAL> {
    // {white_space} - имя правила { return WHITE_SPACE; } - токен

        /* keywords */
        "in"                    { return JSONataTypes.IN; }
        "and"                   { return JSONataTypes.AND; }
        "or"                    { return JSONataTypes.OR; }

        {function}              { return JSONataTypes.FUNCTION; }
        {white_space}           { return TokenType.WHITE_SPACE; }
        {comment}               { return JSONataTypes.COMMENT; }
        {space}                 { return JSONataTypes.SPACE; }
        {string}                { return JSONataTypes.STRING; }
        {number}                { return JSONataTypes.NUMBER; }
        {boolean}               { return JSONataTypes.BOOLEAN; }
        {id}                    { return JSONataTypes.ID; }
        {variable}              { return JSONataTypes.VARIABLE; }
        {regex}                 { return JSONataTypes.REGEX; }

        /* special symbols */
        {colon}                 { return JSONataTypes.COLON; }
        {semicolon}             { return JSONataTypes.SEMICOLON; }
        {dot}                   { return JSONataTypes.DOT; }
        {double_dot}            { return JSONataTypes.DOUBLE_DOT; }
        {comma}                 { return JSONataTypes.COMMA; }
        {concat}                { return JSONataTypes.CONCAT; }
        {order_by}              { return JSONataTypes.ORDER_BY; }
        {positional}            { return JSONataTypes.POSITIONAL; }
        {context}               { return JSONataTypes.CONTEXT; }
        {chain}                 { return JSONataTypes.CHAIN; }
        {root}                  { return JSONataTypes.ROOT; }
        {assign}                { return JSONataTypes.ASSIGN; }
        {question_mark}         { return JSONataTypes.QUESTION_MARK; }
        {plus}                  { return JSONataTypes.PLUS; }
        {minus}                 { return JSONataTypes.MINUS; }
        {descendants}           { return JSONataTypes.DESCENDANTS; }
        {mul}                   { return JSONataTypes.MUL; }
        {quotient}              { return JSONataTypes.QUOTIENT; }
        {vertical_bar}          { return JSONataTypes.VERTICAL_BAR; }
        {remainder}             { return JSONataTypes.REMAINDER; }
        {backslash}             { return JSONataTypes.BACKSLASH; }
        {eq}                    { return JSONataTypes.EQ; }
        {not_eq}                { return JSONataTypes.NOT_EQ; }
        {less}                  { return JSONataTypes.LESS; }
        {greater}               { return JSONataTypes.GREATER; }
        {less_or_equal}         { return JSONataTypes.LESS_OR_EQUAL; }
        {greater_or_equal}      { return JSONataTypes.GREATER_OR_EQUAL; }

//        /* parentheses */
        {lparenth}              { return JSONataTypes.LPARENTH; }
        {rparenth}              { return JSONataTypes.RPARENTH; }
        {lbrace}                { return JSONataTypes.LBRACE; }
        {rbrace}                { return JSONataTypes.RBRACE; }
        {lbracket}              { return JSONataTypes.LBRACKET; }
        {rbracket}              { return JSONataTypes.RBRACKET; }

}

        [^]                     { return TokenType.BAD_CHARACTER; }

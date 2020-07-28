package lexical;
import java.util.LinkedHashSet;
import syntax.Lexical;
import java.io.IOException;
%%

%public
%class LexicalAnalyzer
%line
%column
%unicode
%function next_token
%type Symbol
%state STRING
%state CHAR

%{
    StringBuilder stringBuilder = new StringBuilder();
    boolean record_dcl = false;
    boolean record_id_dcl = false;
    LinkedHashSet<String> record_ids = new LinkedHashSet<String>();
    LinkedHashSet<String> record_types = new LinkedHashSet<String>();
    private Symbol currentSymbol = null;
    public Symbol currentToken() {
            return currentSymbol;
        }
    public String nextToken() {
        try {
            currentSymbol = next_token();
            return currentSymbol.getToken();
        } catch (IOException e) {
            throw new RuntimeException("Unable to get next token", e);
        }
    }
%}

/* Special Cases */
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteCharacter = [ \t\f]
WhiteSpace = {LineTerminator} | {WhiteCharacter}

/* string and character literals */
StringCharacter = [^\r\n\"\\]
SingleCharacter = [^\r\n\'\\]

/* Comments */
MultiLineComment = "/*" ~"*/"
SingleLineComment = "//" {InputCharacter}* {LineTerminator}?
Comment = {MultiLineComment} | {SingleLineComment}

/* Identifiers */
Identifier = [:jletter:][:jletterdigit:]*


/* Integer Numbers */
DecIntegerLiteral = 0 | [1-9][0-9]*
DecLongLiteral    = {DecIntegerLiteral} [lL]
HexIntegerLiteral = 0 [xX] 0* {HexDigit} {1,8}
HexDigit          = [0-9a-fA-F]
/* Real Numbers */
DoubleNumber = [0-9]+[.][0-9]* | [.][0-9]+ | [0-9]+
FloatNumber = {DoubleNumber}[fF]
RealNumber = {FloatNumber} | {DoubleNumber}
ScientificNotation = {RealNumber}[e][+-]?{DecIntegerLiteral}

/* Special Characters */
SpecialCharacter = "\\b" | "\\t" | "\\n" | "\\f" | "\\r" | "\\\"" | "\\'" | "\\\\"


%%
<YYINITIAL> {

  /* keywords */
  "start"                        { return (new Symbol("start"));}
  "begin"                        { return (new Symbol("begin")); }
  "bool"                         { return (new Symbol("type",yytext())); }
  "break"                        { return (new Symbol("break")); }
  "case"                         { return (new Symbol("case")); }
  "char"                         { return (new Symbol("type", yytext())); }
  "const"                        { return (new Symbol("const")); }
  "continue"                     { return (new Symbol("continue")); }
  "default"                      { return (new Symbol("default")); }
  "double"                       { return (new Symbol("type", yytext())); }
  "else"                         { return (new Symbol("else")); }
  "end"                          { return (new Symbol("end"));}
  "function"                     { return (new Symbol("function")); }
  "float"                        { return (new Symbol("type", yytext())); }
  "for"                          { return (new Symbol("for"));}
  "repeat"                       { return (new Symbol("repeat")); }
  "foreach"                       { return (new Symbol("foreach")); }
  "false"                      { return (new Symbol("false", Boolean.valueOf(yytext()))); }
  "int"                          { return (new Symbol("type", yytext())); }
  "short"                         { return (new Symbol("type", yytext())); }
  "long"                         { return (new Symbol("type", yytext())); }
  "if"                           { return (new Symbol("if")); }
  "sizeof"                       { return (new Symbol("sizeof")); }
  "len"                       { return (new Symbol("len")); }
  "string"                       { return (new Symbol("type",yytext())); }
  "void"                       { return (new Symbol("void")); }
  "auto"                       { return (new Symbol("auto")); }
  "in"                       { return (new Symbol("in")); }
  "until"                       { return (new Symbol("until")); }
  "switch"                       { return (new Symbol("switch")); }
  "record"                     { record_dcl = true; return (new Symbol("record")); }
  "while"                     { return (new Symbol("while")); }
  "do"                     { return (new Symbol("do")); }
  "return"                       { return (new Symbol("return")); }
  "true"                         { return (new Symbol("true", Boolean.valueOf(yytext()))); }
  "volatile"                         { return (new Symbol("volatile")); }
  "static"                         { return (new Symbol("static")); }
  "goto"                         { return (new Symbol("goto")); }
  "signed"                         { return (new Symbol("signed")); }
  "new"                         { return (new Symbol("new")); }
  "println"                         { return (new Symbol("println")); }
  "input"                        { return (new Symbol("input")); }



  /* separators */

  "("                            {  return (new Symbol("(")); }
  ")"                            {  return (new Symbol(")")); }
  "{"                            {  return (new Symbol("bracket_open")); }
  "}"                            {  return (new Symbol("bracket_close")); }
  ";"                            {  return (new Symbol(";")); }
  ","                            {  return (new Symbol("comma")); }
  "."                            {  return (new Symbol(".")); }
  "["                            {  return (new Symbol("["));}
  "]"                            {  return (new Symbol("]"));}
  "]["                           {  return (new Symbol("]["));}

  /* operators */

  "="                            {  return (new Symbol("=")); }
  ">"                            {  return (new Symbol(">")); }
  "<"                            {  return (new Symbol("<")); }
  "~"                            {  return (new Symbol("tilda")); }
  ":"                            {  return (new Symbol(":")); }
  "=="                           {  return (new Symbol("==")); }
  "<="                           {  return (new Symbol("<=")); }
  ">="                           {  return (new Symbol(">=")); }
  "!="                           {  return (new Symbol("!=")); }
  "and"                           {  return (new Symbol("and")); }
  "or"                           {  return (new Symbol("or")); }
  "not"                           {  return (new Symbol("not")); }
  "++"                           {  return (new Symbol("++")); }
  "--"                           {  return (new Symbol("--")); }
  "+"                            {  return (new Symbol("+")); }
  "-"                            {  return (new Symbol("-")); }
  "*"                            {  return (new Symbol("*")); }
  "/"                            {  return (new Symbol("/")); }
  "&"                            {  return (new Symbol("&")); }
  "|"                            {  return (new Symbol("|")); }
  "^"                            {  return (new Symbol("^")); }
  "%"                            {  return (new Symbol("%")); }
  "+="                           {  return (new Symbol("+=")); }
  "-="                           {  return (new Symbol("-=")); }
  "*="                           {  return (new Symbol("*=")); }
  "/="                           {  return (new Symbol("/=")); }

  /* string literal */
  \"                             { yybegin(STRING);stringBuilder.setLength(0); stringBuilder.append("\""); }

  /* character literal */
  \'                             { yybegin(CHAR);stringBuilder.setLength(0); stringBuilder.append("'");}

  /* numeric literals */

  {DecIntegerLiteral}            {  return (new Symbol("dec", Integer.valueOf(yytext()))); }
  {DecLongLiteral}               {  return (new Symbol("long_dec",yytext())); }

  {HexIntegerLiteral}            {  return (new Symbol("hex", yytext())); }

  {DoubleNumber}                 {  return (new Symbol("double", Double.valueOf(yytext()))); }
  {FloatNumber}                  {  return (new Symbol("float", Float.valueOf(yytext())));  }
  {ScientificNotation}           {  return (new Symbol("sc_not", yytext())); }

  /* comments */
  {Comment}                      { /*ignore Comment*/ }

 {LineTerminator}                {/*ignore LineTerminator*/}

 {WhiteCharacter}              {/*ignore WhiteCharacter*/}
  /* identifiers */
  {Identifier}                   {   String id = yytext();
                                     if(record_types.contains(id)){
                                         record_dcl = false;
                                         record_id_dcl = true;
                                         return (new Symbol("record_type",id));

                                     } if(record_dcl){
                                         record_types.add(id);
                                         record_dcl = false;
                                         return (new Symbol("record_type",id));
                                     }
                                     if(record_ids.contains(id)){
                                          record_id_dcl = false;
                                          return (new Symbol("record_id",id));
                                      }
                                      if (record_id_dcl)
                                      {
                                          record_id_dcl = false;
                                          record_ids.add(id);
                                          return (new Symbol("record_id",id));
                                      }
                                     return (new Symbol("id",id));}
}
<STRING>
{
    {StringCharacter}+ {stringBuilder.append(yytext());}
    {SpecialCharacter} {stringBuilder.append(yytext());}
    \"                 {yybegin(YYINITIAL); stringBuilder.append("\""); StringBuilder sb = stringBuilder; stringBuilder = new StringBuilder(); return (new Symbol("string", sb.toString()));}

    {LineTerminator}   {throw new RuntimeException("Unexpected End Of Line");}
    \\.                {throw new RuntimeException("Unexpected Escape \""+yytext()+"\"");}
}

<CHAR>
{
    {SingleCharacter}   {return (new Symbol("char", yytext().charAt(0)));}
    {SpecialCharacter}  {return (new Symbol("char", yytext().charAt(0)));}
    \'                  {yybegin(YYINITIAL);}

    {LineTerminator}    {throw new RuntimeException("Unexpected End Of Line");}
    \\.                 {throw new RuntimeException("Unexpected Escape \""+yytext()+"\"");}
}

[^]        { throw new RuntimeException("Illegal character \""+yytext()+
                                        "\" at line "+yyline+", column "+yycolumn); }

<<EOF>>    {return (new Symbol("$"));}
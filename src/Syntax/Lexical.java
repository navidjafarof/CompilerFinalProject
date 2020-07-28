package Syntax;
import Lexical.Symbol;
public interface Lexical {
    String nextToken();
    Symbol currentToken();
}
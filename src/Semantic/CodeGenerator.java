package Semantic;
import Syntax.Lexical;
import Semantic.AST.AST;
import java.util.ArrayDeque;
import java.util.Deque;

public class CodeGenerator implements Syntax.CodeGenerator {
    private Lexical lexical;
    private Deque<Object> semanticStack;
    public CodeGenerator(Lexical lexical) {
        this.lexical = lexical;
        semanticStack = new ArrayDeque<>();
    }
    public AST getResult() {
        return (AST) semanticStack.getFirst();
    }
    @Override
    public void doSemantic(String sem) {
        switch (sem){
            case "push":{
                semanticStack.push(lexical.currentToken().getValue());
                break;
            }
    }
}}

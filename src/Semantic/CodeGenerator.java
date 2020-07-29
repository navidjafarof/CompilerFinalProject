package Semantic;
import Semantic.AST.DCL.FunctionDCL;
import Semantic.SymbolTable.SymbolTable;
import Syntax.Lexical;
import Semantic.AST.AST;
import java.util.ArrayDeque;
import java.util.Deque;
import Semantic.AST.Block.GlobalBlock;

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

            case "setSignature": {
                FunctionDCL functionDcl = (FunctionDCL) semanticStack.pop();
                functionDcl.setSignatureDeclared(true);
                SymbolTable.getInstance().getFunction(functionDcl.getName(),functionDcl.getArgumentTypes()).setSignatureDeclared(true);
                semanticStack.push(functionDcl);
                break;
            }
            case "addFuncDCL": {
                FunctionDCL function = (FunctionDCL) semanticStack.pop();
                function.declare();
                function.setSignature();
                semanticStack.push(function);
                break;
            }
    }


}
    private void addFuncToGlobalBlock(FunctionDCL function) {
        if (GlobalBlock.getInstance().getDeclarations().contains(function)) {
            int index = GlobalBlock.getInstance().getDeclarations().indexOf(function);
            FunctionDCL lastFunc = (FunctionDCL) GlobalBlock.getInstance().getDeclarations().get(index);
            if (lastFunc.getBlock() == null && function.getBlock() != null && lastFunc.getSignatureDeclared()) {
                GlobalBlock.getInstance().getDeclarations().remove(lastFunc);
                GlobalBlock.getInstance().addDeclaration(function);
            } else if (lastFunc.getBlock() != null && lastFunc.getBlock() == null) {
            } else
                throw new RuntimeException("the function is duplicate!!!");
        } else {

            GlobalBlock.getInstance().addDeclaration(function);
        }

    }
}

package Semantic.AST.Statement.Loop;

import Semantic.AST.Block.Block;
import Semantic.AST.Expression.variable.SimpleVariable;
import Semantic.AST.Expression.variable.Variable;
import Semantic.SymbolTable.DSCP.DynamicLocalVariableDSCP;
import Semantic.SymbolTable.SymbolTable;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public class foreach extends Loop{
    private String id;
    private Variable variable;

    foreach(Block block, String id, Variable variable) {
        super(block);
        this.id = id;
        this.variable = variable;
    }

    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
//        String varIteratorName = SymbolTable.getInstance().getTempId();
//        String varCounterName = SymbolTable.getInstance().getTempId();
//        SymbolTable.getInstance().addVariable(varIteratorName, new DynamicLocalVariableDSCP(type, false,
//                SymbolTable.getInstance().getIndex(), false));
//        SimpleVariable varIterator = new SimpleVariable(varIteratorName,)
//        SimpleVariable varCounter = new SimpleVariable(varCounterName);
//        SimpleVariable varIdentifier = new SimpleVariable(identifierName);
//        Label outLabel = new Label();
//        Label conditionLabel = new Label();
//        Label stepLabel = new Label();
    }
}

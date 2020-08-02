package Semantic.AST.DCL.VarDCL;

import Semantic.AST.DCL.Declaration;
import Semantic.AST.Expression.InitialExpression;
import Semantic.AST.Operation;

import org.objectweb.asm.Type;

public abstract class VarDCL implements Operation, InitialExpression, Declaration {
    String name;

    public Type getType() {
        return type;
    }

    Type type;
    boolean global = true;

    public String getName() {
        return name;
    }
}

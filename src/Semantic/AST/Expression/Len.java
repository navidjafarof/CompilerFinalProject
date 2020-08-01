package Semantic.AST.Expression;

import Semantic.AST.Expression.constant.Constant;
import Semantic.AST.Expression.constant.IntegerConstExp;
import Semantic.AST.Expression.constant.StringConstExp;
import Semantic.AST.Expression.variable.Array;
import Semantic.AST.Expression.variable.SimpleVariable;
import Semantic.AST.Expression.variable.Variable;
import Semantic.AST.Operation;

import Semantic.SymbolTable.DSCP.DynamicLocalDSCP;
import Semantic.SymbolTable.SymbolTable;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.*;

public class Len extends Expression implements Operation {

    Expression expression;

    public Len(Expression e) {
        this.expression = e;
    }

    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
        type = Type.INT_TYPE;
        if (expression instanceof Array) {
            int index = ((DynamicLocalDSCP) SymbolTable.getInstance().getDescriptor((((Array) expression).getName()))).getIndex();
            mv.visitVarInsn(ALOAD, index);
            mv.visitInsn(ARRAYLENGTH);
        } else if (expression.getType().equals(Type.getType(String.class))) {
            expression.codegen(cw, mv);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "length", "()I", false);
        } else {
            throw new RuntimeException("Len Argument Is Not Iterable.");
        }
    }
}

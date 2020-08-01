package Semantic.AST.Expression.variable;

import Semantic.AST.Expression.Expression;
import Semantic.AST.Expression.binary.conditional.GreaterThanOrEqualTo;
import Semantic.SymbolTable.DSCP.DSCP;
import Semantic.SymbolTable.DSCP.StaticGlobalArrayDSCP;
import Semantic.SymbolTable.SymbolTable;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.util.ArrayList;

import static org.objectweb.asm.Opcodes.*;


public class Array extends Variable {
    private ArrayList<Expression> indexesExpression;


    public Array(String name, ArrayList<Expression> indexesExpression, Type type) {
        this.name = name;
        this.type = type;
        this.indexesExpression = indexesExpression;
    }


    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
        new SimpleVariable(name, type).codegen(cw, mv);
        Label exceptionLabel = new Label();
        Label endLabel = new Label();

        for (int i = 0; i < indexesExpression.size() - 1; i++) {
            indexesExpression.get(i).codegen(cw, mv);
            DSCP dscp = SymbolTable.getInstance().getDescriptor(name);
            if (dscp instanceof StaticGlobalArrayDSCP) {
                GreaterThanOrEqualTo greaterThanOrEqualTo = new GreaterThanOrEqualTo(indexesExpression.get(i), ((StaticGlobalArrayDSCP) dscp).getDimensionList().get(i));
                greaterThanOrEqualTo.codegen(cw, mv);
                mv.visitJumpInsn(IFGE, exceptionLabel);
            }
            if (!indexesExpression.get(i).getType().equals(Type.INT_TYPE))
                throw new RuntimeException("Index should be an integer number");
            mv.visitInsn(AALOAD);
        }
        // must load the last index separately
        indexesExpression.get(indexesExpression.size() - 1).codegen(cw, mv);
        if (type.getDescriptor().endsWith(";")) // we have array of records
            mv.visitInsn(AALOAD);
        else
            mv.visitInsn(type.getOpcode(IALOAD));

        mv.visitJumpInsn(GOTO, endLabel);
        mv.visitLabel(exceptionLabel);
        mv.visitTypeInsn(NEW, "java/lang/RuntimeException");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/RuntimeException", "<init>", "()V", false);
        mv.visitInsn(ATHROW);
        mv.visitLabel(endLabel);
    }

    public void setIndexesExpression(ArrayList<Expression> indexesExpression) {
        this.indexesExpression = indexesExpression;
    }

    public ArrayList<Expression> getIndexesExpression() {
        return indexesExpression;
    }
}

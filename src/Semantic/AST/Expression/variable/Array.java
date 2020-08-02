package Semantic.AST.Expression.variable;

import Semantic.AST.Expression.Expression;
import Semantic.AST.Expression.binary.conditional.GreaterThanOrEqualTo;
import Semantic.SymbolTable.DSCP.DSCP;
import Semantic.SymbolTable.DSCP.DynamicLocalDSCP;
import Semantic.SymbolTable.DSCP.StaticGlobalArrayDSCP;
import Semantic.SymbolTable.DSCP.StaticGlobalDSCP;
import Semantic.SymbolTable.SymbolTable;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.Locale;

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
        if (getDSCP() instanceof DynamicLocalDSCP) {
            mv.visitVarInsn(ALOAD, ((DynamicLocalDSCP) getDSCP()).getIndex());
        } else if (getDSCP() instanceof StaticGlobalDSCP) {
            StringBuilder arrayType = new StringBuilder();
            arrayType.append("[".repeat(Math.max(0, ((StaticGlobalArrayDSCP) getDSCP()).getDimension()))).append(type.getDescriptor());
            mv.visitFieldInsn(GETSTATIC, "Code", this.name, arrayType.toString());
        }
        indexesExpression.get(0).codegen(cw, mv);
        mv.visitInsn(this.type.getOpcode(IALOAD));
    }

    public void setIndexesExpression(ArrayList<Expression> indexesExpression) {
        this.indexesExpression = indexesExpression;
    }

    public ArrayList<Expression> getIndexesExpression() {
        return indexesExpression;
    }
}

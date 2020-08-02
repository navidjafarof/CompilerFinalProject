package Semantic.AST.Statement.assignment;

import Semantic.AST.Expression.Expression;
import Semantic.AST.Expression.constant.IntegerConstExp;
import Semantic.AST.Expression.variable.Array;
import Semantic.AST.Expression.variable.Variable;
import Semantic.SymbolTable.DSCP.*;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.util.Collections;

import static org.objectweb.asm.Opcodes.*;

public class Assign extends Assignment {
    public Assign(Variable variable, Expression expression) {
        super(variable, expression);
    }

    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
        checkIsConst();
        DSCP dscp = variable.getDSCP();
        if (dscp instanceof DynamicLocalDSCP) {
            int index = ((DynamicLocalDSCP) dscp).getIndex();
            if (dscp instanceof DynamicLocalVariableDSCP) {
                this.expression.codegen(cw, mv);
                if (!variable.getType().equals(expression.getType()))
                    throw new RuntimeException("Mismatching Type In Assignment.");
                mv.visitVarInsn(variable.getType().getOpcode(ISTORE), index);
            } else {
                mv.visitVarInsn(ALOAD, index);
                for (Expression e : ((Array) variable).getIndexesExpression()) {
                    e.codegen(cw, mv);
                }
                this.expression.codegen(cw, mv);
                if (!variable.getType().equals(expression.getType()))
                    throw new RuntimeException("Mismatching Type In Assignment.");
                mv.visitInsn(variable.getType().getOpcode(IASTORE));
            }

        } else {
            if (dscp instanceof StaticGlobalVariableDSCP)
                mv.visitFieldInsn(PUTSTATIC, "Code", variable.getName(), dscp.getType().toString());
            else if (dscp instanceof StaticGlobalArrayDSCP) {
                StringBuilder arrayType = new StringBuilder();
                arrayType.append("[".repeat(Math.max(0, ((StaticGlobalArrayDSCP) dscp).getDimension()))).append(variable.getType().getDescriptor());
                mv.visitFieldInsn(GETSTATIC, "Code", this.variable.getName(), arrayType.toString());
                for (Expression e : ((Array) variable).getIndexesExpression()) {
                    e.codegen(cw, mv);
                }
                this.expression.codegen(cw, mv);
                mv.visitInsn(IASTORE);
            }
        }
        dscp.setValid(true);
    }
}

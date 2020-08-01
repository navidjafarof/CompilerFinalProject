package Semantic.AST.Statement.assignment;

import Semantic.AST.Expression.Expression;
import Semantic.AST.Expression.constant.IntegerConstExp;
import Semantic.AST.Expression.variable.Array;
import Semantic.AST.Expression.variable.Variable;
import Semantic.SymbolTable.DSCP.DSCP;
import Semantic.SymbolTable.DSCP.DynamicLocalArrayDSCP;
import Semantic.SymbolTable.DSCP.DynamicLocalDSCP;
import Semantic.SymbolTable.DSCP.DynamicLocalVariableDSCP;
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
        if (variable.getType() != expression.getType())
            throw new RuntimeException("Mismatching Type In Assignment.");
        if (dscp instanceof DynamicLocalDSCP) {
            int index = ((DynamicLocalDSCP) dscp).getIndex();
            if (dscp instanceof DynamicLocalVariableDSCP){
                this.expression.codegen(cw, mv);
                mv.visitVarInsn(variable.getType().getOpcode(ISTORE), index);
                }
            else {
                mv.visitVarInsn(ALOAD,index);
                Collections.reverse(((Array) variable).getIndexesExpression());
                for (Expression e : ((Array)variable).getIndexesExpression())
                {
                    e.codegen(cw, mv);
                }
                this.expression.codegen(cw, mv);
                mv.visitInsn(variable.getType().getOpcode(IASTORE));
            }

        } else
            mv.visitFieldInsn(PUTSTATIC, "Main", variable.getName(), dscp.getType().toString());
        dscp.setValid(true);
    }
}

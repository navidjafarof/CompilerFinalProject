package Semantic.AST.Statement.assignment;

import Semantic.AST.Expression.Expression;
import Semantic.AST.Expression.variable.Variable;
import Semantic.SymbolTable.DSCP.DSCP;
import Semantic.SymbolTable.DSCP.DynamicLocalDSCP;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Opcodes.ISTORE;
import static org.objectweb.asm.Opcodes.PUTSTATIC;

public class MinusAssign extends Assignment{
    public MinusAssign(Variable variable, Expression expression) {
        super(variable, expression);
    }

    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
        checkIsConst();
        DSCP dscp = variable.getDSCP();
        variable.codegen(cw, mv);
        expression.codegen(cw, mv);

        if (variable.getType() != expression.getType())
            throw new RuntimeException("Mismatching Type In Assignment.");

        mv.visitInsn(variable.getType().getOpcode(Opcodes.ISUB));

        if(dscp instanceof DynamicLocalDSCP) {
            int index = ((DynamicLocalDSCP) dscp).getIndex();
            mv.visitVarInsn(variable.getType().getOpcode(ISTORE), index);
        }
        else
            mv.visitFieldInsn(PUTSTATIC, "Main", variable.getName(), dscp.getType().toString());
    }
}

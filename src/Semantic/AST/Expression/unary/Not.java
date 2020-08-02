package Semantic.AST.Expression.unary;

import Semantic.AST.Expression.Expression;
import Semantic.AST.Expression.constant.Constant;
import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.ICONST_0;
import static org.objectweb.asm.Opcodes.ICONST_1;

public class Not extends UnaryExpression {
    public Not(Expression expression) {
        super(expression);
    }

    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
        Type resultType = getType();
        expression.codegen(cw, mv);
        Label l1 = new Label();
        Label l2 = new Label();
        mv.visitJumpInsn(determineOp(resultType), l1);
        mv.visitInsn(Opcodes.ICONST_0);
        mv.visitJumpInsn(Opcodes.GOTO, l2);
        mv.visitLabel(l1);
        mv.visitInsn(Opcodes.ICONST_1);
        mv.visitLabel(l2);

    }

    @Override
    public int determineOp(Type type) {
        if (type == Type.INT_TYPE)
            return Opcodes.IFEQ;
        else
            System.out.println("Type Mismatch");
        return 0;
    }
}
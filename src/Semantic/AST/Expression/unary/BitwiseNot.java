package Semantic.AST.Expression.unary;

import Semantic.AST.Expression.Expression;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.ICONST_M1;
import static org.objectweb.asm.Opcodes.IXOR;

public class BitwiseNot extends UnaryExpression {
    public BitwiseNot(Expression expression) {
        super(expression);
    }

    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
        expression.codegen(cw, mv);
        if (expression.getType() != Type.INT_TYPE && expression.getType() != Type.LONG_TYPE)
            throw new RuntimeException("Invalid Type For Not.");
        mv.visitInsn(ICONST_M1);
        type = expression.getType();
        mv.visitInsn(type.getOpcode(IXOR));
    }
}

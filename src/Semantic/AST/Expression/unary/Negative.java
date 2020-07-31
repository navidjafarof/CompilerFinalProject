package Semantic.AST.Expression.unary;

import Semantic.AST.Expression.Expression;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.INEG;

public class Negative extends UnaryExpression {
    public Negative(Expression expression) {
        super(expression);
    }

    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
        expression.codegen(cw, mv);
        if (type == Type.BOOLEAN_TYPE)
            throw new RuntimeException("It's not number!");
        type = expression.getType();
        mv.visitInsn(type.getOpcode(INEG));
    }
}

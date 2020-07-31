package Semantic.AST.Expression.binary.conditional;

import Semantic.AST.Expression.Expression;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.IFEQ;
import static org.objectweb.asm.Opcodes.IF_ICMPEQ;

public class NotEqual extends ConditionalExpression {
    public NotEqual(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
        compare(IFEQ, IF_ICMPEQ, cw, mv);
    }
}

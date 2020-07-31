package Semantic.AST.Expression.binary.conditional;

import Semantic.AST.Expression.Expression;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class GreaterThan extends ConditionalExpression {
    public GreaterThan(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
        compare(Opcodes.IFLE, Opcodes.IF_ICMPLE, cw, mv);
    }
}

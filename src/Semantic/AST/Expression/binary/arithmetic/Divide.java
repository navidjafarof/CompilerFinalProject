package Semantic.AST.Expression.binary.arithmetic;

import Semantic.AST.Expression.Expression;
import Semantic.AST.Expression.binary.BinaryExpression;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.*;

public class Divide extends BinaryExpression {
    public Divide(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
        expression1.codegen(cw, mv);
        expression2.codegen(cw, mv);
        if (expression1.getType() != expression2.getType()) {
            throw new IllegalArgumentException("Operand Types Must Be The Same.");
        }
        type = expression1.getType();
        mv.visitInsn(type.getOpcode(IDIV));
    }
}

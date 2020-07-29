package Semantic.AST.Expression.binary.conditional;

import Semantic.AST.Expression.Expression;
import Semantic.AST.Expression.binary.BinaryExpression;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;


public class BitwiseAnd extends BinaryExpression {
    public BitwiseAnd(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
        expression1.codegen(cw, mv);
        expression2.codegen(cw, mv);
        if (expression1.getType() != expression2.getType()){
            throw new IllegalArgumentException("Two Operands must be of the same type");
        }
        type = expression1.getType();
        mv.visitInsn(type.getOpcode(IAND));
    }
}

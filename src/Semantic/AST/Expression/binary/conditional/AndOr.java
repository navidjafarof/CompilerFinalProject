package Semantic.AST.Expression.binary.conditional;

import Semantic.AST.Expression.Expression;
import Semantic.AST.Expression.binary.BinaryExpression;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.*;

public abstract class AndOr extends BinaryExpression {
    public AndOr(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    void AndOr(boolean isAnd, ClassWriter cw, MethodVisitor mv) {

        Label label = new Label();
        Label EndLabel = new Label();

        Type type = getType();

        if (type != Type.BOOLEAN_TYPE && type != Type.INT_TYPE)
            throw new RuntimeException("Only Int And Boolean Types Can Be Operands Of Conditional And.");

        // handled short circuit
        // for AND if one operand is zero(false) we should jp to code for false(ICONST_0)that is label
        // for OR if one operand is not zero(true) we should jp to code for true(ICONST_1)that is label, too
        // for AND if the result will be true, we should do the code for ICONST_1 and then GOTO out(EndLabel)
        // for OR if the result will be false, we should do the code for ICONST_0 and then GOTO out(EndLabel)
        expression1.codegen(cw, mv);
        mv.visitJumpInsn(isAnd ? IFEQ : IFNE, label);
        expression2.codegen(cw, mv);
        mv.visitJumpInsn(isAnd ? IFEQ : IFNE, label);
        mv.visitInsn(isAnd ? ICONST_1 : ICONST_0);
        mv.visitJumpInsn(GOTO, EndLabel);
        mv.visitLabel(label);
        mv.visitInsn(isAnd ? ICONST_0 : ICONST_1);
        mv.visitLabel(EndLabel);


    }
}
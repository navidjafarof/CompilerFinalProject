package Semantic.AST.Expression.binary.conditional;

import Semantic.AST.Expression.Expression;
import Semantic.AST.Expression.binary.BinaryExpression;
import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;

public abstract class ConditionalExpression extends BinaryExpression {
    public ConditionalExpression(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    public void compare(int notIntOpcode, int intOpcode, ClassWriter cw, MethodVisitor mv) {

        expression1.codegen(cw, mv);
        expression2.codegen(cw, mv);

        if (expression1.getType() != expression2.getType()) {
            throw new IllegalArgumentException("Two Operands must be of the same type");
        }
        type = expression1.getType();
        int opcode;

        if (type == Type.FLOAT_TYPE) {
            mv.visitInsn(Opcodes.FCMPG);
            opcode = notIntOpcode;
        } else if (type == Type.DOUBLE_TYPE) {
            mv.visitInsn(Opcodes.DCMPG);
            opcode = notIntOpcode;
        } else if (type == Type.LONG_TYPE) {
            mv.visitInsn(Opcodes.LCMP);
            opcode = notIntOpcode;
        } else {
            opcode = intOpcode;
        }

        Label label1 = new Label();
        Label label2 = new Label();
        mv.visitJumpInsn(opcode, label1);
        mv.visitInsn(ICONST_1);
        mv.visitJumpInsn(GOTO, label2);
        mv.visitLabel(label1);
        mv.visitInsn(ICONST_0);
        mv.visitLabel(label2);
    }

    void AndOr(boolean isAnd, ClassWriter cw, MethodVisitor mv) {

        Label label = new Label();
        Label EndLabel = new Label();


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

        if (expression1.getType() != expression2.getType()) {
            throw new IllegalArgumentException("Operand Types Must Be The Same.");
        }
        type = expression1.getType();

        if (type != Type.BOOLEAN_TYPE && type != Type.INT_TYPE)
            throw new RuntimeException("Only Int And Boolean Types Can Be Operands Of Conditional And.");

    }
}

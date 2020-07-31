package Semantic.AST.Expression.unary;

import Semantic.AST.Expression.Expression;
import Semantic.AST.Expression.constant.Constant;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.ICONST_0;
import static org.objectweb.asm.Opcodes.ICONST_1;

public class Not extends UnaryExpression {
    public Not(Expression expression) {
        super(expression);
    }

    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
        expression.codegen(cw, mv);
        type = expression.getType();
        if (type != Type.INT_TYPE && type != Type.LONG_TYPE && type != Type.BOOLEAN_TYPE)
            throw new RuntimeException("Invalid Type For Not.");
        Object res = ((Constant) expression).getValue();
        if (res instanceof Boolean)
            mv.visitInsn(((Boolean) res) ? ICONST_1 : ICONST_0);
        if (res instanceof Integer)
            mv.visitInsn(((Integer) res) != 0 ? ICONST_1 : ICONST_0);
        if (res instanceof Long)
            mv.visitInsn(((Long) res) != 0 ? ICONST_1 : ICONST_0);

    }
}

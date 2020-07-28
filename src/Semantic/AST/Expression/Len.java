package Semantic.AST.Expression;

import Semantic.AST.Operation;
import static org.objectweb.asm.Opcodes.ARRAYLENGTH;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class Len extends Expression implements Operation {

    Expression expression;

    public Len(Expression e)
    {
        this.expression = e;
    }

    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
        this.expression.codegen(cw, mv);
        type = expression.getType();
        if (expression instanceof ArrayVariable)
        {
            mv.visitInsn(ARRAYLENGTH);
        }
        else
        {
            throw new RuntimeException("Len Argument Is Not Iterable.");
        }
    }
}

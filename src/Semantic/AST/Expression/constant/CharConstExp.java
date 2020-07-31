package Semantic.AST.Expression.constant;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class CharConstExp extends Constant {
    private final Character value;

    public CharConstExp(Character value) {
        this.value = value;
        type = Type.CHAR_TYPE;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
        mv.visitLdcInsn(value);
    }
}

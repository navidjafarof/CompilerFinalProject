package Semantic.AST.Expression.constant;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class StringConstExp extends Constant{
    private final String value;

    public StringConstExp(String value){
        this.value = value.substring(1,value.length() -1);
        type = Type.getType(String.class);
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
        mv.visitLdcInsn(value);
    }
}

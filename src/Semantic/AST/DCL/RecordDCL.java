package Semantic.AST.DCL;

import Semantic.AST.AST;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class RecordDCL implements AST {
    String name;

    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

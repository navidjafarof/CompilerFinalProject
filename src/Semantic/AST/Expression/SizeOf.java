package Semantic.AST.Expression;

import Semantic.AST.Expression.constant.IntegerConstExp;
import Semantic.AST.Operation;
import Semantic.SymbolTable.SymbolTable;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class SizeOf extends Expression implements Operation {
    Integer value;

    public SizeOf(String type) {
        value = SymbolTable.getSize(type);
        this.type = Type.INT_TYPE;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
        IntegerConstExp.storeIntValue(mv, value);
    }
}

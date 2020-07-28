package Semantic.AST.Expression;

import Semantic.AST.DCL.FunctionDCL;
import Semantic.AST.Operation;
import Semantic.SymbolTable.SymbolTable;
import com.sun.org.apache.bcel.internal.generic.INVOKESTATIC;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.util.ArrayList;

import static org.objectweb.asm.Opcodes.*;

public class FunctionCall extends Expression implements Operation {
    String name;
    ArrayList<Expression> arguments;
    FunctionDCL functionDCL;

    public FunctionCall(String name, ArrayList<Expression> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public void addArgument(Expression e) {
        if (arguments == null) {
            arguments = new ArrayList<>();
        }
        arguments.add(e);
    }


    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
        ArrayList<Type> argumentTypes = new ArrayList<>();

        for (Expression e : arguments) {
            e.codegen(cw, mv);
            argumentTypes.add(e.getType());
        }

        this.functionDCL = SymbolTable.getInstance().getFunction(this.name, argumentTypes);
        this.type = functionDCL.getType();

        if (arguments.size() != functionDCL.getInputArguments().size())
            throw new RuntimeException("Number Of Input Arguments Not Matching.");
        mv.visitMethodInsn(INVOKESTATIC, "main", this.name, this.functionDCL.getSignature(), false);
    }
}

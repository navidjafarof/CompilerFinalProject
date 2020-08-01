package Semantic.AST.DCL.VarDCL;

import Semantic.AST.Expression.Expression;
import Semantic.SymbolTable.DSCP.DSCP;
import Semantic.SymbolTable.DSCP.DynamicLocalArrayDSCP;
import Semantic.SymbolTable.DSCP.StaticGlobalArrayDSCP;
import Semantic.SymbolTable.SymbolTable;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.Collections;

import static org.objectweb.asm.Opcodes.*;

public class ArrayDCL extends VarDCL {
    private ArrayList<Expression> dimensionsExpression;

    private int dimensionNum;

    public ArrayDCL(String name, Type type, boolean global, int dimensionNum) {
        this.name = name;
        this.type = type;
        this.global = global;
        this.dimensionsExpression = new ArrayList<>(dimensionNum);
        this.dimensionNum = dimensionNum;
    }

    public ArrayDCL(String name, String stringType, boolean global, Integer dimensionNum, Type type, ArrayList<Expression> dimensionsExpression) {
        this.name = name;
        if (!stringType.equals("auto")) {
            if (!SymbolTable.getTypeFromStr(stringType).equals(type))
                throw new RuntimeException("the types of array doesn't match");
        } else if (dimensionsExpression == null)
            throw new RuntimeException("auto variables must have been initialized");
        if (dimensionNum != null) {
            if (dimensionNum != dimensionsExpression.size())
                throw new RuntimeException("Dimensions Are Not Correct");
            this.dimensionNum = dimensionNum;
        }
        this.type = type;
        this.global = global;
        this.dimensionsExpression = dimensionsExpression;
    }

    public int getDimensionNum() {
        return dimensionNum;
    }

    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
        Collections.reverse(this.dimensionsExpression);
        for (Expression dim : dimensionsExpression) {
            dim.codegen(cw, mv);
        }
        if (global) {
            String repeatedArray = new String(new char[dimensionsExpression.size()]).replace("\0", "[");
            Type arrayType = Type.getType(repeatedArray + type.getDescriptor());
            cw.visitField(ACC_STATIC, name, arrayType.getDescriptor(), null, null).visitEnd();
        } else {
            if (dimensionNum == 1) {
                if (type.getDescriptor().startsWith("L"))
                    mv.visitTypeInsn(ANEWARRAY, type.getDescriptor());
                else
                    mv.visitIntInsn(NEWARRAY, SymbolTable.getTType(type));
            } else {
                StringBuilder t = new StringBuilder();
                for (int i = 0; i < dimensionNum; i++) {
                    t.append("[");
                }
                t.append(type.getDescriptor());
                mv.visitMultiANewArrayInsn(t.toString(), dimensionsExpression.size());
            }
            mv.visitVarInsn(ASTORE, SymbolTable.getInstance().getIndex());
        }
        try {
            SymbolTable.getInstance().getDescriptor(name);
        } catch (Exception e) {
            declare(this.name, this.type, this.dimensionsExpression, this.dimensionNum, this.global);
        }
    }

    public static void declare(String name, Type type, ArrayList<Expression> dimensions, int dimNum, boolean global) {
        DSCP dscp;
        if (!global) {
            dscp = new DynamicLocalArrayDSCP(type, true, SymbolTable.getInstance().getIndex(), dimNum, dimensions);
        } else
            dscp = new StaticGlobalArrayDSCP(type, true, dimNum, dimensions);
        SymbolTable.getInstance().addVariable(name, dscp);
    }

    public void setDimensionsExpression(ArrayList<Expression> dimensionsExpression) {
        this.dimensionsExpression = dimensionsExpression;
    }
}

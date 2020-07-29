package Semantic.AST.Expression.unary;

import Semantic.AST.Expression.Expression;
import Semantic.AST.Expression.constant.IntegerConstExp;
import Semantic.AST.Operation;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class prePlusPlus extends UnaryExpression implements InitExpression, StepExpression, Operation {
    public prePlusPlus(Expression expression) {
        super(expression);
    }

    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
        type = expression.getType();
        if (!(expression instanceof Variable) || (type != Type.INT_TYPE && type != Type.DOUBLE_TYPE && type != Type.LONG_TYPE && type != Type.FLOAT_TYPE))
            throw new RuntimeException("the operand is wrong");
        Variable var = (Variable)expression;
        checkIsConstant(var);
        new SumAssign(new IntegerConstExp(1), var).codegen(cw , mv);
        new SimpleVar(var.getName(), var.getType()).codegen(cw , mv);
    }
}

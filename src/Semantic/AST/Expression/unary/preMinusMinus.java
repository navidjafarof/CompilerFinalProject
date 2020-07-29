package Semantic.AST.Expression.unary;

import Semantic.AST.Expression.Expression;
import Semantic.AST.Expression.InitialExpression;
import Semantic.AST.Expression.StepExpression;
import Semantic.AST.Expression.constant.IntegerConstExp;
import Semantic.AST.Expression.variable.SimpleVariable;
import Semantic.AST.Expression.variable.Variable;
import Semantic.AST.Operation;
import Semantic.AST.Statement.assignment.MinusAssign;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class preMinusMinus extends UnaryExpression implements InitialExpression, StepExpression, Operation {
    public preMinusMinus(Expression expression) {
        super(expression);
    }

    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
        type = expression.getType();
        if (!(expression instanceof Variable) || (type != Type.INT_TYPE && type != Type.DOUBLE_TYPE && type != Type.LONG_TYPE && type != Type.FLOAT_TYPE))
            throw new RuntimeException("the operand is wrong");
        Variable var = (Variable) expression;
        checkIsConstant(var);
        new MinusAssign(var, new IntegerConstExp(1)).codegen(cw, mv);
        new SimpleVariable(var.getName(), var.getType()).codegen(cw, mv);
    }
}

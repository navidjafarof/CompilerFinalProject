package Semantic.AST.Expression.unary;

import Semantic.AST.Expression.Expression;
import Semantic.AST.Expression.InitialExpression;
import Semantic.AST.Expression.StepExpression;
import Semantic.AST.Expression.constant.IntegerConstExp;
import Semantic.AST.Expression.variable.SimpleVariable;
import Semantic.AST.Expression.variable.Variable;
import Semantic.AST.Operation;
import Semantic.AST.Statement.assignment.PlusAssign;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class postPlusPlus extends UnaryExpression implements InitialExpression, StepExpression, Operation {
    public postPlusPlus(Expression expression) {
        super(expression);
    }

    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
        type = expression.getType();
        if (!(expression instanceof Variable) || (type != Type.INT_TYPE && type != Type.DOUBLE_TYPE && type != Type.LONG_TYPE && type != Type.FLOAT_TYPE))
            throw new RuntimeException("Type Invalid For ++");
        Variable var = (Variable) expression;
        checkIsConstant(var);
        // storing the last value in temporary variable
        new SimpleVariable(var.getName(), var.getType()).codegen(cw, mv);
        new PlusAssign(var, new IntegerConstExp(1)).codegen(cw, mv);
    }
}

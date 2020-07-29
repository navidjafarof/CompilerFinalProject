package Semantic.AST.Expression.binary;

import Semantic.AST.Expression.Expression;

public abstract class BinaryExpression extends Expression {
    protected Expression expression1, expression2;

    public BinaryExpression(Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }
}

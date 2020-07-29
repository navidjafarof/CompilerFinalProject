package Semantic.AST.Expression.unary;

import Semantic.AST.Expression.Expression;
import Semantic.SymbolTable.DSCP.DSCP;
import Semantic.SymbolTable.DSCP.DynamicLocalVariableDSCP;
import Semantic.SymbolTable.DSCP.StaticGlobalVariableDSCP;
import Semantic.SymbolTable.SymbolTable;


public abstract class UnaryExpression extends Expression {
    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    protected Expression expression;

    public UnaryExpression(Expression expression) {
        this.expression = expression;
    }
    protected void checkIsConstant(Variable variable) {
        boolean isConstant = false;
        if (variable instanceof SimpleVar) {
            DSCP dscp = SymbolTable.getInstance().getDescriptor(variable.getName());
                isConstant = dscp.isConstant();
        }
        if (isConstant)
            throw new RuntimeException("Can Not Assign To Constant Variable.");
    }

}

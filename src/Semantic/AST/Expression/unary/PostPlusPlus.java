package Semantic.AST.Expression.unary;

import Semantic.AST.Expression.Expression;
import Semantic.AST.Expression.constant.IntegerConstExp;
import Semantic.AST.Expression.variable.SimpleVariable;
import Semantic.AST.Statement.assignment.PlusAssign;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class PostPlusPlus extends PlusMinus {
    public PostPlusPlus(Expression expression) {
        super(expression);
    }

    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
        this.postOrPre = "post";
        this.operator = "plus";
        super.codegen(cw, mv);
    }
}

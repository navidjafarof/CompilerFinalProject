package Semantic.AST.Statement.condition;

import Semantic.AST.Block.Block;
import Semantic.AST.Expression.Expression;
import Semantic.AST.Expression.binary.conditional.NotEqual;
import Semantic.AST.Expression.constant.IntegerConstExp;
import Semantic.AST.Statement.Statement;
import Semantic.SymbolTable.Scope;
import Semantic.SymbolTable.SymbolTable;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.Opcodes.IFEQ;

public class If extends Statement {

    private Expression expression;
    private Block ifBlock;

    private Block elseBlock;

    private Label startElse = new Label();
    private Label endElse = new Label();

    public If(Expression expression, Block ifBlock, Block elseBlock) {
        this.expression = expression;
        this.ifBlock = ifBlock;
        this.elseBlock = elseBlock;
    }

    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
        SymbolTable.getInstance().addScope(Scope.CONDITION);
        NotEqual notEqual = new NotEqual(expression, new IntegerConstExp(0));
        notEqual.codegen(cw, mv);
        mv.visitJumpInsn(IFEQ, startElse);
        ifBlock.codegen(cw, mv);
        mv.visitJumpInsn(GOTO, endElse);
        SymbolTable.getInstance().popScope();
        if (elseBlock != null) {
            SymbolTable.getInstance().addScope(Scope.CONDITION);
            mv.visitLabel(startElse);
            elseBlock.codegen(cw, mv);
            SymbolTable.getInstance().popScope();
        } else
            mv.visitLabel(startElse);
        mv.visitLabel(endElse);
    }

    public void setElseBlock(Block elseBlock) {
        this.elseBlock = elseBlock;
    }
}

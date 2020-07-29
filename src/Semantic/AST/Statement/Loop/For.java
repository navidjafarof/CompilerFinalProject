package Semantic.AST.Statement.Loop;

import Semantic.AST.Block.Block;
import Semantic.AST.Expression.Expression;
import Semantic.AST.Expression.InitialExpression;
import Semantic.AST.Expression.StepExpression;
import Semantic.AST.Expression.binary.conditional.NotEqual;
import Semantic.AST.Expression.constant.IntegerConstExp;
import Semantic.AST.Expression.unary.postMinusMinus;
import Semantic.AST.Expression.unary.postPlusPlus;
import Semantic.AST.Expression.unary.preMinusMinus;
import Semantic.AST.Expression.unary.prePlusPlus;
import Semantic.SymbolTable.Scope;
import Semantic.SymbolTable.SymbolTable;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class For extends Loop {

    private InitialExpression init;
    private Expression expression;
    private StepExpression step;
    private Label expLabel = new Label();
    private Label stepLabel = new Label();
    private Label blockLabel = new Label();


    public For(Block block, InitialExpression init, Expression expression, StepExpression step) {
        super(block);
        this.init = init;
        this.expression = expression;
        this.step = step;
    }

    @Override
    public void codegen(ClassWriter cw, MethodVisitor mv) {
        SymbolTable.getInstance().addScope(Scope.LOOP);
        SymbolTable.getInstance().setInnerLoop(this);
        // ST init
        if (init != null) {
            init.codegen(cw, mv);
            if (init instanceof postPlusPlus || init instanceof prePlusPlus
                    || init instanceof postMinusMinus || init instanceof preMinusMinus)
                mv.visitInsn(POP);
        }
        // Boolean Expression
        mv.visitLabel(expLabel);

        // jz, BE, end
        // jnz, BE, blockLabel
        NotEqual notEqual = new NotEqual(expression, new IntegerConstExp(0));
        notEqual.codegen(cw, mv);
        mv.visitJumpInsn(IFEQ, end);
        mv.visitJumpInsn(GOTO, blockLabel);

        // ST step
        mv.visitLabel(stepLabel);
        mv.visitLabel(startLoop);
        if (step != null) {
            step.codegen(cw, mv);
            if (step instanceof postPlusPlus || step instanceof prePlusPlus
                    || step instanceof postMinusMinus || step instanceof preMinusMinus)
                mv.visitInsn(POP);
        }

        mv.visitJumpInsn(GOTO, expLabel);

        // ST body
        mv.visitLabel(blockLabel);
        block.codegen(cw, mv);
        mv.visitJumpInsn(GOTO, stepLabel);

        mv.visitLabel(end);

        SymbolTable.getInstance().popScope();
        SymbolTable.getInstance().setInnerLoop(null);
    }
}

package Semantic.SymbolTable.DSCP;

import Semantic.AST.Expression.Expression;
import org.objectweb.asm.Type;

import java.util.List;

public class DynamicLocalArrayDSCP extends DynamicLocalDSCP{

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    int dimension;
    List<Expression> dimensionList;

    public DynamicLocalArrayDSCP(Type type, boolean isValid, int index, int dimension, List<Expression> dimensionList) {
        super(type, isValid, index);
        this.dimension = dimension;
        this.dimensionList = dimensionList;
    }
}

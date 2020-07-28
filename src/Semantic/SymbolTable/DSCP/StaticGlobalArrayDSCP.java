package Semantic.SymbolTable.DSCP;

import org.objectweb.asm.Type;
import Semantic.AST.Expression.Expression;
import java.util.List;

public class StaticGlobalArrayDSCP extends StaticGlobalDSCP {
    private int dimension;

    private List<Expression> dimensionList;

    public StaticGlobalArrayDSCP(Type type, boolean isValid, int dimension, List<Expression> dimensionList) {
        super(type, isValid);
        this.dimension = dimension;
        this.dimensionList = dimensionList;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public List<Expression> getDimensionList() {
        return dimensionList;
    }

    public void setDimensionList(List<Expression> dimensionList) {
        this.dimensionList = dimensionList;
    }
}

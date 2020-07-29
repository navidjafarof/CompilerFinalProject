package Semantic.AST.Expression;

import Semantic.AST.AST;
import org.objectweb.asm.Type;

public abstract class Expression implements AST {
    public Type type;

    public Type getType() {
        if (type == null)
            throw new RuntimeException("No Type Set For Expression.");
        return type;
    }
}

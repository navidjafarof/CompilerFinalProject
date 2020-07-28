package Semantic.AST.DCL.VarDCL;

import Semantic.AST.AST;
import org.objectweb.asm.Type;

public abstract class VarDCL implements AST {
    String name;
    Type type;
    boolean global = true;
}

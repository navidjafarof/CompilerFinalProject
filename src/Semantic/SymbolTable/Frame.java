package Semantic.SymbolTable;

import Semantic.SymbolTable.DSCP.DSCP;
import org.objectweb.asm.Label;

import java.util.HashMap;

public class Frame extends HashMap<String, DSCP> {

    private int index = 0;
    private Label startLabel;
    private Label endLabel;
    private Scope scopeType;


    public Label getStartLabel() {
        return startLabel;
    }

    public void setStartLabel(Label startLabel) {
        this.startLabel = startLabel;
    }

    public Label getEndLabel() {
        return endLabel;
    }

    public void setEndLabel(Label endLabel) {
        this.endLabel = endLabel;
    }

    public void addIndex(int add) {
        index += add;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Scope getScopeType() {
        return scopeType;
    }

    public void setScopeType(Scope scopeType) {
        this.scopeType = scopeType;
    }
}
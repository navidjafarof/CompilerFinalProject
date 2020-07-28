package Semantic.SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import Semantic.SymbolTable.DSCP.DSCP;
import Semantic.AST.DCL.FunctionDCL;
import Semantic.AST.DCL.RecordDCL;
import Semantic.SymbolTable.statement.Condition.Switch;
import Semantic.SymbolTable.statement.loop.Loop;
import Semantic.SymbolTable.DSCP.DynamicLocalDSCP;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class SymbolTable {

    private static SymbolTable instance = new SymbolTable();

    private FunctionDCL LastFunction;

    private Loop innerLoop;
    private Switch lastSwitch;
    private ArrayList<Frame> scopesStack = new ArrayList<>();

    private HashMap<String, ArrayList<FunctionDCL>> funcDcls = new HashMap<>();
    private HashMap<String, RecordDCL> recordDcls = new HashMap<>();

    private SymbolTable() {
        Frame mainFrame = new Frame();
        mainFrame.setIndex(1);
        mainFrame.setScopeType(Scope.GLOBAL);
        scopesStack.add(mainFrame);
    }

    public static SymbolTable getInstance() {
        return instance;
    }

    public static int getSize(String name) {
        int size;
        switch (name) {
            case "int":
                size = Integer.SIZE;
                break;
            case "long":
                size = Long.SIZE;
                break;
            case "float":
                size = Float.SIZE;
                break;
            case "double":
                size = Double.SIZE;
                break;
            case "char":
                size = Character.SIZE;
                break;
            case "string":
                size = Integer.SIZE;
                break;
            case "bool":
                size = 1;
                break;
            default:
                throw new IllegalArgumentException("Undefined Type.");

        }
        return size;
    }

    public static Type getTypeFromVarName(String varType) {
        Type type;
        switch (varType) {
            case "Integer":
            case "int":
            case "I":
                type = Type.INT_TYPE;
                break;
            case "Long":
            case "long":
            case "J":
                type = Type.LONG_TYPE;
                break;
            case "Float":
            case "float":
            case "F":
                type = Type.FLOAT_TYPE;
                break;
            case "Double":
            case "double":
            case "D":
                type = Type.DOUBLE_TYPE;
                break;
            case "Character":
            case "char":
            case "C":
                type = Type.CHAR_TYPE;
                break;
            case "String":
            case "string":
            case "Ljava/lang/String;":
                type = Type.getType(String.class);
                break;
            case "Boolean":
            case "bool":
            case "Z":
                type = Type.BOOLEAN_TYPE;
                break;
            case "void":
            case "V":
                type = Type.VOID_TYPE;
                break;
            default:
                type = Type.getType("L" + varType + ";");
        }
        return type;
    }

    public static int getTType(Type type) {

        if (type == Type.INT_TYPE)
            return Opcodes.T_INT;
        else if (type == Type.LONG_TYPE)
            return Opcodes.T_LONG;
        else if (type == Type.FLOAT_TYPE)
            return Opcodes.T_FLOAT;
        else if (type == Type.DOUBLE_TYPE)
            return Opcodes.T_DOUBLE;
        else if (type == Type.CHAR_TYPE)
            return Opcodes.T_CHAR;
        else if (type == Type.BOOLEAN_TYPE)
            return Opcodes.T_BOOLEAN;
        else
            throw new RuntimeException(type + " is Not correct");
    }

    public Set<String> getFuncNames() {
        return funcDcls.keySet(); // Returns All Functions Names
    }

    public void popScope() {
        scopesStack.remove(scopesStack.size() - 1);
    }

    public void addScope(Scope scopeType) {
        Frame frame = new Frame();
        frame.setScopeType(scopeType);
        if (scopeType != Scope.FUNCTION)
            frame.setIndex(getLastScope().getIndex());
        scopesStack.add(frame);
    }

    public Frame getLastScope() {
        if (scopesStack.size() == 0)
            throw new RuntimeException("Scopes Stacks is Empty.");

        return scopesStack.get(scopesStack.size() - 1);
    }


    //To declare a function add it to funcDcls

    public void addFunction(FunctionDCL funcDcl) {
        if (funcDcls.containsKey(funcDcl.getName())) {
            if (funcDcls.get(funcDcl.getName()).contains(funcDcl)) {
                int index = funcDcls.get(funcDcl.getName()).indexOf(funcDcl);
                FunctionDCL lastfunc = funcDcls.get(funcDcl.getName()).get(index);
                if ((lastfunc.getBlock() != null && funcDcl.getBlock() != null) ||
                        (lastfunc.getBlock() == null && funcDcl.getBlock() == null&& !lastfunc.getSignatureDeclared()))
                    throw new RuntimeException("the function is duplicate!!!");

            } else {
                funcDcls.get(funcDcl.getName()).add(funcDcl);
            }
        } else {
            ArrayList<FunctionDCL> funcDclList = new ArrayList<>();
            funcDclList.add(funcDcl);
            funcDcls.put(funcDcl.getName(), funcDclList);
        }
    }

    public FunctionDCL getFunction(String name, ArrayList<Type> inputs) {
        if (funcDcls.containsKey(name)) {
            ArrayList<FunctionDCL> funcDclMapper = funcDcls.get(name);
            for (FunctionDCL f : funcDclMapper) {
                if (f.checkEqual(name, inputs)) {
                    return f;
                }
            }
        }
        throw new RuntimeException("Function " + name + " " + inputs + " Was Not Declared.");
    }

    //declare a variable to the last symbol table

    public void addVariable(String name, DSCP dscp) {
        if (getLastScope().containsKey(name)) {
            throw new RuntimeException("Variable " + name + " Was Declared Previously.");
        }
        if (dscp instanceof DynamicLocalDSCP) {
            getLastScope().put(name, dscp);
//            getLastScope().addIndex(dscp.getType().getSize() - 1);
            getLastScope().addIndex(1);
        } else
            scopesStack.get(0).put(name, dscp);
    }

    public FunctionDCL getLastFunction() {
        return LastFunction;
    }

    public void setLastFunction(FunctionDCL lastFunction) {
        LastFunction = lastFunction;
    }

    public DSCP getDescriptor(String name) {
        int stackIndex = scopesStack.size();
        while (stackIndex > 0) {
            stackIndex--;
            if (scopesStack.get(stackIndex).containsKey(name))
                return scopesStack.get(stackIndex).get(name);
        }
        throw new RuntimeException(name + " Has Not Been Initialized.");
    }

    public boolean canHaveBreak() {
        return getLastScope().getScopeType() == Scope.LOOP || getLastScope().getScopeType() == Scope.SWITCH;
//        return (lastSwitch != null || innerLoop != null);
    }

    public void addRecord(RecordDCL record) {
        if (recordDcls.containsKey(record.getName()))
            throw new RuntimeException("The record was declared early!");
        recordDcls.put(record.getName(), record);
    }


    private RecordDCL getRecord(String name) {
        if (recordDcls.containsKey(name))
            throw new RuntimeException("Record not Found");

        return recordDcls.get(name);
    }

    public boolean isRecordDefined(String name) {
        try {
            getRecord(name);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public int getIndex() {
        return getLastScope().getIndex();
    }
}
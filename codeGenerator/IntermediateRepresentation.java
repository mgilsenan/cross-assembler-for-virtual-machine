package codeGenerator;

import java.util.ArrayList;
import java.util.List;

public class IntermediateRepresentation {
    private ArrayList<String> machineCode;
    private ArrayList<String> assemblyCode;

    public IntermediateRepresentation() {
        machineCode = new ArrayList<String>();
        assemblyCode = new ArrayList<String>();
    }

    public List<String> getmachineCode(){
        return machineCode;
    }

    public List<String> getassemblyCode(){
        return assemblyCode;
    }

    public void addMachineCode(String hex){
        machineCode.add(hex);
    }

    public void addAssemblyCode(String instruction){
        assemblyCode.add(instruction);
    }
}
package lexicalAnalyzer;

import SymbolTable.ISymbolTable;

public class InitiateSymbolTable {
    private ISymbolTable symbolTable;

    public InitiateSymbolTable(ISymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public void generate() {
        for(int i = 0; i < Opcode.inherentMnemonics.length; i++) {
            symbolTable.insertMnemonic(Opcode.inherentMnemonics[i], Opcode.inherentOpcodes[i], "inherent");
        }
        for(int i= 0; i < Opcode.immediateMnemonics.length; i++){
            symbolTable.insertMnemonic(Opcode.immediateMnemonics[i], Opcode.immediateOpcodes[i], "immediate");
        }
    }
    
}

package SymbolTable;
import java.util.HashMap;

public interface ISymbolTable {
    /**
     * Insert a symbol mnemonic to the symbol table
     * @param symbol, symbol hexcode, String mnemType
     * mnemType = inherent(for inherentMnemonics), immediate(for immediateMnemonics),
     * relative(for relativeMnemonics)
     */
    public void insertMnemonic(String symbol, int hexcode, String mnemType);
    
    /**
     * Insert a symbol label to the symbol table
     * @param symbol, symbol address
     */
    public void insertLabel(String symbol, String address);

    /**
     * get a mnemonic hexcode from the symbol table, returns -1 if not found
     * @param symbol
     * @return hexcode value
     */
    int lookupMnemonic(String symbol);
    
    /**
     * get a Label address from the symbol table, returns null if not found
     * @param symbol
     * @return address
     */
    String lookupLabel(String symbol);
    
    /**
     * get a mnemonic type of a specific symbol, returns "null" if not found
     * @param symbol
     * @return mnemType = inherent(for inherentMnemonics), immediate(for immediateMnemonics),
     *  relative(for relativeMnemonics)
     */
    String getMnemType(String symbol);

    
    /**
     * get a hashmap of Inherent Mnemonics stored in the table
     * @return Inherent Mnemonics hashmap
     */    
	HashMap<String, Integer> getInherentMnemonics();

    /**
     * get a hashmap of Inherent Mnemonics stored in the table
     * @return Inherent Mnemonics hashmap
     */    
	HashMap<String, Integer> getImmediateMnemonics();
	
    /**
     * get a hashmap of Inherent Mnemonics stored in the table
     * @return Inherent Mnemonics hashmap
     */    
	HashMap<String, Integer> getRelativeMnemonics();

    /**
     * get a hashmap of all labels stored in the table
     * @return labels hashmap
     */  
    HashMap<String, String> getLabels();
    
//    boolean invalidSymbol();
}
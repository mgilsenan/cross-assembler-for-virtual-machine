package SymbolTable;
//Class symbol table is used to store mnemonics and labels of an assembly unit
import java.util.HashMap;

public class SymbolTable implements ISymbolTable {
    
    private HashMap<String, Integer> inherentMnemonics;
    private HashMap<String, Integer> immediateMnemonics;
    private HashMap<String, Integer> relativeMnemonics;
    private HashMap<String, String> labels;
    
    public SymbolTable() {
    	 inherentMnemonics = new HashMap<String, Integer>();
    	 immediateMnemonics = new HashMap<String, Integer>();
    	 relativeMnemonics = new HashMap<String, Integer>();
    	 labels = new HashMap<String, String>();
	}

    @Override
    public void insertMnemonic(String symbol, int hexcode, String mnemType) {
    	if(symbol != null) {
    		if(mnemType.equals("inherent"))
    			inherentMnemonics.put(symbol, hexcode);
    		else if(mnemType.equals("immediate"))
    			immediateMnemonics.put(symbol, hexcode);
    		else if(mnemType.equals("relative"))
    			relativeMnemonics.put(symbol, hexcode);			
    	}
    }
    
    @Override
    public void insertLabel(String symbol, String address) {
    	if(symbol != null) {
			labels.put(symbol, address);
    	}    		
    }
    
	@Override
	public int lookupMnemonic(String symbol) {
		if(inherentMnemonics.containsKey(symbol))
			return inherentMnemonics.get(symbol);
		if(immediateMnemonics.containsKey(symbol))
			return immediateMnemonics.get(symbol);
		if(relativeMnemonics.containsKey(symbol))
			return relativeMnemonics.get(symbol);
		else
			return -1;
	}
	
	@Override
	public String getMnemType(String symbol) {
		if(inherentMnemonics.containsKey(symbol))
			return "inherent";
		if(immediateMnemonics.containsKey(symbol))
			return "immediate";
		if(relativeMnemonics.containsKey(symbol))
			return "relative";
		else
			return "null";
	}
	
	@Override
	public String lookupLabel(String symbol) {
		if(labels.containsKey(symbol))
			return labels.get(symbol);
		else
			return null;
	}

	public HashMap<String, Integer> getInherentMnemonics() {
		return inherentMnemonics;
	}

	public void setInherentMnemonics(HashMap<String, Integer> inherentMnemonics) {
		this.inherentMnemonics = inherentMnemonics;
	}

	public HashMap<String, Integer> getImmediateMnemonics() {
		return immediateMnemonics;
	}

	public void setImmediateMnemonics(HashMap<String, Integer> immediateMnemonics) {
		this.immediateMnemonics = immediateMnemonics;
	}

	public HashMap<String, Integer> getRelativeMnemonics() {
		return relativeMnemonics;
	}

	public void setRelativeMnemonics(HashMap<String, Integer> relativeMnemonics) {
		this.relativeMnemonics = relativeMnemonics;
	}

	@Override
	public HashMap<String, String>  getLabels() {
		return labels;
	}
    
    //Print all symbols in the symbol table.
    public String toString() {
        String s = "Mnemonics : \n";
        for (String symbolName : inherentMnemonics.keySet()) {
            s += symbolName + "\n";
        }
        for (String symbolName : immediateMnemonics.keySet()) {
            s += symbolName + "\n";
        }
        for (String symbolName : relativeMnemonics.keySet()) {
            s += symbolName + "\n";
        }
        s += "Labels : \n"; 
        for (String symbolName : labels.keySet()) {
            s += symbolName + "\n";
        }
        return s;
    }


}

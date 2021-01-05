import SymbolTable.*;

public class TestSymbolTable {
	public static void main(String[] args) {
    	ISymbolTable st = new SymbolTable();
    	st.insertMnemonic("halt", 0x01, "inherent");
    	st.insertMnemonic("dup", 0x02, "inherent");
    	st.insertMnemonic("add", 0x03, "immediate");
    	st.insertLabel("label1", "004");
    	st.insertLabel("label2", "005");
    	
    	System.out.println("Test SymbolTable"); //test name
    	System.out.print("Mnemonics: {halt=0x01, dup=0x02}, Labels: {label1=004, label2=005},"
    			+ " lookup(halt) : 0x01, lookup(add) : -1\n");//expected output
    	//add lookup method
    	System.out.print("Mnemonics: " + st.getInherentMnemonics() + st.getImmediateMnemonics() + ", Labels: " + st.getLabels()
    			+ ", lookup(halt) : " + st.lookupMnemonic("halt") + ", lookup(add) : " + st.lookupMnemonic("add") + "\n");
	}	
}
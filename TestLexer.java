import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import SymbolTable.ISymbolTable;
import SymbolTable.SymbolTable;
import lexicalAnalyzer.Lexer;

public class TestLexer {

	public static void main(String[] args) throws IOException {
		String srcString = "";
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("test.asm"));
			String line = reader.readLine();
			while (line != null) {
				srcString += line + "\n";
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ISymbolTable st = new SymbolTable();
		Lexer lxr = new Lexer(srcString, st);
		System.out.println("Test Lexer");
		System.out.println("; Sample program ldc.i3 0");//expected value
		System.out.println(lxr.getToken().getValue() + " " + lxr.getToken().getValue() + " " + lxr.getToken().getValue());//actual value
	
	}

}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import SymbolTable.ISymbolTable;
import SymbolTable.SymbolTable;
import lexicalAnalyzer.Lexer;

public class TestLexer {

	private static BufferedReader reader;

	public static void main(String[] args) throws IOException {
		FileInputStream srcStream = new FileInputStream(new File("test.asm"));

        InputStreamReader input = new InputStreamReader(srcStream);

		reader = new BufferedReader(input);
		String srcString = "";
		String line;

		while((line = reader.readLine()) != null) {
			line = line.replaceAll("[\\s|\\u00A0]+"," ");
			srcString += line + "\n";
		}
		System.out.println(srcString);
		
		ISymbolTable st = new SymbolTable();
		Lexer lxr = new Lexer(srcString, st);
		System.out.println("Test Lexer");
		System.out.println("; Sample program ldc.i3 0");//expected value
		System.out.println(lxr.getToken().getValue() + " " + lxr.getToken().getValue() + " " + lxr.getToken().getValue());//actual value
	
	}

}

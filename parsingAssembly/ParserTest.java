package parsingAssembly;
import SymbolTable.ISymbolTable;
import SymbolTable.SymbolTable;
import lexicalAnalyzer.InitiateSymbolTable;
import lexicalAnalyzer.Lexer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

class ParserTest {

    private static InitiateSymbolTable initiateSymbolTable;

	public static void main(String[] args) throws IOException {

        FileInputStream srcStream = new FileInputStream(new File("test.asm"));

        InputStreamReader input = new InputStreamReader(srcStream);

		BufferedReader reader = new BufferedReader(input);
		String srcString = "";
		String line;

		while((line = reader.readLine()) != null) {
			line = line.replaceAll("[\\s|\\u00A0]+"," ");
			srcString += line + "\n";
		}
		System.out.println(srcString);
		
        ISymbolTable st = new SymbolTable();
        initiateSymbolTable = new InitiateSymbolTable(st);
        initiateSymbolTable.generate();
		Lexer lxr = new Lexer(srcString, st);

        AssemblerFactory f = new AssemblerFactory(lxr,st);
        Parser p = new Parser(f);
        p.parse();

        System.out.println("Test Parser Finish");
    }

}
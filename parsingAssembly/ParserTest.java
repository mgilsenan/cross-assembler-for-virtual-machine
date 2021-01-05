package parsingAssembly;

import SymbolTable.ISymbolTable;
import SymbolTable.SymbolTable;
import lexicalAnalyzer.Lexer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//import static org.junit.jupiter.api.Assertions.*;

class ParserTest {


    //    @org.junit.jupiter.api.Test
    void parse() {
    }

    //    @org.junit.jupiter.api.Test

    void parseLineStatement() {
    }

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
//		System.out.println(srcString);
        Lexer lxr = new Lexer(srcString, st);

        AssemblerFactory f = new AssemblerFactory(lxr,st);
        Parser p = new Parser(f);
        p.parse();

        //LineStatement lsmt = new LineStatement();

        System.out.println("Test Parser Finish");
//        System.out.println(lxr.getToken().getTokenInt() + " " + lxr.getToken().getTokenInt() + " " + lxr.getToken().getTokenInt());

    }

}
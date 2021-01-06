package lexicalAnalyzer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import SymbolTable.*;
import errorHandling.ErrorMessage;

public class Lexer implements Opcode {
    public static boolean EOF = false;
    public static boolean EOL = false;
    private int line;
    private int colPos;
    private int incrementalPosition;//reserve the reader place
    private char chr;
    public String s;//file content
    private ISymbolTable keywordTable;
    private Token token;
    private boolean mnemFound = false;

    private ArrayList<String> tokenType = new ArrayList<>();

    public Lexer(String source, ISymbolTable symbolTable) {
        this.line = 0;
        this.colPos = 0;
        this.incrementalPosition = 0;
        this.s = source;
        this.chr = this.s.charAt(0);
        this.keywordTable = symbolTable;


        // Enter all mnemonics as keywords in the symbol table...
        initiateSymbolTable(this.keywordTable);
    }


    /*Initiate symbol table with all mnemonics*/
    private void initiateSymbolTable(ISymbolTable keywordTable) {
        for(int i = 0; i < Opcode.inherentMnemonics.length; i++) {
            keywordTable.insertMnemonic(Opcode.inherentMnemonics[i], Opcode.inherentOpcodes[i], "inherent");
            tokenType.add(Opcode.inherentMnemonics[i]);
        }
        for(int i= 0; i < Opcode.immediateMnemonics.length; i++){
            keywordTable.insertMnemonic(Opcode.immediateMnemonics[i], Opcode.immediateOpcodes[i], "immediate");
            tokenType.add(Opcode.immediateMnemonics[i]);
        }
    }

    //Differentiate mnemonics from labels, and save labels in symbol table
    Token mnemonic_or_label(int line, int pos) {
//    	System.out.println("checking at : position, line " + pos + ", " + line);

      int tokenInt;

        String text = "";

        while (Character.isAlphabetic(this.chr) || Character.isDigit(this.chr) || this.chr == '.') {
            text += this.chr;
            getNextChar();
        }

        if (text.equals("")) {
            ErrorMessage err = new ErrorMessage(new Position(line, pos), "unrecognized character : " + this.chr);
        }

        if (keywordTable.lookupMnemonic(text) != -1) {//check mnemonic
//        	System.out.println("checking mnemonics");

            if(pos == 0) {//should not be at first column
                ErrorMessage err = new ErrorMessage(new Position(line, pos), "can not have a mnemonic at first column!");
                return new Token("", line, pos, 0);
            }
            else {
                tokenInt = 2;
                Token mnemonic = new Token(text, line, pos-1, tokenInt);
                //use it to check operand
                mnemFound = true;
//        		System.out.print(" /in mnmonic/ " + text + " found");
//        		System.out.println(EOL);
                return mnemonic;
            }
        }
        else if(Character.isUpperCase(text.charAt(0)) && pos == 0){//checking for labels : should only be at pos = 0
//        	System.out.println("it is label");
            tokenInt = 1;
            Token label = new Token(text, line, pos, tokenInt);
            keywordTable.insertLabel(text, String.format("%05X", line & 0x0FFFFF));
            return label;

        }
        return new Token("", line, pos, -1);
    }

    Token directive(int line, int pos) {
        String text = ".";
        getNextChar();
        while (Character.isAlphabetic(this.chr)) {
            text += this.chr;
            getNextChar();
        }
        if(text.toLowerCase().equals(".cstring")) {
            int tokenInt = 3;
            Token tkn = new Token(text, line, pos-1, tokenInt);
            return tkn;
        }
        return new Token("", this.line, this.colPos, 0);
    }

    Token comment(int line, int pos) {
        String text = ";";
        getNextChar();
        //add till !EOL
        while (!isEOL(this.chr)) {
            text += this.chr;
            getNextChar();
        }
//    	System.out.println("EOL at " + text);
//        EOL = true;
        int tokenInt = 4;

        Token tkn = new Token(text, line, pos-1, tokenInt);
        return tkn;
    }

    Token number(int line, int pos) {
        String text = "";
        getNextChar();

        while (Character.isDigit(this.chr)) {
            text += this.chr;
            getNextChar();
        }
        int tokenInt = 4;
        Token tkn = new Token(text, line, pos-1, tokenInt);
        return tkn;
    }
    //Operand : Label | Address | Offset 
    Token operand(int line, int pos) {
        String text = "";
        int tokenInt;
        while (Character.isAlphabetic(this.chr) || Character.isDigit(this.chr) || this.chr == '.') {
            text += this.chr;
            getNextChar();
        }
//        System.out.print(" /in operand/ ");
        tokenInt = 3;
        return new Token(text, line, pos-1, tokenInt);
    }
    //Return the token found - will be used by the parser
    public Token getToken() {

       if(EOL)
         mnemFound = false;
       EOL = false;

        int curline, curPos;
        while (Character.isWhitespace(this.chr)) {
            getNextChar();
        }
        curline = this.line;
        curPos= this.colPos;
        
        switch (this.chr) {
//            case '\u0000': 
//            	EOF = true;
//            	return new Token("EOF", this.line, this.colPos, -1);//tokenInt = -1 : not a token = EOF
//            case '\n':case '\r':
//            	return new Token("EOL", this.line, this.colPos, 0);//tokenInt = 0 : not a token = EOL
            case '.':
              return directive(curline, curPos);
            case ';':
              return comment(curline, curPos);
            case '0': case '1': case '2': case '3': case '4':
            case '5': case '6': case '7': case '8': case '9':
              return operand(curline, curPos);


            case 'a': case 'b': case 'c': case 'd': case 'e':
            case 'f': case 'g': case 'h': case 'i': case 'j':
            case 'k': case 'l': case 'm': case 'n': case 'o':
            case 'p': case 'q': case 'r': case 's': case 't':
            case 'u': case 'v': case 'w': case 'x': case 'y':
            case 'z':
            case 'A': case 'B': case 'C': case 'D': case 'E':
            case 'F': case 'G': case 'H': case 'I': case 'J':
            case 'K': case 'L': case 'M': case 'N': case 'O':
            case 'P': case 'Q': case 'R': case 'S': case 'T':
            case 'U': case 'V': case 'W': case 'X': case 'Y':
            case '_':
            case 'Z':

              if(mnemFound) {
//            	System.out.print(" mnem found");
                  return operand(curline, curPos);
              }
              else
                    return mnemonic_or_label(curline, curPos);

//            case '-':
//                getNextChar(); return MINUS;
//
//            case ',':
//                getNextChar(); return COMMA;
//
//            case '"':
//                return getString(curline, curPos);
            default://invalid char

              getNextChar();
              return new Token("", this.line, this.colPos, -1);//not a token, invalid characters or EOF

        }
    }


    //Read the next character from the assembly file
    char getNextChar() {
        this.colPos++;
        this.incrementalPosition++;
        if (this.incrementalPosition >= this.s.length()) {
            this.chr = '\u0000';//EOF
            EOF = true;
            EOL = true;
            return this.chr;
        }
//        System.out.println("current colPos = " + colPos);
        this.chr = this.s.charAt(this.incrementalPosition);
        if (isEOL(this.chr)) {
            this.line++;
            this.colPos = -1; // reset the column position
            mnemFound = false; //reset for mnemonic flag
            EOL = true;
//            System.out.println("resetting the position at line");
//            System.out.println(EOL + "===========");
        }
        return this.chr;
    }

    //Print all tokens found - used for testing
    void printTokens() {
        Token t;
        while ((t = getToken()).getValue() != "") {
            System.out.println(t);
        }
        System.out.println(t);
    }

    boolean isEOL(char c) {

      if(c == '\n' | c == '\r')
      {
          EOL = true;
          mnemFound = false;
          return true;
      }
      else return false;

    }

}
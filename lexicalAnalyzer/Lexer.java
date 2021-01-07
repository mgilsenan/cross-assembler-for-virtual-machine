package lexicalAnalyzer;
import java.util.ArrayList;
import SymbolTable.*;
import errorHandling.ErrorMessage;

public class Lexer implements Opcode {
    public static boolean EOF = false;
    public static boolean EOL = false;
    private int line;
    private int colPos;
    private int incrementalPosition;
    private char chr;
    public String entireSrcFile;
    private ISymbolTable keywordTable;

    private ArrayList<String> tokenType = new ArrayList<>();

    public Lexer(String source, ISymbolTable symbolTable) {
        this.line = 0;
        this.colPos = 0;
        this.incrementalPosition = 0;
        this.entireSrcFile = source;
        this.chr = this.entireSrcFile.charAt(0);
        this.keywordTable = symbolTable;
        initiateSymbolTable(this.keywordTable); //remove refactor to different class
    }

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

    Token mnemonic(int line, int position){
        int tokenInt;
        String text = "";
       
        while (Character.isAlphabetic(this.chr) || Character.isDigit(this.chr) || this.chr == '.') {
            text += this.chr;
            getNextChar();
        }

        if (text.equals("")) {
            ErrorMessage err = new ErrorMessage(new Position(line, position), "unrecognized character : " + this.chr);
        }

        if (keywordTable.lookupMnemonic(text) != -1) {

            if(position == 0) {//should not be at first column
                ErrorMessage err = new ErrorMessage(new Position(line, position), "can not have a mnemonic at first column!");
                return new Token("", line, position, 0);
            }
            else {
                tokenInt = 2;
                Token mnemonic = new Token(text, line, position-1, tokenInt);
               
                return mnemonic;
            }
            
        }

        return new Token("", line, position, -1);
    }

    Token label(int line, int position){
        int tokenInt;
        String text = "";
        
        
        while (Character.isAlphabetic(this.chr) || Character.isDigit(this.chr) || this.chr == '.') {
            text += this.chr;
            getNextChar();
        }

        if (text.equals("")) {
            ErrorMessage err = new ErrorMessage(new Position(line, position), "unrecognized character : " + this.chr);
        }

        if(Character.isUpperCase(text.charAt(0)) && position == 0){
            tokenInt = 1;
            Token label = new Token(text, line, position, tokenInt);
            keywordTable.insertLabel(text, String.format("%05X", line & 0x0FFFFF));
            return label;

        }

        return new Token("", line, position, -1);
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
        
        while (!isEOL(this.chr)) {
            text += this.chr;
            getNextChar();
        }

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
    
    Token operand(int line, int pos) {
        String text = "";
        int tokenInt;

        while (Character.isAlphabetic(this.chr) || Character.isDigit(this.chr) || this.chr == '.') {
            text += this.chr;
            getNextChar();
        }

        tokenInt = 3;

        return new Token(text, line, pos-1, tokenInt);
    }
    
    public Token getToken() {
        int curline, curPos;

        while (Character.isWhitespace(this.chr)) {
            getNextChar();
        }

        curline = this.line;
        curPos= this.colPos;
        
        switch (this.chr) {
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
                return mnemonic(curline, curPos);
            case 'A': case 'B': case 'C': case 'D': case 'E':
            case 'F': case 'G': case 'H': case 'I': case 'J':
            case 'K': case 'L': case 'M': case 'N': case 'O':
            case 'P': case 'Q': case 'R': case 'S': case 'T':
            case 'U': case 'V': case 'W': case 'X': case 'Y':
            case '_':
            case 'Z':
                return label(curline, curPos);
            default:

              getNextChar();
              return new Token("", this.line, this.colPos, -1);//not a token, invalid characters or EOF

        }
    }

    char getNextChar() {
        this.colPos++;
        this.incrementalPosition++;

        if (this.incrementalPosition >= this.entireSrcFile.length()) {
            this.chr = '\u0000';//EOF
            EOF = true;
            EOL = true;
            return this.chr;
        }

        this.chr = this.entireSrcFile.charAt(this.incrementalPosition);
        if (isEOL(this.chr)) {
            
            this.line++;
            this.colPos = -1; // reset the column position
            EOL = true;
        }

        return this.chr;
    }

    boolean isEOL(char c) {

      if(c == '\n' )
      {
          EOL = true;
          return true;
      }
      
      else return false;
    }

}
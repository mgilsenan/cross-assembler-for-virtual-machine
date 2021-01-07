package lexicalAnalyzer;
import SymbolTable.*;
import errorHandling.ErrorMessage;

public class Lexer implements Opcode {
    public static boolean EOF = false;
    public static boolean EOL = false;
    private int line;
    private int columnPosition;
    private int incrementalPosition;
    private char srcFileChar;
    public String entireSrcFile;
    private ISymbolTable keywordTable;

    public Lexer(String source, ISymbolTable symbolTable) {
        this.line = 0;
        this.columnPosition = 0;
        this.incrementalPosition = 0;
        this.entireSrcFile = source;
        this.srcFileChar = this.entireSrcFile.charAt(0);
        this.keywordTable = symbolTable;
    }

    private Token mnemonic(int line, int position){
        int tokenInt;
        String text = "";
       
        while (Character.isAlphabetic(this.srcFileChar) || Character.isDigit(this.srcFileChar) || this.srcFileChar == '.') {
            text += this.srcFileChar;
            getNextChar();
        }

        if (text.equals("")) {
            ErrorMessage err = new ErrorMessage(new Position(line, position), "unrecognized character : " + this.srcFileChar);
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

    private Token label(int line, int position){
        int tokenInt;
        String text = "";
        
        while (Character.isAlphabetic(this.srcFileChar) || Character.isDigit(this.srcFileChar) || this.srcFileChar == '.') {
            text += this.srcFileChar;
            getNextChar();
        }

        if (text.equals("")) {
            ErrorMessage err = new ErrorMessage(new Position(line, position), "unrecognized character : " + this.srcFileChar);
        }

        if(Character.isUpperCase(text.charAt(0)) && position == 0){
            tokenInt = 1;
            Token label = new Token(text, line, position, tokenInt);
            keywordTable.insertLabel(text, String.format("%05X", line & 0x0FFFFF));
            return label;

        }

        return new Token("", line, position, -1);
    }

    private Token directive(int line, int pos) {
        String text = ".";

        getNextChar();

        while (Character.isAlphabetic(this.srcFileChar)) {
            text += this.srcFileChar;
            getNextChar();
        }

        if(text.toLowerCase().equals(".cstring")) {
            int tokenInt = 3;
            Token tkn = new Token(text, line, pos-1, tokenInt);
            return tkn;
        }

        return new Token("", this.line, this.columnPosition, 0);
    }

    private Token comment(int line, int pos) {
        String text = ";";
        getNextChar();
        
        while (!isEOL(this.srcFileChar)) {
            text += this.srcFileChar;
            getNextChar();
        }

        int tokenInt = 4;

        Token tkn = new Token(text, line, pos-1, tokenInt);

        return tkn;
    }
    
    private Token operand(int line, int pos) {
        String text = "";
        int tokenInt;

        while (Character.isAlphabetic(this.srcFileChar) || Character.isDigit(this.srcFileChar) || this.srcFileChar == '.') {
            text += this.srcFileChar;
            getNextChar();
        }

        tokenInt = 3;

        return new Token(text, line, pos-1, tokenInt);
    }
    
    public Token getToken() {
        int curline, curPos;

        while (Character.isWhitespace(this.srcFileChar)) {
            getNextChar();
        }

        curline = this.line;
        curPos= this.columnPosition;
        
        switch (this.srcFileChar) {
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
              return new Token("", this.line, this.columnPosition, -1);//not a token, invalid characters or EOF

        }
    }

    private char getNextChar() {
        this.columnPosition++;
        this.incrementalPosition++;

        if (this.incrementalPosition >= this.entireSrcFile.length()) {
            this.srcFileChar = '\u0000';//EOF
            EOF = true;
            EOL = true;
            return this.srcFileChar;
        }

        this.srcFileChar = this.entireSrcFile.charAt(this.incrementalPosition);
        if (isEOL(this.srcFileChar)) {
            
            this.line++;
            this.columnPosition = -1; // reset the column position
            EOL = true;
        }

        return this.srcFileChar;
    }

    private boolean isEOL(char c) {

      if(c == '\n' )
      {
          EOL = true;
          return true;
      }

      else return false;
    }

}
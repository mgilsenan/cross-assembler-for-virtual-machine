package parsingAssembly;

import SymbolTable.*;
import errorHandling.ErrorMessage;
import lexicalAnalyzer.*;

public class Parser implements IParser {
    private int           token;
    private ILexer        lexer;
    private Lexer           lxr;
    private ISourceFile   sourceFile;
    private IReportable   errorReporter;
    private ISymbolTable  table;
    private ISymbolTable  keywordTable;
    private Token tokenObj;

    public void LineStatement(Label label, Instruction inst, Comment comment) {
    }

    public Parser(AssemblerFactory factory) {
        this.lxr = factory.getLexer();
        this.sourceFile = factory.getSourceFile();
        this.errorReporter = factory.getErrorReporter();
        this.table = factory.getSymbolTable();
        //nextToken(); // prime
        //address = 0;
    }
    // Record the error: <t> expected, found <token> at <token>.position
    // protected void expect(int t) {
    //     if (t != token) {
    //         String expected = Lexer.getTokenName(t);
    //         errorReporter.record( _Error.create( expected+" expected", Lexer.getPosition()) );
    //     }
    //     nextToken();
    // }
    // protected void expect(String t) {
    //     errorReporter.record( _Error.create(t+" expected", Lexer.getPosition()) );
    // }
    // protected void error(String t) {
    //     errorReporter.record( _Error.create(t, Lexer.getPosition()) );
    // }

    // private class SyntaxError extends Exception {}

    // -------------------------------------------------------------------
    // An assembly unit is zero or more line statement(s).
    //
    // AssemblyUnit = { LineStmt } EOF .
    // -------------------------------------------------------------------
    public AssemblyUnit parse() {
        System.out.println("Parsing a AssemblyUnit...");

        LineStatmentList seq = new LineStatmentList();
        parseLineStatement();
        while (!lxr.EOF) {
            seq.add(parseLineStatement());
        }
        return new AssemblyUnit(seq);
    }
    //---------------------------------------------------------------------------------
    private boolean parseInherent(Mnemonic mnemonic, Operand operand) {
        // your code...

        String temp = table.getMnemType(mnemonic.getValue());
        System.out.println(temp);
        if(table.getMnemType(mnemonic.getValue()).contains("inherent")){
            if(operand == null){
                return true;
            }
            else {
                ErrorMessage err = new ErrorMessage(new Position(tokenObj.getLine(), tokenObj.getPos()),
                        "\"Inherent should not have an operand\" ");
                return false;
            }
        }
        if(table.getMnemType(mnemonic.getValue()).equals("null")){
            ErrorMessage err = new ErrorMessage(new Position(tokenObj.getLine(), tokenObj.getPos()),
                    "\"SYNTAX ERROR\" ");
            return false;
        }
        return false;
    }
    //---------------------------------------------------------------------------------
    private boolean parseImmediate(Mnemonic mnemonic, Operand operand) {
         //your code...
        //System.out.println(operand.getValue()); //
        if(table.getMnemType(mnemonic.getValue()).contains("immediate")){
            if(operand != null){
                return true;
            }
            else {
                ErrorMessage err = new ErrorMessage(new Position(tokenObj.getLine(), tokenObj.getPos()),
                        "\"Immediate should have an operand\" ");
                return false;
            }
        }
        return false;
    }
    //---------------------------------------------------------------------------------
    private boolean parseRelative(Mnemonic mnemonic, Operand operand) {
        // your code...
        if(table.getMnemType(mnemonic.getValue()).contains("relative")){
            if(operand != null){
                return true;
            }
            else {
                ErrorMessage err = new ErrorMessage(new Position(tokenObj.getLine(), tokenObj.getPos()),
                        "\"Relative should have an operand\" ");
                return false;
            }
        }
        return false;
    }
    // -------------------------------------------------------------------
    // A line statement:
    //   - could be empty (only a EOL);
    //   - could have a single comment start at BOL or after a label, label/inst, or label/dir;
    //   - could have a label only, etc.
    //
    // LineStatement = [Label] [Instruction | Directive ] [Comment] EOL .
    //
    public LineStatement parseLineStatement() {
        Label        label = null;
        Instruction instruction = null;
        Mnemonic  mnemonic = null;
        Operand     operand= null;
        Comment      comment = null;

        Directive   directive = null;
        Digit digit = null;

        lxr.EOL = false;

        //System.out.println("Parsing a Line Statement...");
        System.out.println();
        while(!lxr.EOL) {
            tokenObj = lxr.getToken();
            int tokenInt = tokenObj.getTokenInt();
            String tokenType = tokenObj.getValue();
            switch (tokenInt) {
                case 1://label
                     if(Character.isUpperCase(tokenType.charAt(0))){
                         System.out.print("This is a Label : " );
                         label = new Label(tokenType);
                     }
                    break;
                case 2: //Mnemonic
                    System.out.print("This is a Mnemonic : " );
                    mnemonic = new Mnemonic(tokenType);
                    break;
                case 3://Operand
                    if(Character.isUpperCase(tokenType.charAt(0)) || tokenType.matches("^[0-9]*$")){
                        System.out.print("This is a Operand : " );
                        operand = new Operand(tokenType);
                    }
                    else{
                        ErrorMessage err = new ErrorMessage(new Position(tokenObj.getLine(), tokenObj.getPos()),
                                "\"Invalid Operand\" ");
                    }
                    break;
                case 4://Comment
                    if(tokenType.charAt(0) == ';'){
                        System.out.print("This is a Comment : ");
                        comment = new Comment(tokenType);
                    }
                    break;
               ///case 5://directive
               ///    if(tokenType.contains(".cstring")){
               ///        System.out.print("This is a Directive : " );
               ///        directive = new Directive(tokenType);
               ///    }
               ///    break;
                //case 6://number
                //    if (tokenType.contains("[a-zA-Z]+") == false) {
                //        System.out.print("This is a number : " );
                //        digit = new Digit(tokenType);
                //    }
                //    break;
                //case 7:  tokenType = lxr.getToken().getValue();
                ////label = new Label(tokenType);
                //    break;
                default:
                    break;
            }
            System.out.println("\t\t\t"  + tokenType);
            //System.out.println();
        }


        if(mnemonic != null){
            //System.out.println(mnemonic.getValue());
            boolean inherent = parseInherent(mnemonic,operand);
            boolean immediate = parseImmediate(mnemonic,operand);
            boolean relative = parseRelative(mnemonic,operand);

            if(inherent){
                instruction = new Instruction(mnemonic,null,table.lookupMnemonic(mnemonic.getValue()),1);
            }
            else if (immediate){
                instruction = new Instruction(mnemonic,operand,table.lookupMnemonic(mnemonic.getValue()),2);
            }
            else if (relative){
                instruction = new Instruction(mnemonic,operand,table.lookupMnemonic(mnemonic.getValue()),3);
            }
            else if(!inherent && !immediate && !relative){
                ErrorMessage err = new ErrorMessage(new Position(tokenObj.getLine(), tokenObj.getPos()),
                        "\"INSTRUCTION SYNTAX ERROR\" ");
                mnemonic.value = "ERROR ";
                instruction = new Instruction(mnemonic,operand,table.lookupMnemonic(mnemonic.getValue()),4); // flag 4 is when an ERROR occurs
            }

        }



        return new LineStatement(label, instruction, comment);
        
    }

    //protected void nextToken() {
    //    token = lxr.getToken();
    //}
}

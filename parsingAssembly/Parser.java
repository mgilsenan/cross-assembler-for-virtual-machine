package parsingAssembly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import SymbolTable.*;
import codeGenerator.IntermediateRepresentation;
import errorHandling.ErrorMessage;
import lexicalAnalyzer.*;

public class Parser implements IParser {
    private Lexer lexer;
    private IReportable errorReporter;
    private ISymbolTable table;
    private Token tokenObj;
    private IntermediateRepresentation irList;

    public Parser(AssemblerFactory factory) {
        this.lexer = factory.getLexer();
        this.errorReporter = factory.getErrorReporter();
        this.table = factory.getSymbolTable();
        irList = new IntermediateRepresentation();
    }

    public AssemblyUnit parse() {
        System.out.println("Parsing a AssemblyUnit...");

        LineStatmentList seq = new LineStatmentList();
        parseLineStatement();
        while (!Lexer.EOF) {
            seq.add(parseLineStatement());
        }
        return new AssemblyUnit(seq);
    }
    
    private Instruction parseInherent(Mnemonic mnemonic, Operand operand) {
        return new Instruction(mnemonic,null,table.lookupMnemonic(mnemonic.getValue()),1);
    }
    
    private Instruction parseImmediate(Mnemonic mnemonic, Operand operand) {
        return new Instruction(mnemonic,operand,table.lookupMnemonic(mnemonic.getValue()),2);
    }
    
    private Instruction parseRelative(Mnemonic mnemonic, Operand operand) {
        return new Instruction(mnemonic,operand,table.lookupMnemonic(mnemonic.getValue()),3);
    }
 
    public LineStatement parseLineStatement() {
        Label        label = null;
        Instruction instruction = null;
        Mnemonic  mnemonic = null;
        Operand     operand= null;
        Comment      comment = null;
        HashMap<String, Integer> inherentMnemonics = table.getInherentMnemonics();
        HashMap<String, Integer> immediateMnemonics = table.getImmediateMnemonics();
        HashMap<String, Integer> relativeMnemonics = table.getRelativeMnemonics();
        String mnemonicToken = null;

        Lexer.EOL = false;

        System.out.println();
        while(!Lexer.EOL) {
            tokenObj = lexer.getToken();
            int tokenInt = tokenObj.getTokenInt();
            String tokenType = tokenObj.getValue();
            
            switch (tokenInt) {
                case 1://label
                     if(Character.isUpperCase(tokenType.charAt(0))){
                         //System.out.print("This is a Label : " );
                         label = new Label(tokenType);
                     }
                    break;
                case 2: //Mnemonic
                    //System.out.print("This is a Mnemonic : " );
                    mnemonic = new Mnemonic(tokenType);
                    mnemonicToken = tokenType;
                    break;
                case 3://Operand
                    if(Character.isUpperCase(tokenType.charAt(0)) || tokenType.matches("^[0-9]*$")){
                        //System.out.print("This is a Operand : " );
                        operand = new Operand(tokenType);
                    }
                    else{
                        ErrorMessage err = new ErrorMessage(new Position(tokenObj.getLine(), tokenObj.getPos()),
                                "\"Invalid Operand\" ");
                    }
                    break;
                case 4://Comment
                    if(tokenType.charAt(0) == ';'){
                        //System.out.print("This is a Comment : ");
                        comment = new Comment(tokenType);
                    }
                    break;
                default:
                    break;
            }
            //System.out.println("\t\t\t"  + tokenType);
        }
        
        if(mnemonic != null){
            if(inherentMnemonics.containsKey(mnemonicToken)){
                Instruction parseInherent = parseInherent(mnemonic, null);
                String hexString = Integer.toHexString(parseInherent.getHexInt());
                String mnemonicStr = parseInherent.getMnemonicStr();
                System.out.println(hexString+" "+mnemonicStr);
                irList.addMachineCode(hexString);
                irList.addAssemblyCode(mnemonicStr);
            }
                
            if(immediateMnemonics.containsKey(mnemonicToken)){
                Instruction parseImmediate = parseImmediate(mnemonic, operand);//immediate instruction
                if(operand == null){
                    String hexString = Integer.toHexString(parseImmediate.getHexInt());
                    String instructionString = hexString + " " + parseImmediate.getMnemonicStr();
                    System.out.println(instructionString);
                    irList.addMachineCode(hexString);
                    irList.addAssemblyCode(instructionString);
                } else {
                    int hexInt = parseImmediate.getHexInt() + parseImmediate.getIntOperand();
                    String hexString = Integer.toHexString(hexInt);
                    String instructionString = hexString + " " + parseImmediate.getMnemonicStr() + " " + parseImmediate.getOperand();
                    System.out.println(instructionString);
                    irList.addMachineCode(hexString);
                    irList.addAssemblyCode(instructionString);
                }
            }
              
            // if(relativeMnemonics.containsKey(mnemonicToken)){
            //     Instruction parseRelative = parseRelative(mnemonic, operand);
            //     int hexInt = parseRelative.getHexInt();
            //     String mnemonicStr = parseRelative.getMnemonicStr();
            //     int intOperand = parseRelative.getIntOperand();
            //     System.out.println(hexInt+" "+mnemonicStr+" "+intOperand);
            // }

            //if a token has a space do nothing

        } else {
            // irList.addMachineCode(" ");
            // irList.addAssemblyCode(" ");
        }

        return new LineStatement(label, instruction, comment);
    }

    public List<String> getmachineCode(){
        return irList.getmachineCode();
    }
    
    public List<String> getassemblyCode(){
        return irList.getassemblyCode();
    }
}

package parsingAssembly;

public class Instruction {

    private Mnemonic mnemonic;
    private Operand operand;
    private int hexa;
    private int flag;

    public String getValue() {
        return mnemonic.getValue();
    }

    public Instruction(Mnemonic mnemonic, Operand operand,int hexa, int flag) {
        this.mnemonic = mnemonic;
        this.operand = operand;
        this.hexa = hexa;
        this.flag = flag; // 1 - Inherent, 2 - Immediate, 3 - Relative
    }
}
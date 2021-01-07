package parsingAssembly;

public class Instruction {

    private Mnemonic mnemonic;
    private Operand operand;
    private int hex;
    private int flag;

    public String getMnemonicStr() {
        return mnemonic.getValue();
    }

    public int getHexInt(){
        return hex;
    }

    public String getOperand() {
        return operand.getValue();
    }

    public int getIntOperand() {
        return operand.getIntValue();
    }

    public int getFlag(){
        return flag;
    }

    public Instruction(Mnemonic mnemonic, Operand operand,int hex, int flag) {
        this.mnemonic = mnemonic;
        this.operand = operand;
        this.hex = hex;
        this.flag = flag; // 1 - Inherent, 2 - Immediate, 3 - Relative
    }
}
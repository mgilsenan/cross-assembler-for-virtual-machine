package parsingAssembly;

public class Operand {
    private String value;

    public Operand(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int getIntValue() {
        return Integer.parseInt(value);
    }
}

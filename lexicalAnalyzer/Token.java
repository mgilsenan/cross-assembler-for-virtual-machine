package lexicalAnalyzer;

public class Token {
    private String value;
    private int line;
    private int pos;
    private int tokenInt;
    Token(String value, int line, int pos, int tokenInt) {
        this.value = value;
        this.line = line;
        this.pos = pos;
        this.tokenInt = tokenInt;// 1: mnemonic, 2: label, 3: directive, 4: number, 5: minus, 6: comment, 7: string
    }
    
	@Override
	public String toString() {
		return "Token [value=" + value + ", line=" + line + ", pos=" + pos + ", tokenInt=" + tokenInt + "]";
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public int getTokenInt() {
		return tokenInt;
	}
	public void setTokenInt(int tokenInt) {
		this.tokenInt = tokenInt;
	}
    
}

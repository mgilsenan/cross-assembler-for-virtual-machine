package lexicalAnalyzer;
public class Position {
	private int lineNumber;
	private int columnNumber;
	
	public Position(int lineNumber, int columnNumber) {
		this.lineNumber = lineNumber;
		this.columnNumber = columnNumber;
	}

	// Getters and Setters:
	public int getLineNumber() { return lineNumber; }
	public void setLineNumber(int lineNumber) { this.lineNumber = lineNumber; }
	public int getColumnNumber() { return columnNumber; }
	public void setColumnNumber(int columnNumber) { this.columnNumber = columnNumber; }
}
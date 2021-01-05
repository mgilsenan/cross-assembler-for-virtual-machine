package errorHandling;

import lexicalAnalyzer.Position;

public class ErrorMessage implements IErrorMessage {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	private String errorMessage;
	private Position errorPosition;
	   
	public ErrorMessage(Position errorPosition, String errorMessage) { 
		this.errorPosition = errorPosition;
		this.errorMessage = errorMessage;
		ErrorReporter.addError(this);
		printErrorMessage();
	}
	
	@Override
	public void printErrorMessage() { 
		System.out.printf("Error found at Error-Position: line#%4d   column#%4d   with Error-Message: %s\n", 
											errorPosition.getLineNumber(), errorPosition.getColumnNumber(), errorMessage); 
	}
	
	// Getters
	public Position getErrorPosition() { return errorPosition; }
	public String getErrorMessage() { return errorMessage; }
}

package errorHandling;
import java.util.ArrayList;
import java.util.List;

public class ErrorReporter {
	ErrorReporter errorReporter;
	
	public static List<IErrorMessage> foundErrors;
	
	static { foundErrors = new ArrayList<IErrorMessage>(); }
	
	public static void addError(IErrorMessage errorMessage) {
		foundErrors.add(errorMessage);
	}
	
	public static void displayFoundErrorsReport() {
		System.out.println("Summary of Errors Found in the Assembly SourceFile:");
		for(IErrorMessage errorMessage : foundErrors) { errorMessage.printErrorMessage(); }
	}
	
	// Used for testing purposes only.
	public static void resetReporter() { foundErrors = new ArrayList<IErrorMessage>(); }
}
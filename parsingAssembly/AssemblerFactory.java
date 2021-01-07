package parsingAssembly;
import SymbolTable.ISymbolTable;
import lexicalAnalyzer.Lexer;

public class AssemblerFactory {

	private Lexer lexer;
	private ISymbolTable symbolTable;

	public AssemblerFactory(Lexer lexer, ISymbolTable symbolTable) {
		this.lexer = lexer;
		this.symbolTable = symbolTable;
	}

	public Lexer getLexer() {
		return lexer;
	}

	public ISourceFile getSourceFile() {
		return null;
	}

	public IReportable getErrorReporter() {
		return null;
	}

	public ISymbolTable getSymbolTable() {
		return symbolTable;
	}
    
}

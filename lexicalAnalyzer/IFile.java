package lexicalAnalyzer;
import java.io.File;

public interface IFile {
	public String getSrcFileName();
	public boolean validateSrcFile();
	public File getFile();
}
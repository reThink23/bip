package ueb3;

import java.io.IOException;

public class FileFormatException extends IOException {
	FileFormatException() { super("The file does not have the right format."); }
	FileFormatException(String message) { super(message); }
}

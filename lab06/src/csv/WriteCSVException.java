package csv;

public class WriteCSVException extends CSVException {
	WriteCSVException() {
		super();
	}

	WriteCSVException(String message) {
		super(message);
	}

	WriteCSVException(String message, Throwable e) {
		super(message, e);
	}
}

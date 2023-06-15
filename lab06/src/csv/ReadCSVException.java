package csv;

public class ReadCSVException extends CSVException {
	ReadCSVException() {
		super();
	}

	ReadCSVException(String message) {
		super(message);
	}

	ReadCSVException(String message, Throwable e) {
		super(message, e);
	}
}

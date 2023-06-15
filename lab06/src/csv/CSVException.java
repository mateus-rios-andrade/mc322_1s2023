package csv;

public class CSVException extends RuntimeException {
	
	CSVException() {
		super();
	}

	CSVException(String message) {
		super(message);
	}

	CSVException(String message, Throwable e) {
		super(message, e);
	}
}

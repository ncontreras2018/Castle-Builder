package throwables;

@SuppressWarnings("serial")
public class IllegalLocationException extends RuntimeException {

	public IllegalLocationException() {
	}

	public IllegalLocationException(String s) {
		super(s);
	}
}

package bus;

public class RaiseException extends Exception{

	private static final long serialVersionUID = 98740598831837124L;
	
	public RaiseException() {
		super();
	}
	public RaiseException(String message) {
		super(message);
	}
}

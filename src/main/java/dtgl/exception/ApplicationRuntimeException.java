package dtgl.exception;

public class ApplicationRuntimeException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ApplicationRuntimeException(String message) {
		super(message);
	}
	
}

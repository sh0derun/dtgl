package dtgl.exception;

public class ShaderException extends ApplicationRuntimeException{

	private static final long serialVersionUID = 1L;

	public ShaderException(String shaderType, String shaderPath, String message) {
		super(shaderType + " : " + shaderPath + System.lineSeparator() + message);
	}
	
}

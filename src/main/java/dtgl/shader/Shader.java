package dtgl.shader;

import dtgl.engine.component.Light;
import dtgl.engine.component.PointLight;
import dtgl.engine.component.material.PhongMaterial;
import dtgl.exception.ApplicationRuntimeException;
import dtgl.exception.ShaderException;
import dtgl.math.Mat4;
import dtgl.math.Vec2;
import dtgl.math.Vec3;
import dtgl.math.Vec4;
import dtgl.utils.FileUtils;
import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL30.*;

/*Shader class
* All shaders that have compilation error will be replaced by colored (ERROR_COLOR) shader (VS_ERROR, FS_ERROR)*/

public class Shader {

	private static final Map<Integer, String> shaderTypes = new HashMap<Integer, String>() {{
		put(GL_VERTEX_SHADER, "GL_VERTEX_SHADER");
		put(GL_FRAGMENT_SHADER, "GL_FRAGMENT_SHADER");
	}};

	private static final String ERROR_VS = "shaders/VS_ERROR.glsl";
	private static final String ERROR_FS = "shaders/FS_ERROR.glsl";
	public static final Vec4 ERROR_COLOR = new Vec4(1,0,0,1);

	private int programId;
	private String vsPath, fsPath;
	public boolean inError;
	private static final String NO_UNIFORM_NAME_ERROR = "no uniform name found ";

	private Map<String, Integer> locationsMap = new HashMap<>();

	public Shader(String vsPath, String fsPath) {
		this.vsPath = FileUtils.getPath(vsPath).getPath();
		this.fsPath = FileUtils.getPath(fsPath).getPath();
		this.inError = false;
		this.programId = loadShaders(this.vsPath, this.fsPath);
	}

	private int loadShaders(String vsPath, String fsPath) {
		int programId = glCreateProgram();
		int vsId = -1, fsId = -1;

		try {
			String vsSource = FileUtils.loadShaderFile(vsPath);
			String fsSource = FileUtils.loadShaderFile(fsPath);

			vsId = createCompileAttachShader(GL_VERTEX_SHADER, vsPath, vsSource, programId);
			fsId = createCompileAttachShader(GL_FRAGMENT_SHADER, fsPath, fsSource, programId);
		} catch(ShaderException e){
			inError = true;

			glDeleteProgram(programId);
			glDeleteShader(vsId);
			glDeleteShader(fsId);

			programId = glCreateProgram();
			vsId = createCompileAttachShader(GL_VERTEX_SHADER, ERROR_VS, FileUtils.loadShaderFile(ERROR_VS), programId);
			fsId = createCompileAttachShader(GL_FRAGMENT_SHADER, ERROR_FS, FileUtils.loadShaderFile(ERROR_FS), programId);

			System.out.println(e.getMessage());
		}

		glLinkProgram(programId);
		glValidateProgram(programId);

		glDeleteShader(vsId);
		glDeleteShader(fsId);

		IntBuffer intBuffer = BufferUtils.createIntBuffer(1);
		glGetProgramiv(programId, GL_ACTIVE_UNIFORMS, intBuffer);

		System.out.println(intBuffer.get());

		return programId;
	}

	private int createCompileAttachShader(int shaderType, String shaderPath, String shaderSource, int programId) throws ShaderException {
		int shaderId = glCreateShader(shaderType);
		glShaderSource(shaderId, shaderSource);
		glCompileShader(shaderId);
		int res = glGetShaderi(shaderId, GL_COMPILE_STATUS);
		if (res == GL_FALSE) {
			String infoLog = glGetShaderInfoLog(shaderId);
			throw new ShaderException(shaderTypes.get(shaderType), shaderPath, infoLog);
		}
		glAttachShader(programId, shaderId);
		return shaderId;
	}

	public int getUniformLocation(String name) {
		if(!locationsMap.containsKey(name)){
			int location = glGetUniformLocation(programId, name);
			if(location == -1) {
				throw new ApplicationRuntimeException(NO_UNIFORM_NAME_ERROR + name);
			}
			locationsMap.put(name, location);
		}
		return locationsMap.get(name);
	}

	public void setUniform(String name, float x){
		glUniform1f(getUniformLocation(name), x);
	}

	public void setUniform(String name, Vec2 vec2){
		glUniform2fv(getUniformLocation(name), vec2.getCoords());
	}
	
	public void setUniform(String name, Vec3 vec3){
		glUniform3fv(getUniformLocation(name), vec3.getCoords());
	}
	
	public void setUniform(String name, Vec4 vec4){
		glUniform4fv(getUniformLocation(name), vec4.getCoords());
	}

	public void setUniform(String name, Mat4 mat4){
		glUniformMatrix4fv(getUniformLocation(name), false, mat4.getValues());
	}

	public <L extends Light> void setUniform(String name, L light) {
		if(light instanceof PointLight){
			PointLight pointLight = (PointLight) light;
			glUniform3fv(getUniformLocation(name+".position"), pointLight.getPosition().getCoords());
			glUniform3fv(getUniformLocation(name+".color"), pointLight.getColor().getCoords());
		}
	}

	public void setUniform(String name, PhongMaterial material) {
		glUniform3fv(getUniformLocation(name + ".ambient"), material.getAmbient().getCoords());
		glUniform3fv(getUniformLocation(name + ".diffuse"), material.getDiffuse().getCoords());
		glUniform3fv(getUniformLocation(name + ".specular"), material.getSpecular().getCoords());
		glUniform1f(getUniformLocation(name + ".shininess"), material.getShininess());
	}

	public void setUniform(String name, int i){
		glUniform1i(getUniformLocation(name), i);
	}

	public void activate() {
		glUseProgram(programId);
	}

	public void deactivate() {
		glUseProgram(0);
	}

	@Override
	public String toString() {
		return "Shader{" +
				"\n\tvsPath='" + vsPath + '\'' +
				", \n\tfsPath='" + fsPath + '\'' +
				'}';
	}
}

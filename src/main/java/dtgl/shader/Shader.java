package dtgl.shader;

import static org.lwjgl.opengl.GL30.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import dtgl.exception.ApplicationRuntimeException;
import dtgl.math.Mat4;
import dtgl.math.Vec2;
import dtgl.math.Vec3;
import dtgl.math.Vec4;
import dtgl.utils.FileUtils;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

public class Shader {

	int programId;
	String vsPath, fsPath;
	private static final String NO_UNIFORM_NAME_ERROR = "no uniform name found ";

	private Map<String, Integer> locationsMap = new HashMap<>();

	public Shader(String vsPath, String fsPath) {
		this.vsPath = vsPath;
		this.fsPath = fsPath;
		String vsSource = FileUtils.loadFile(vsPath);
		String fsSource = FileUtils.loadFile(fsPath);

		this.programId = loadShaders(vsSource, fsSource);
	}

	private int createCompileAttachShader(int shaderType, String shaderSource, int programId){
		int shaderId = glCreateShader(shaderType);
		glShaderSource(shaderId, shaderSource);
		glCompileShader(shaderId);
		int res = glGetShaderi(shaderId, GL_COMPILE_STATUS);
		if (res == GL_FALSE) {
			String infoLog = glGetShaderInfoLog(shaderId);
			throw new ApplicationRuntimeException(infoLog);
		}
		glAttachShader(programId, shaderId);
		return shaderId;
	}

	private int loadShaders(String vsSource, String fsSource) {
		int programId = glCreateProgram();

		// vertex shader
		int vsId = createCompileAttachShader(GL_VERTEX_SHADER, vsSource, programId);

		// fragment shader
		int fsId = createCompileAttachShader(GL_FRAGMENT_SHADER, fsSource, programId);

		glLinkProgram(programId);
		glValidateProgram(programId);

		glDeleteShader(vsId);
		glDeleteShader(fsId);

		return programId;
	}

	private int getUniformLocation(String name) {
		if(!locationsMap.containsKey(name)){
			int location = glGetUniformLocation(programId, name);
			if(location == -1) {
				throw new ApplicationRuntimeException(NO_UNIFORM_NAME_ERROR + name);
			}
			locationsMap.put(name, location);
		}
		return locationsMap.get(name);
	}

	public void setUniformFloat(String name, float x){
		glUniform1f(getUniformLocation(name), x);
	}

	public void setUniformVec2(String name, Vec2 vec2){
		glUniform2fv(getUniformLocation(name), vec2.getCoords());
	}
	
	public void setUniformVec3(String name, Vec3 vec3){
		glUniform3fv(getUniformLocation(name), vec3.getCoords());
	}
	
	public void setUniformVec4(String name, Vec4 vec4){
		glUniform4fv(getUniformLocation(name), vec4.getCoords());
	}
	
	public void setUniformMat4(String name, Mat4 mat4){
		glUniformMatrix4fv(getUniformLocation(name), false, mat4.getValues());
	}
	
	public void setUniformInt(String name, int i){
		glUniform1i(getUniformLocation(name), i);
	}
	
	public void activate() {
		glUseProgram(programId);
	}

	public void deactivate() {
		glUseProgram(0);
	}

	private FloatBuffer floatArrayToFloatBuffer(float[] data) {
		ByteBuffer buf = ByteBuffer.allocateDirect(data.length * 4);
		buf.order(ByteOrder.nativeOrder());
		FloatBuffer buffer = buf.asFloatBuffer();
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

}

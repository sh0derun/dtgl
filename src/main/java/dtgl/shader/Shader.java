package dtgl.shader;

import static org.lwjgl.opengl.GL30.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import dtgl.exception.ApplicationRuntimeException;
import dtgl.math.Mat4;
import dtgl.math.Vec2;
import dtgl.math.Vec3;
import dtgl.math.Vec4;
import dtgl.utils.FileUtils;

public class Shader {

	int programId;
	String vsPath, fsPath;
	private static final String NO_UNIFORM_NAME_ERROR = "no uniform name found ";

	public Shader(String vsPath, String fsPath) {
		this.vsPath = vsPath;
		this.fsPath = fsPath;
		String vsSource = FileUtils.loadFile(vsPath);
		String fsSource = FileUtils.loadFile(fsPath);

		this.programId = loadShaders(vsSource, fsSource);
	}

	private int loadShaders(String vsSource, String fsSource) {
		int programId = glCreateProgram();
		int vsId = glCreateShader(GL_VERTEX_SHADER);
		int fsId = glCreateShader(GL_FRAGMENT_SHADER);
		// vertex shader
		glShaderSource(vsId, vsSource);
		glCompileShader(vsId);
		int res = glGetShaderi(vsId, GL_COMPILE_STATUS);
		if (res == GL_FALSE) {
			String infoLog = glGetShaderInfoLog(vsId);
			throw new ApplicationRuntimeException(infoLog);
		}
		glAttachShader(programId, vsId);

		// fragment shader
		glShaderSource(fsId, fsSource);
		glCompileShader(fsId);
		res = glGetShaderi(fsId, GL_COMPILE_STATUS);
		if (res == GL_FALSE) {
			String infoLog = glGetShaderInfoLog(fsId);
			throw new ApplicationRuntimeException(infoLog);
		}
		glAttachShader(programId, fsId);

		glLinkProgram(programId);
		glValidateProgram(programId);

		glDeleteShader(vsId);
		glDeleteShader(fsId);

		return programId;
	}

	public void setUniformFloat(String name, float x){
		int location = glGetUniformLocation(programId, name);
		if(location == -1) {
			throw new ApplicationRuntimeException(NO_UNIFORM_NAME_ERROR + name);
		}
		glUniform1f(location, x);
	}
	
	public void setUniformVec2(String name, Vec2 vec2){
		int location = glGetUniformLocation(programId, name);
		if(location == -1) {
			throw new ApplicationRuntimeException(NO_UNIFORM_NAME_ERROR + name);
		}
		glUniform2fv(location, vec2.getCoords());
	}
	
	public void setUniformVec3(String name, Vec3 vec3){
		int location = glGetUniformLocation(programId, name);
		if(location == -1) {
			throw new ApplicationRuntimeException(NO_UNIFORM_NAME_ERROR + name);
		}
		glUniform3fv(location, vec3.getCoords());
	}
	
	public void setUniformVec4(String name, Vec4 vec4){
		int location = glGetUniformLocation(programId, name);
		if(location == -1) {
			throw new ApplicationRuntimeException(NO_UNIFORM_NAME_ERROR + name);
		}
		glUniform4fv(location, vec4.getCoords());
	}
	
	public void setUniformMat4(String name, Mat4 mat4){
		int location = glGetUniformLocation(programId, name);
		if(location == -1) {
			throw new ApplicationRuntimeException(NO_UNIFORM_NAME_ERROR + name);
		}
		glUniformMatrix4fv(location, false, mat4.getValues());
	}
	
	public void setUniformInt(String name, int i){
		int location = glGetUniformLocation(programId, name);
		if(location == -1) {
			throw new ApplicationRuntimeException(NO_UNIFORM_NAME_ERROR + name);
		}
		glUniform1i(location, i);
	}
	
	public void activate() {
		glUseProgram(programId);
	}

	public void deactivate() {
		glUseProgram(0);
	}

}

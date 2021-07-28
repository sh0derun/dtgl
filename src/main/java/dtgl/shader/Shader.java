package dtgl.shader;

import org.lwjgl.opengl.GL30;

import dtgl.math.Mat4;
import dtgl.math.Vec2;
import dtgl.math.Vec3;
import dtgl.math.Vec4;
import dtgl.utils.FileUtils;

public class Shader {

	int programId;
	String vsPath, fsPath;

	public Shader(String vsPath, String fsPath) {
		this.vsPath = vsPath;
		this.fsPath = fsPath;
		String vsSource = FileUtils.loadFile(vsPath);
		String fsSource = FileUtils.loadFile(fsPath);

		this.programId = loadShaders(vsSource, fsSource);
	}

	private int loadShaders(String vsSource, String fsSource) {
		int programId = GL30.glCreateProgram();
		int vsId = GL30.glCreateShader(GL30.GL_VERTEX_SHADER);
		int fsId = GL30.glCreateShader(GL30.GL_FRAGMENT_SHADER);
		// vertex shader
		GL30.glShaderSource(vsId, vsSource);
		GL30.glCompileShader(vsId);
		int res = GL30.glGetShaderi(vsId, GL30.GL_COMPILE_STATUS);
		if (res == GL30.GL_FALSE) {
			String infoLog = GL30.glGetShaderInfoLog(vsId);
			System.out.println(infoLog);
		}
		GL30.glAttachShader(programId, vsId);

		// fragment shader
		GL30.glShaderSource(fsId, fsSource);
		GL30.glCompileShader(fsId);
		res = GL30.glGetShaderi(fsId, GL30.GL_COMPILE_STATUS);
		if (res == GL30.GL_FALSE) {
			String infoLog = GL30.glGetShaderInfoLog(fsId);
			System.out.println(infoLog);
		}
		GL30.glAttachShader(programId, fsId);

		GL30.glLinkProgram(programId);
		GL30.glValidateProgram(programId);

		GL30.glDeleteShader(vsId);
		GL30.glDeleteShader(fsId);

		return programId;
	}

	public void setUniformFloat(String name, float x){
		GL30.glUniform1f(GL30.glGetUniformLocation(programId, name), x);
	}
	
	public void setUniformVec2(String name, Vec2 vec2){
		GL30.glUniform2fv(GL30.glGetUniformLocation(programId, name), vec2.getCoords());
	}
	
	public void setUniformVec3(String name, Vec3 vec3){
		GL30.glUniform3fv(GL30.glGetUniformLocation(programId, name), vec3.getCoords());
	}
	
	public void setUniformVec4(String name, Vec4 vec4){
		GL30.glUniform4fv(GL30.glGetUniformLocation(programId, name), vec4.getCoords());
	}
	
	public void setUniformMat4(String name, Mat4 mat4){
		GL30.glUniformMatrix4fv(GL30.glGetUniformLocation(programId, name), false, mat4.getValues());
	}
	
	public void activate() {
		GL30.glUseProgram(programId);
	}

	public void deactivate() {
		GL30.glUseProgram(0);
	}

}

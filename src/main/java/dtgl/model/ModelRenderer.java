package dtgl.model;

import dtgl.display.Window;
import dtgl.math.Mat4;
import dtgl.math.Vec2;
import dtgl.shader.*;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.lwjgl.opengl.GL30.*;

public class ModelRenderer {

	UniformsModifier uniformsModifier = new UniformsModifierDirect();
	
	public void render(Model model, Shader shader, Window window) {
		glBindVertexArray(model.getVao());
		enableVertexAttribArrays();

		List<Uniform> mat4s = Arrays.asList(new Uniform<>(UniformType.MAT4,"ml_mat",Mat4.getTransformationMat(model.getPos(), model.getRot(), model.getScale())));
		List<Uniform> vec2s = Arrays.asList(new Uniform<>(UniformType.VEC2, "resolution", window == null ? new Vec2(500, 500) : new Vec2(window.getWidth(), window.getHeight())));
		List<Uniform> floats = Arrays.asList(new Uniform<>(UniformType.FLOAT,"time", (float)(GLFW.glfwGetTime())));
		List<Uniform> uniformTextures = Arrays.stream(model.getTextures().orElse(new Texture[]{}))
												.map(texture -> new Uniform<>(UniformType.SAMPLER_2D, texture.getUniformName(), texture))
												.collect(Collectors.toList());

		EnumMap<UniformType, List<Uniform>> uniformsMap = new EnumMap<>(UniformType.class);
		uniformsMap.put(UniformType.FLOAT, floats);
		uniformsMap.put(UniformType.VEC2, vec2s);
		uniformsMap.put(UniformType.MAT4, mat4s);
		uniformsMap.put(UniformType.SAMPLER_2D, uniformTextures);

		uniformsModifier.updateUniformsValues(shader, uniformsMap);

		glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
		disableVertexAttribArrays();
		glBindVertexArray(0);
	}
	
	private void enableVertexAttribArrays() {
		glEnableVertexAttribArray(0);//position
		glEnableVertexAttribArray(1);//color
		glEnableVertexAttribArray(2);//texture coords
	}
	
	private void disableVertexAttribArrays() {
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
	}

}

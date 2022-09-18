package dtgl.model;

import dtgl.display.Window;
import dtgl.math.Mat4;
import dtgl.math.Vec2;
import dtgl.math.Vec3;
import dtgl.shader.*;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.lwjgl.opengl.GL30.*;

public class ModelRenderer {

	private UniformsHandler uniformsHandler;
	private Camera camera = new Camera();
	private Mat4 projection;

	public ModelRenderer(Window window){
		uniformsHandler = new DefaultUniformsHandler();
		projection = Mat4.perspective(70, 1.0f*window.getWidth()/window.getHeight(), 0.1f, 30);
	}
	
	public void render(Model model, Shader shader, Window window) {
		glBindVertexArray(model.getVao());

		double time = GLFW.glfwGetTime();

		float radius = 5.0f;
		float camX = (float) (Math.cos(time) * radius);
		float camY = 0;
		float camZ = (float) (Math.sin(time) * radius) + (float)(Math.cos(time * 0.5) * radius * 0.5f);

		camera.setPos(new Vec3(camX, camY, camZ));

		projection = Mat4.perspective(70, 1.0f*window.getWidth()/window.getHeight(), 0.1f, 30);

		List<Uniform> mat4s = Arrays.asList(
				new Uniform(UniformType.MAT4,"model",Mat4.getTransformationMat(model.getPos(), model.getRot(), model.getAngle(), model.getScale())),
				new Uniform(UniformType.MAT4,"view", Mat4.lookAt(camera.getPos(), camera.getTarget(), Vec3.UP)),
				new Uniform(UniformType.MAT4, "projection", projection));

		List<Uniform> vec2s = Arrays.asList(new Uniform(UniformType.VEC2, "resolution", new Vec2(window.getWidth(), window.getHeight())));
		List<Uniform> floats = Arrays.asList(new Uniform(UniformType.FLOAT,"time", (float)time));
		List<Uniform> uniformTextures = Arrays.stream(model.getTextures().orElse(new Texture[]{}))
												.map(texture -> new Uniform(UniformType.SAMPLER_2D, texture.getUniformName(), texture))
												.collect(Collectors.toList());

		EnumMap<UniformType, List<Uniform>> uniformsMap = new EnumMap<>(UniformType.class);
		uniformsMap.put(UniformType.FLOAT, floats);
		uniformsMap.put(UniformType.VEC2, vec2s);
		uniformsMap.put(UniformType.MAT4, mat4s);
		uniformsMap.put(UniformType.SAMPLER_2D, uniformTextures);

		uniformsHandler.updateUniformsValues(shader, uniformsMap);

		enableVertexAttribArrays();
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

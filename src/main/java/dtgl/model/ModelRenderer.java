package dtgl.model;

import dtgl.display.Window;
import dtgl.math.Mat4;
import dtgl.math.Vec3;
import dtgl.shader.DefaultUniformsHandler;
import dtgl.shader.Shader;
import dtgl.shader.Uniform;
import dtgl.shader.UniformType;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ModelRenderer extends AbstractRenderer{

	private Camera camera = new Camera();
	private Mat4 projection;

	public ModelRenderer(){
		super(new DefaultUniformsHandler());
	}
	
	public void render(Model model, Shader shader, Window window) {
		GL30.glBindVertexArray(model.getVao());

		List<Uniform> uniforms = getUniformList(model, shader, window);

		uniformsHandler.updateUniformsValues(shader, uniforms);

		enableVertexAttribArrays(model.getAttributesIndices());
		GL30.glDrawElements(GL30.GL_TRIANGLES, model.getVertexCount(), GL30.GL_UNSIGNED_INT, 0);
		disableVertexAttribArrays(model.getAttributesIndices());

		GL30.glBindVertexArray(0);
	}

	private List<Uniform> getUniformList(Model model, Shader shader, Window window) {
		double time = GLFW.glfwGetTime();

		float radius = 5.0f;
		float camX = (float) (Math.cos(time) * radius);
		float camY = 0;
		float camZ = (float) (Math.sin(time) * radius) + (float) (Math.cos(time * 0.5) * radius * 0.5f);

		camera.setPos(new Vec3(camX, camY, camZ));

		projection = Mat4.perspective(70, 1.0f * window.getWidth() / window.getHeight(), 0.1f, 30);

		List<Uniform> uniforms = new ArrayList<>(Arrays.asList(
				new Uniform(UniformType.MAT4, "model", Mat4.getTransformationMat(model.getPos(), model.getRot(), model.getAngle(), model.getScale())),
				new Uniform(UniformType.MAT4, "view", /*Mat4.lookAt(camera.getPos(), camera.getTarget(), Vec3.UP)*/Mat4.translate(Vec3.TOWARD.mult(-10))),
				new Uniform(UniformType.MAT4, "projection", projection)));

		List<Uniform> uniformTextures = Arrays.stream(model.getTextures().orElse(new Texture[]{}))
				.map(texture -> new Uniform(UniformType.SAMPLER_2D, texture.getUniformName(), texture))
				.collect(Collectors.toList());

		uniforms.addAll(uniformTextures);

		if (shader.inError) {
			uniforms.add(new Uniform(UniformType.VEC4, "error_color", Shader.ERROR_COLOR));
		}

		return uniforms;
	}


}

package dtgl.renderer;

import dtgl.display.Window;
import dtgl.math.Mat4;
import dtgl.math.Vec3;
import dtgl.model.Camera;
import dtgl.model.Model;
import dtgl.model.Texture;
import dtgl.shader.DefaultUniformsHandler;
import dtgl.shader.Shader;
import dtgl.shader.Uniform;
import dtgl.shader.UniformType;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ModelRenderer extends AbstractRenderer{

	private final Camera camera = new Camera();

	public ModelRenderer(){
		super(new DefaultUniformsHandler());
	}
	
	public void render(Model model) {
		GL30.glBindVertexArray(model.getVao());

		model.getShader().activate();

		List<Uniform> uniforms = getUniformList(model, window, model.getUniforms());

		uniformsHandler.updateUniformsValues(model.getShader(), uniforms);

		enableVertexAttribArrays(model.getAttributesIndices());
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		disableVertexAttribArrays(model.getAttributesIndices());

		model.getShader().deactivate();

		GL30.glBindVertexArray(0);
	}

	private List<Uniform> getUniformList(Model model, Window window, List<Uniform> uniforms) {

		Mat4 projection = Mat4.perspective(70, 1.0f * window.getWidth() / window.getHeight(), 0.1f, 30);

		List<Uniform> requiredUniforms = new ArrayList<>(Arrays.asList(
				new Uniform(UniformType.MAT4, "model", Mat4.getTransformationMat(model.getPos(), model.getRot(), model.getAngle(), model.getScale())),
				new Uniform(UniformType.MAT4, "view", Mat4.translate(Vec3.TOWARD.mult(-10))),
				new Uniform(UniformType.MAT4, "projection", projection)));

		List<Uniform> uniformTextures = Arrays.stream(model.getTextures().orElse(new Texture[]{}))
				.map(texture -> new Uniform(UniformType.SAMPLER_2D, texture.getUniformName(), texture))
				.collect(Collectors.toList());

		uniforms.addAll(uniformTextures);

		if (model.getShader().inError) {
			requiredUniforms.add(new Uniform(UniformType.VEC4, "error_color", Shader.ERROR_COLOR));
		}
		else {
			Uniform lightColor = new Uniform(UniformType.VEC3, "light_color", new Vec3(1, 1, 1));
			requiredUniforms.add(lightColor);

			double time = GLFW.glfwGetTime();

			float radius = 20.0f;
			float lightPosX = (float) (Math.sin(time) * radius) * (float) (Math.cos(time) * radius);
			float lightPosY = (float) (Math.sin(time) * radius) * (float) (Math.sin(time) * radius);
			float lightPosZ = (float) (Math.cos(time) * radius);

			Uniform lightPos = new Uniform(UniformType.VEC3, "light_pos", new Vec3(0, 0, 7)/*new Vec3((float) (Math.cos(time) * 5), (float) (Math.sin(time) * 5), 7)*/);
			requiredUniforms.add(lightPos);

			requiredUniforms.addAll(uniforms);
		}



		return requiredUniforms;
	}


}

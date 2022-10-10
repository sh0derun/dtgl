package dtgl;

import dtgl.engine.DTGLEngine;
import dtgl.math.Vec3;
import dtgl.model.Model;
import dtgl.model.ModelRenderer;
import dtgl.shader.Shader;
import dtgl.utils.obj.OBJModel;
import dtgl.utils.obj.OBJModelLoader;
import org.lwjgl.glfw.GLFW;

public class Main {

	public static void main(String[] args) {

		DTGLEngine engine = new DTGLEngine(new ModelRenderer());

		Shader shader = new Shader("shaders/vs_isosphere.glsl", "shaders/fs_isosphere.glsl");

		OBJModel objModel = OBJModelLoader.LoadModelDataFromOBJ("res/isosphere.obj");
		Model model = engine.getModelLoader().load(objModel, null);
		model.setScale(2);

		while(!engine.getWindow().isClosed()) {
			float time = (float)GLFW.glfwGetTime();
			engine.getWindow().clear();

			model.setRot(new Vec3(time*20,time*20, time*20));
			shader.activate();
			engine.getRenderer().render(model, shader, engine.getWindow());
			shader.deactivate();

			engine.handleInputs();
			engine.getWindow().update();
		}
		
		engine.getModelLoader().clean();
		engine.getWindow().free();
	}

}
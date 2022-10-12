package dtgl;

import dtgl.engine.DTGLEngine;
import dtgl.engine.EngineSetup;
import dtgl.math.Vec3;
import dtgl.model.Model;
import dtgl.model.ModelLoader;
import dtgl.engine.EngineLogic;
import dtgl.renderer.AbstractRenderer;
import dtgl.renderer.ModelRenderer;
import dtgl.shader.Shader;
import dtgl.utils.obj.OBJModel;
import dtgl.utils.obj.OBJModelLoader;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		List<Model> models = new ArrayList<>();

		EngineSetup engineSetup = (ModelLoader modelLoader)->{
			Shader shader = new Shader("shaders/vs_isosphere.glsl", "shaders/fs_isosphere.glsl");
			OBJModel objModel = OBJModelLoader.LoadModelDataFromOBJ("res/isosphere.obj");
			Model model = modelLoader.load(objModel, null, shader);
			model.setScale(2);
			models.add(model);
			return models;
		};

		EngineLogic engineLogic = (AbstractRenderer renderer, float time)->{
			for (Model model : models) {
				model.setRot(new Vec3(time*20,time*20, time*20));
				renderer.render(model);
			}
		};

		DTGLEngine engine = new DTGLEngine(new ModelRenderer(), engineSetup, engineLogic);

		engine.loop();

	}

}
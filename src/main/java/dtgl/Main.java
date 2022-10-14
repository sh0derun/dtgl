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
import dtgl.shader.Uniform;
import dtgl.shader.UniformType;
import dtgl.utils.obj.OBJModel;
import dtgl.utils.obj.OBJModelLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

public class Main {

	public static void main(String[] args) {

		List<Model> models = new ArrayList<>();
		final int COUNT = 16;
		Vec3[] renderedModelsColors = new Vec3[COUNT];
		Random rnd = new Random();

		BiFunction<Float, Float, Float> getRandomFloat = (min, max)->min + rnd.nextFloat() * (max - min);

		for (int i = 0; i < COUNT; i++) {
			renderedModelsColors[i] = new Vec3(getRandomFloat.apply(0f, 1f), getRandomFloat.apply(0f, 1f),getRandomFloat.apply(0f, 1f));
		}

		EngineSetup engineSetup = (ModelLoader modelLoader)->{
			Shader shader = new Shader("shaders/vs_icosphere.glsl", "shaders/fs_icosphere.glsl");
			OBJModel objModel = OBJModelLoader.LoadModelDataFromOBJ("res/icosphere_per_vertex_normals.obj");
			Model model = modelLoader.load(objModel, null, shader);
			model.setScale(0.6f);
			models.add(model);
			return models;
		};

		EngineLogic engineLogic = (AbstractRenderer renderer, float time)->{
			for (Model model : models) {
				for(int i = 0; i < 4; i++) {
					for(int j = 0; j < 4; j++) {
						for(int k = 0; k < 4; k++) {
							model.setPos(new Vec3((i - 1.5f) * 2, (j - 1.5f) * 2, (k - 1.5f) * 2));
							model.setRot(new Vec3(time * 5, time * 10, time * 15));
							Uniform modelColor = new Uniform(UniformType.VEC3, "model_color", renderedModelsColors[j + i + k * 3]);
							model.setUniforms(Arrays.asList(modelColor));
							renderer.render(model);
						}
					}
				}
			}
		};

		DTGLEngine engine = new DTGLEngine(new ModelRenderer(), engineSetup, engineLogic);

		engine.loop();

	}

}
package dtgl;

import dtgl.engine.DTGLEngine;
import dtgl.engine.EngineSetup;
import dtgl.engine.component.material.PhongMaterial;
import dtgl.engine.component.material.PhongMaterialConstants;
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
		PhongMaterial[] renderedModelsColors = new PhongMaterial[COUNT];
		Random rnd = new Random();

		BiFunction<Float, Float, Float> getRandomFloat = (min, max)->min + rnd.nextFloat() * (max - min);

		for (int i = 0; i < COUNT; i++) {
			renderedModelsColors[i] = PhongMaterialConstants.materials[rnd.nextInt(PhongMaterialConstants.materials.length)];
		}

		PhongMaterial material = new PhongMaterial("gold", new Vec3(0.24725f,0.1995f,0.0745f),
				new Vec3(0.75164f, 0.60648f,	0.22648f),
				new Vec3(0.628281f, 0.555802f, 0.366065f),51.2f);

		EngineSetup engineSetup = (ModelLoader modelLoader)->{
			Shader shader = new Shader("shaders/vs.glsl", "shaders/fscube.glsl");
			OBJModel objModel = OBJModelLoader.LoadModelDataFromOBJ("res/cube_flat.obj");
			Model model = modelLoader.loadCube(0.5f, null, shader);
//			model.setScale(2f);
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
							Uniform modelColor = new Uniform(UniformType.PHONG_MATERIAL, "model_material", renderedModelsColors[j + i + k * 3]);
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
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

		System.out.println(Arrays.toString(PhongMaterialConstants.class.getFields()));

		EngineSetup engineSetup = (ModelLoader modelLoader)->{
			Shader shader = new Shader("shaders/vs.glsl", "shaders/fscube.glsl");
			Shader shaderIcosphere = new Shader("shaders/vs_icosphere.glsl", "shaders/fs_icosphere.glsl");
			OBJModel objModel = OBJModelLoader.LoadModelDataFromOBJ("res/icosphere_per_vertex_normals.obj");
			Model cubeModel = modelLoader.loadCube(0.5f, null, shader);
			Model icosphereModel = modelLoader.load(objModel, null, shaderIcosphere);
			icosphereModel.setScale(0.8f);
			models.addAll(Arrays.asList(cubeModel, icosphereModel));
			return models;
		};

		EngineLogic engineLogic = (AbstractRenderer renderer, float time) -> {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					for (int k = 0; k < 4; k++) {
						Model model = models.get((j + i + k) % models.size());
						float offset = 1.5f;
						model.setPos(new Vec3((i - offset) * 2, (j - offset) * 2, (k - offset) * 2));
						model.setRot(new Vec3(time * 5, time * 10, time * 15));
						Uniform modelColor = new Uniform(UniformType.PHONG_MATERIAL, "model_material", renderedModelsColors[j + i + k * 3]);
						model.setUniforms(Arrays.asList(modelColor));
						renderer.render(model);
					}
				}
			}
		};

		DTGLEngine engine = new DTGLEngine(new ModelRenderer(), engineSetup, engineLogic);

		engine.loop();

	}

}
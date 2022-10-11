package dtgl;

import dtgl.engine.DTGLEngine;
import dtgl.model.Model;
import dtgl.model.ModelLoader;
import dtgl.renderer.ModelRenderer;
import dtgl.shader.Shader;
import dtgl.utils.obj.OBJModel;
import dtgl.utils.obj.OBJModelLoader;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {

		DTGLEngine engine = new DTGLEngine(new ModelRenderer(), (ModelLoader modelLoader)->{
			Shader shader = new Shader("shaders/vs_isosphere.glsl", "shaders/fs_isosphere.glsl");
			OBJModel objModel = OBJModelLoader.LoadModelDataFromOBJ("res/isosphere.obj");
			Model model = modelLoader.load(objModel, null, shader);
			model.setScale(2);
			return new ArrayList<>(Arrays.asList(model));
		});

		engine.loop();

	}

}
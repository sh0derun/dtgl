package dtgl;

import dtgl.display.Window;
import dtgl.display.listener.Listener;
import dtgl.math.Vec3;
import dtgl.model.Model;
import dtgl.model.ModelLoader;
import dtgl.model.ModelRenderer;
import dtgl.model.Texture;
import dtgl.shader.Shader;
import dtgl.utils.obj.OBJModel;
import dtgl.utils.obj.OBJModelLoader;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL30.GL_LUMINANCE;

public class Main {

	public static void main(String[] args) {

		Window window = Window.getInstance();
		Listener listener = Listener.getInstance();

		ModelLoader loader = new ModelLoader();

		/*Texture texture = new Texture("res/2037089.png", "texture2", GL_RGBA, GL_RGBA8);
		Texture texture1 = new Texture("res/bvb.png", "texture2", GL_RGBA, GL_RGBA8);
		Texture texture2 = new Texture("res/checker.png", "texture2", GL_LUMINANCE, GL_LUMINANCE);

		Random rnd = new Random();
		float size = 0.15f;
		List<Model> cubes = new ArrayList<>();

		Texture[] txs = new Texture[]{texture,texture1,texture2};

		Model[] cubess = {loader.loadCube(size, null),
						  loader.loadCube(size, new Texture[]{txs[0]}),
						  loader.loadCube(size, new Texture[]{txs[1]}),
						  loader.loadCube(size, new Texture[]{txs[2]})};

		List<Vec3> positions = new ArrayList<>();

		double r = 4;

		for(float i = 0; i <= 2.0*Math.PI; i+=(2*Math.PI)/12.0){
			for(float j = 0; j <= Math.PI; j+=Math.PI/8.0){
				float theta = j;
				float phi = i;
				float x = (float)(r*Math.sin(theta)*Math.cos(phi));
				float y = (float)(r*Math.cos(theta));
				float z = (float)(r*Math.sin(theta)*Math.sin(phi));
				Vec3 w = new Vec3(x, y, z);
				positions.add(w);
			}
		}

		for (int i = 0; i < positions.size(); i++){
			Model cube = cubess[i%cubess.length];
			System.out.println(cube.getTextures().isPresent() ? cube.getTextures().get()[0].getTexturePath() : "NONE");
		}

		Shader cubeShader = new Shader("shaders/vs.glsl", "shaders/fscube.glsl");
		Shader shader = new Shader("shaders/vs.glsl", "shaders/fs1.glsl");
*/
		Shader shader = new Shader("shaders/vs_isosphere.glsl", "shaders/fs_isosphere.glsl");

		OBJModel objModel = OBJModelLoader.LoadModelDataFromOBJ("res/isosphere.obj");
		Model model = loader.load(objModel, null);
		model.setScale(2);

		ModelRenderer renderer = new ModelRenderer(window);

		while(!window.isClosed()) {
			float time = (float)GLFW.glfwGetTime();
			window.clear();
			/*for(int i = 0; i < positions.size(); i++){
				Model cube = cubess[i%cubess.length];
				cube.setPos(positions.get(i));
				cube.setRot(new Vec3(time*20,time*20, time*20));
				if(!cube.getTextures().isPresent()){
					cubeShader.activate();
					renderer.render(cube, cubeShader, window);
					cubeShader.deactivate();
				}else {
					shader.activate();
					renderer.render(cube, shader, window);
					shader.deactivate();
				}
			}*/

			model.setRot(new Vec3(time*20,time*20, time*20));
			shader.activate();
			renderer.render(model, shader, window);
			shader.deactivate();

			window.update();
			if(listener.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
				break;
			}
		}
		
		loader.clean();
		window.free();
	}

	public static float getRandomFloat(float min, float max){
		return min + new Random().nextFloat() * (max - min);
	}

}
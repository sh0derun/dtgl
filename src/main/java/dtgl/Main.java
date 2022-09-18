package dtgl;

import dtgl.display.Window;
import dtgl.display.listener.Listener;
import dtgl.math.Vec3;
import dtgl.model.Model;
import dtgl.model.ModelLoader;
import dtgl.model.ModelRenderer;
import dtgl.model.Texture;
import dtgl.shader.Shader;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL30.GL_LUMINANCE;

public class Main {

	public static void main(String[] args) {

		Window window = Window.getInstance();
		Listener listener = Listener.getInstance();

		ModelLoader loader = new ModelLoader();

		Texture texture = new Texture("res/2037089.png", "texture2", GL_RGBA, GL_RGBA8);
		Texture texture1 = new Texture("res/bvb.png", "texture2", GL_RGBA, GL_RGBA8);
		Texture texture2 = new Texture("res/checker.png", "texture2", GL_LUMINANCE, GL_LUMINANCE);

		Random rnd = new Random();
		float size = 0.15f;
		List<Model> cubes = new ArrayList<>();

		Texture[] txs = new Texture[]{texture,texture1,texture2};

		for(float i = 0; i <= 2.0*Math.PI; i+=(2*Math.PI)/12.0){
			for(float j = 0; j <= Math.PI; j+=Math.PI/8.0){
				float theta = j;
				float phi = i;
				Vec3 w = new Vec3((float)(2.25*Math.sin(theta)*Math.cos(phi)) ,(float)(2.25*Math.sin(theta)*Math.sin(phi)), (float)(2.25*Math.cos(theta)));
				Model cube = (int)(j+i)%2==0 ? loader.loadCube(size, null) : loader.loadCube(size, new Texture[]{txs[rnd.nextInt(txs.length)]});
				cube.setPos(w);
				cubes.add(cube);
			}
		}

		Shader cubeShader = new Shader("shaders/vs.glsl", "shaders/fscube.glsl");
		Shader shader = new Shader("shaders/vs.glsl", "shaders/fs1.glsl");
		ModelRenderer renderer = new ModelRenderer(window);

		while(!window.isClosed()) {
			float time = (float)GLFW.glfwGetTime();
			window.clear();
			for(int i = 0; i < cubes.size(); i++){
				Model cube = cubes.get(i);
				if(!cube.getTextures().isPresent()){
					cubeShader.activate();
					renderer.render(cube, cubeShader, window);
					cubeShader.deactivate();
				}else {
					shader.activate();
					renderer.render(cube, shader, window);
					shader.deactivate();
				}
			}
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
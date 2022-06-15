package dtgl;

import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.glClearColor;

import org.lwjgl.glfw.GLFW;

import dtgl.display.Window;
import dtgl.display.listener.Listener;

import dtgl.math.Vec3;
import dtgl.math.Vec4;
import dtgl.model.Model;
import dtgl.model.ModelLoader;
import dtgl.model.ModelRenderer;
import dtgl.model.Texture;
import dtgl.shader.Shader;
import dtgl.utils.OBJModelLoader;
import org.lwjgl.system.CallbackI;

import static org.lwjgl.opengl.GL30.*;

public class Main {

	public static void main(String[] args) {
		Window window = Window.getInstance();
		Listener listener = Listener.getInstance();
		
		glClearColor(0,0,0,1);	
		
		Shader shader = new Shader("shaders/vs.glsl", "shaders/fs.glsl");
		ModelLoader loader = new ModelLoader();
		ModelRenderer renderer = new ModelRenderer();
		
		//OBJModelLoader.LoadModelDataFromOBJ("res/cube.obj");
		
		float[] vertices = {
			0.5f,  0.5f, 0.0f,   1.0f, 0.0f, 0.0f, 1,   1.0f, 1.0f,
			0.5f, -0.5f, 0.0f,   0.0f, 1.0f, 0.0f, 1,   1.0f, 0.0f,
		   -0.5f, -0.5f, 0.0f,   0.0f, 0.0f, 1.0f, 1,   0.0f, 0.0f,
		   -0.5f,  0.5f, 0.0f,   1.0f, 1.0f, 0.0f, 1,   0.0f, 1.0f
		};
		
		int[] indices = {
			0, 3, 2,
			2, 1, 0
		};
		
//		Texture texture = new Texture("res/checker.png", "texChecker");
//		texture.bindTexture();
//		texture.loadTexture(GL_LUMINANCE, GL_LUMINANCE);
		Texture texture = new Texture("res/2037089.png", "texture0", GL_RGBA, GL_RGBA8);
		Texture texture1 = new Texture("res/checker.png", "texture1", GL_LUMINANCE, GL_LUMINANCE);
		Texture[] textures = {texture, texture1};
		Model model = loader.load(vertices, indices, textures);
		//model.setRot(new Vec3(0,0,45));
		
		shader.activate();
		
		while(!window.isClosed()) {
			window.clear();
			//model.setScale((float)(Math.sin(GLFW.glfwGetTime())*0.5+0.5)+1.0f);
			//model.setRot(new Vec3(1,1,(float)(Math.sin(GLFW.glfwGetTime())*180)));
			//System.out.println((float)GLFW.glfwGetTime()%2.0);
			float f = 0.25f;//(float) Math.abs(Math.signum((Math.random()-0.5f)*1.0f));
			//model.setPos(new Vec3(f*((float)GLFW.glfwGetTime()%1.5f),0,0));
			//model.setScale((float)Math.sin(GLFW.glfwGetTime())*0.5f+0.5f);
			model.setRot(new Vec3(0,0,(float)GLFW.glfwGetTime()*30));
			renderer.render(model, shader);
			window.update();
			if(listener.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
				break;
			}
		}
		
		loader.clean();
		window.free();
	}

}
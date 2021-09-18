package dtgl;

import static org.lwjgl.opengl.GL11.glClearColor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.lwjgl.opengl.GL30;

import dtgl.display.Window;
import dtgl.display.listener.Listener;
import dtgl.math.Mat4;
import dtgl.math.Vec2;
import dtgl.math.Vec4;
import dtgl.model.Model;
import dtgl.model.ModelLoader;
import dtgl.model.ModelRenderer;
import dtgl.shader.Shader;

public class Main {

	public static void main(String[] args) {
		Window window = Window.getInstance();
		Listener listener = Listener.getInstance();
		
		glClearColor(0.3f, 0.6f, 0.2f, 1.0f);	
		
		Shader shader = new Shader("shaders/vs.glsl", "shaders/fs.glsl");
		ModelLoader loader = new ModelLoader();
		ModelRenderer renderer = new ModelRenderer();
		
		float vertices[] = {
			0.5f,  0.5f, 0.0f,   1.0f, 0.0f, 0.0f, 1,   1.0f, 1.0f,
			0.5f, -0.5f, 0.0f,   0.0f, 1.0f, 0.0f, 1,   1.0f, 0.0f,
		   -0.5f, -0.5f, 0.0f,   0.0f, 0.0f, 1.0f, 1,   0.0f, 0.0f,
		   -0.5f,  0.5f, 0.0f,   1.0f, 1.0f, 0.0f, 1,   0.0f, 1.0f
		};
		
		int indices[] = {
			0, 3, 2,
			2, 1, 0
		};
		
		GL30.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
		GL30.glEnable(GL30.GL_BLEND);
		
		Model model = loader.load(vertices, indices);
		
		shader.activate();
		Mat4 pers = Mat4.identity();
		shader.setUniformMat4("pr_mat", pers);
		shader.setUniformVec4("col", new Vec4(1, 0, 1, 1));
		
		Instant start = Instant.now();
			
		while(!window.isClosed()) {
			shader.setUniformMat4("pr_mat", pers);
			window.clear();
			shader.setUniformInt("texture1", 0);
			GL30.glActiveTexture(GL30.GL_TEXTURE0);
			System.out.println("loader.texList.get(0) = "+loader.texList.get(0));
			GL30.glBindTexture(GL30.GL_TEXTURE_2D, loader.texList.get(0));
			renderer.render(model);
			window.update();
			Instant end = Instant.now();
			long milli = ChronoUnit.MILLIS.between(start, end);
			shader.setUniformFloat("time", (float)(milli / 1000.0));
			shader.setUniformVec2("res", new Vec2(window.getWidth(), window.getHeight()));
		}
		
		loader.clean();
		window.free();
	}

}
package dtgl;

import static org.lwjgl.opengl.GL11.glClearColor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import dtgl.display.Window;
import dtgl.display.listener.Listener;
import dtgl.math.Vec2;
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
		
		float[] vertices = {
			-0.5f, 0.5f, 0.0f,
			-0.5f, -0.5f, 0.0f,
			0.5f, -0.5f, 0.0f,
			
			-0.5f, 0.5f, 0.0f,
			0.5f, 0.5f, 0.0f,
			0.5f, -0.5f, 0.0f
		};
		
		Model model = loader.load(vertices);
		
		shader.activate();
		
		Instant start = Instant.now();
			
		while(!window.isClosed()) {
			window.clear();
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
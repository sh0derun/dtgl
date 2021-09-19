package dtgl;

import static org.lwjgl.opengl.GL11.glClearColor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import dtgl.display.Window;
import dtgl.math.Mat4;
import dtgl.math.Vec2;
import dtgl.math.Vec3;
import dtgl.math.Vec4;
import dtgl.model.Model;
import dtgl.model.ModelLoader;
import dtgl.model.ModelRenderer;
import dtgl.shader.Shader;

import static org.lwjgl.opengl.GL30.*;

public class Main {

	public static void main(String[] args) {
		Window window = Window.getInstance();
		
		glClearColor(0,0,0,1);	
		
		Shader shader = new Shader("shaders/vs.glsl", "shaders/fs.glsl");
		ModelLoader loader = new ModelLoader();
		ModelRenderer renderer = new ModelRenderer();
		
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
		
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_BLEND);
		
		Model model = loader.load(vertices, indices);
		
		shader.activate();
		Mat4 pers = Mat4.identity();
		//shader.setUniformMat4("pr_mat", pers);
		
		Instant start = Instant.now();
		
		while(!window.isClosed()) {
			window.clear();
			shader.setUniformInt(model.getTexture().getUniformName(), 0);
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, model.getTexture().getId());
			renderer.render(model);
			window.update();
			Instant end = Instant.now();
			long milli = ChronoUnit.MILLIS.between(start, end);
			//shader.setUniformFloat("time", (float)(milli / 1000.0));
			//shader.setUniformVec2("res", new Vec2(window.getWidth(), window.getHeight()));
			//shader.setUniformMat4("ml_mat", Mat4.rotate((float)Math.sin(milli / 1000.0)*0, new Vec3(0,1,0)));
		}
		
		loader.clean();
		window.free();
	}

}
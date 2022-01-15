package dtgl.model;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.*;

import dtgl.math.Mat4;
import dtgl.shader.Shader;

public class ModelRenderer {
	
	public void render(Model model, Shader shader) {
		glBindVertexArray(model.getVao());
		enableVertexAtrribArrays();
		//shader.setUniformFloat("time", (float)(milli / 1000.0));
		//shader.setUniformVec2("res", new Vec2(window.getWidth(), window.getHeight()));
		if(model.getTexture() != null) {
			shader.setUniformInt(model.getTexture().getUniformName(), 0);
			glActiveTexture(GL_TEXTURE0);
	    	glBindTexture(GL_TEXTURE_2D, model.getTexture().getId());
		}
		Mat4 transformation = Mat4.getTransformationMat(model.getPos(), model.getRot(), model.getScale());
		shader.setUniformMat4("ml_mat", transformation);
		glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
		disableVertexAtrribArrays();
		glBindVertexArray(0);
	}
	
	private void enableVertexAtrribArrays() {
		glEnableVertexAttribArray(0);//position
		glEnableVertexAttribArray(1);//color
		glEnableVertexAttribArray(2);//texture coords
	}
	
	private void disableVertexAtrribArrays() {
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
	}

}

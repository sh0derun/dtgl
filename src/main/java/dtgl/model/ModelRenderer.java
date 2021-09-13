package dtgl.model;

import org.lwjgl.opengl.GL30;

public class ModelRenderer {
	
	public void render(Model model) {
		GL30.glBindVertexArray(model.getVao());
		GL30.glEnableVertexAttribArray(0);
		//GL30.glDrawArrays(GL30.GL_TRIANGLES, 0, model.getVertexCount());
		GL30.glDrawElements(GL30.GL_TRIANGLES, model.getVertexCount(), GL30.GL_UNSIGNED_INT, 0);
		GL30.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

}

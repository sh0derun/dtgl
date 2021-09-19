package dtgl.model;

import static org.lwjgl.opengl.GL30.*;

public class ModelRenderer {
	
	public void render(Model model) {
		glBindVertexArray(model.getVao());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glBindVertexArray(0);
	}

}

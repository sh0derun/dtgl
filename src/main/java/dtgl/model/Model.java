package dtgl.model;

public class Model {
	
	private int vao;
	private int vertexCount;
	private Texture texture;
	
	public Model(int vao, int vertexCount, Texture texture) {
		this.vao = vao;
		this.vertexCount = vertexCount;
		this.texture = texture;
	}

	public int getVao() {
		return vao;
	}

	public int getVertexCount() {
		return vertexCount;
	}
	
	public Texture getTexture() {
		return texture;
	}

}

package dtgl.model;


import dtgl.math.Vec3;

public class Model {
	
	private int vao;
	private int vertexCount;
	private Texture texture;
	private Vec3 pos, rot;
	private float scale;
	
	public Model(int vao, int vertexCount, Texture texture) {
		this.vao = vao;
		this.vertexCount = vertexCount;
		this.texture = texture;
		pos = new Vec3();
		rot = new Vec3();
		scale = 1.0f;
		
		if(this.texture != null) {
			this.texture.bindTexture();
			this.texture.loadTexture();
		}
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

	public Vec3 getPos() {
		return pos;
	}

	public void setPos(Vec3 pos) {
		this.pos = pos;
	}

	public Vec3 getRot() {
		return rot;
	}

	public void setRot(Vec3 rot) {
		this.rot = rot;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

}

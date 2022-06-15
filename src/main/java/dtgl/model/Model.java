package dtgl.model;


import dtgl.math.Vec3;

public class Model {
	
	private int vao;
	private int vertexCount;
	private Texture[] textures;
	private Vec3 pos, rot;
	private float scale;
	
	public Model(int vao, int vertexCount, Texture[] textures) {
		this.vao = vao;
		this.vertexCount = vertexCount;
		this.textures = textures;
		pos = new Vec3();
		rot = new Vec3();
		scale = 1.0f;

		if(textures != null) {
			for (Texture texture : this.textures)
				if (texture != null) {
					texture.bindTexture();
					texture.loadTexture();
				}
		}
	}

	public int getVao() {
		return vao;
	}

	public int getVertexCount() {
		return vertexCount;
	}
	
	public Texture[] getTextures() {
		return textures;
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

package dtgl.model;


import dtgl.math.Vec3;
import dtgl.shader.Shader;
import dtgl.shader.Uniform;

import java.util.List;
import java.util.Optional;

public abstract class Model {
	
	protected int vao;
	protected int[] attributesIndices;
	protected int vertexCount;
	protected float angle;
	protected Vec3 pos, rot;
	protected float scale;
	protected Shader shader;
	protected List<Uniform> uniforms;

	public int getVao() {
		return vao;
	}

	public int[] getAttributesIndices() {
		return attributesIndices;
	}

	public int getVertexCount() {
		return vertexCount;
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

	public Optional<Texture[]> getTextures() {
		return Optional.empty();
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getAngle() {
		return angle;
	}

	public Shader getShader() {
		return shader;
	}

	public void setShader(Shader shader) {
		this.shader = shader;
	}

    public List<Uniform> getUniforms() {
        return uniforms;
    }

    public void setUniforms(List<Uniform> uniforms) {
        this.uniforms = uniforms;
    }
}

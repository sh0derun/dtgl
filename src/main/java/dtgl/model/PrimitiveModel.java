package dtgl.model;

import dtgl.math.Vec3;
import dtgl.shader.Shader;

public class PrimitiveModel extends Model{

    public PrimitiveModel(int vao, int vertexCount, int[] ids, Shader shader) {
        this.vao = vao;
        this.vertexCount = vertexCount;
        pos = new Vec3();
        rot = new Vec3();
        scale = 1.0f;
        this.attributesIndices = ids;
        this.shader = shader;
    }

}

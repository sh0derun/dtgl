package dtgl.model;

import dtgl.math.Vec3;

public class PrimitiveModel extends Model{

    public PrimitiveModel(int vao, int vertexCount) {
        this.vao = vao;
        this.vertexCount = vertexCount;
        pos = new Vec3();
        rot = new Vec3();
        scale = 1.0f;
    }

}

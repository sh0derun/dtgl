package dtgl.utils.obj;

import java.util.Arrays;

public class OBJModel{
    float[] positions;
    float[] textureCoords;
    float[] normals;
    int[] indices;


    public OBJModel(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
        this.positions = positions;
        this.textureCoords = textureCoords;
        this.normals = normals;
        this.indices = indices;
    }

    public float[] getPositions() {
        return positions;
    }

    public float[] getTextureCoords() {
        return textureCoords;
    }

    public float[] getNormals() {
        return normals;
    }

    public int[] getIndices() {
        return indices;
    }

    @Override
    public String toString() {
        return "OBJModel{" +
                "positions=" + Arrays.toString(positions) +
                ", textureCoords=" + Arrays.toString(textureCoords) +
                ", normals=" + Arrays.toString(normals) +
                ", indices=" + Arrays.toString(indices) +
                '}';
    }
}
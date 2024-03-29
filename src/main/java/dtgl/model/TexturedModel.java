package dtgl.model;

import dtgl.math.Vec3;
import dtgl.shader.Shader;

import java.util.Optional;

public class TexturedModel extends Model{

    private Texture[] textures;

    public TexturedModel(int vao, int vertexCount, Texture[] textures, int[] ids, Shader shader){
        this.vao = vao;
        this.vertexCount = vertexCount;
        pos = new Vec3();
        rot = new Vec3();
        scale = 1.0f;
        this.textures = textures;
        if(textures != null) {
            for (Texture texture : this.textures)
                if (texture != null) {
                    texture.bindTexture();
                    texture.loadTexture();
                }
        }
        this.attributesIndices = ids;
        this.shader = shader;
    }

    public Optional<Texture[]> getTextures() {
        return textures == null ? Optional.empty() : Optional.of(textures);
    }

}

package dtgl.shader;

import dtgl.exception.ApplicationRuntimeException;
import dtgl.math.Mat4;
import dtgl.math.Vec2;
import dtgl.math.Vec4;
import dtgl.model.Texture;

import java.util.EnumMap;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class DefaultUniformsHandler implements UniformsHandler{

    @Override
    public void updateUniformsValues(Shader shader, EnumMap<UniformType, List<Uniform>> uniformsMap) {
        uniformsMap.forEach((uniformType, uniforms) -> {
            switch (uniformType) {
                case FLOAT : uniforms.forEach(uniform -> shader.setUniform(uniform.getName(), (Float) uniform.getValue()));break;
                case VEC2 : uniforms.forEach(uniform -> shader.setUniform(uniform.getName(), (Vec2) uniform.getValue()));break;
                case VEC4 : uniforms.forEach(uniform -> shader.setUniform(uniform.getName(), (Vec4) uniform.getValue()));break;
                case MAT4 : uniforms.forEach(uniform -> shader.setUniform(uniform.getName(), (Mat4) uniform.getValue()));break;
                case SAMPLER_2D :
                    for(int i = 0; i < uniforms.size(); i++) {
                        Uniform textureUniform = uniforms.get(i);
                        shader.setUniform(textureUniform.getName(), i);
                        glActiveTexture(GL_TEXTURE0+i);
                        glBindTexture(GL_TEXTURE_2D, ((Texture) textureUniform.getValue()).getId());
                    }
                    break;
                default:
                    throw new ApplicationRuntimeException("uniform type not handled !");
            }
        });
    }

}

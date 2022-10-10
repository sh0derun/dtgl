package dtgl.shader;

import java.util.EnumMap;
import java.util.List;

public interface UniformsHandler {

    void updateUniformsValues(Shader shader, EnumMap<UniformType, List<Uniform>> uniformsMap);
    void updateUniformsValues(Shader shader, List<Uniform> uniforms);

}

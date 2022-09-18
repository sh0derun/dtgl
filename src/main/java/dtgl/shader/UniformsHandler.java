package dtgl.shader;

import java.util.EnumMap;
import java.util.List;

public interface UniformsHandler {

    default void updateUniformsValues(Shader shader, EnumMap<UniformType, List<Uniform>> uniformsMap) {}

}

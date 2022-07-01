package dtgl.shader;

import java.util.EnumMap;
import java.util.List;

public interface UniformsModifier {

    default void updateUniformsValues(Shader shader, EnumMap<UniformType, List<Uniform>> uniformsMap) {}

}

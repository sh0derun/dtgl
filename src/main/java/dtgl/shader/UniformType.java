package dtgl.shader;

public enum UniformType {
    NONE("none"),
    INT("int"), FLOAT("float"),
    VEC2("vec2"), VEC3("vec3"), VEC4("vec4"),
    MAT2("mat2"), MAT3("mat3"), MAT4("mat4"),
    SAMPLER_2D("sampler2D"),
    POINT_LIGHT("point_light"),
    PHONG_MATERIAL("phong_material");

    private String value;

    UniformType(String value){
        this.value = value;
    }
}

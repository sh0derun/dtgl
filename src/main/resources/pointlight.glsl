#include<material.glsl>

struct PointLight{
    vec3 position;
    vec3 color;
};

vec3 getPhongMaterial(Material material, PointLight pointLight, vec3 position, vec3 normal){
    vec3 ambientComponent =  vec3((0.212671f*material.ambient.r + 0.715160f*material.ambient.g + 0.072169f*material.ambient.b)/(0.212671f*material.diffuse.r + 0.715160f*material.diffuse.g + 0.072169f*material.diffuse.b));
    ambientComponent *= material.ambient;
    vec3 lightDir = normalize(pointLight.position - position);
    float normalLightAngle = max(0, dot(normal, lightDir));
    vec3 diffuseComponent = material.diffuse * pointLight.color * normalLightAngle;

    vec3 from_light_source = -lightDir;
    vec3 reflected_light = normalize(reflect(from_light_source, normal));
    float specularFactor = pow(max(0, dot(normalize(-position), reflected_light)), material.shininess);
    vec3 specularComponent = material.specular * specularFactor * pointLight.color;

    return vec3(ambientComponent + diffuseComponent + specularComponent);
}
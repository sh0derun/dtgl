#version 330 core

#include<material.glsl>
#include<pointlight.glsl>

in vec3 outNormal;
in vec2 outTextureCoord;
in vec3 outPosition;

out vec4 FragColor;

uniform Material model_material;
uniform PointLight point_light;

vec3 getPhongMaterial(PointLight pointLight){
    vec3 ambientComponent =  vec3((0.212671f*model_material.ambient.r + 0.715160f*model_material.ambient.g + 0.072169f*model_material.ambient.b)/(0.212671f*model_material.diffuse.r + 0.715160f*model_material.diffuse.g + 0.072169f*model_material.diffuse.b));//model_material.ambient;
    ambientComponent *= model_material.ambient;
    vec3 lightDir = normalize(pointLight.position - outPosition);
    float normalLightAngle = max(0, dot(outNormal, lightDir));
    vec3 diffuseComponent = model_material.diffuse * pointLight.color * normalLightAngle;

    vec3 from_light_source = -lightDir;
    vec3 reflected_light = normalize(reflect(from_light_source, outNormal));
    float specularFactor = pow(max(0, dot(normalize(-outPosition), reflected_light)), model_material.shininess);
    vec3 specularComponent = model_material.specular * specularFactor * pointLight.color;

    return vec3(ambientComponent + diffuseComponent + specularComponent);
}

void main() {
    vec3 phongRes = getPhongMaterial(point_light);
    FragColor = vec4(phongRes, 1);
}

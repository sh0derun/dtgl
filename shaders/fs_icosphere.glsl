#version 330 core

#include<material.glsl>
#include<pointlight.glsl>

in vec3 outNormal;
in vec2 outTextureCoord;
in vec3 outPosition;

out vec4 FragColor;

uniform vec3 model_color;

uniform PointLight point_light;

Material getPhongMaterial(PointLight pointLight, float shininess){
    vec3 ambientComponent = vec3(0.1);

    vec3 lightDir = normalize(pointLight.position - outPosition);
    float normalLightAngle = max(0, dot(outNormal, lightDir));
    vec3 diffuseComponent = pointLight.color * normalLightAngle;

    vec3 from_light_source = -lightDir;
    vec3 reflected_light = normalize(reflect(from_light_source, outNormal));
    float specularFactor = pow(max(0, dot(normalize(-outPosition), reflected_light)), shininess);
    vec3 specularComponent = vec3(1,1,1) * specularFactor * pointLight.color;

    return Material(ambientComponent, diffuseComponent, specularComponent);
}

void main() {
    Material phongRes = getPhongMaterial(point_light, 64);
    FragColor = vec4(model_color * (phongRes.ambient + phongRes.diffuse + phongRes.specular), 1);
}

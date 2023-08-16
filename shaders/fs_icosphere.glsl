#version 330 core

#include<material.glsl>
#include<pointlight.glsl>

in vec3 outNormal;
in vec2 outTextureCoord;
in vec3 outPosition;

out vec4 FragColor;

uniform Material model_material;
uniform PointLight point_light;


void main() {
    vec3 phongRes = getPhongMaterial(model_material, point_light, outPosition, outNormal);
    FragColor = vec4(phongRes, 1);
}

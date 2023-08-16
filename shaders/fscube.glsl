#version 330 core

#include<material.glsl>
#include<pointlight.glsl>


out vec4 FragColor;

in vec3 position;
in vec3 normal;

uniform Material model_material;
uniform PointLight point_light;


void main() {
    vec3 phongRes = getPhongMaterial(model_material, point_light, position, normal);
    FragColor = vec4(phongRes, 1);
}
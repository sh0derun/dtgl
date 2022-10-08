#version 330 core

in vec3 outNormal;
in vec2 outTextureCoord;
in vec3 outPosition;

out vec4 FragColor;

void main() {
    FragColor = vec4(outNormal, 1.0);
}

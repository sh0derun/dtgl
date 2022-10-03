#version 330 core
out vec4 FragColor;

in vec4 outColor;
in vec2 outTex;

uniform vec4 error_color;

void main() {
    FragColor = error_color;
}
#version 330 core
out vec4 FragColor;
  
in vec4 outColor;
in vec2 outTex;

uniform float time;
uniform vec2 res;
uniform vec4 col;

uniform sampler2D texture1;

void main() {
    FragColor = texture(texture1, outTex)*outColor;
}
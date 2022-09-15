#version 330 core
layout (location = 0) in vec3 pos;
layout (location = 1) in vec4 color;
layout (location = 2) in vec2 tex;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;
  
out vec4 outColor;
out vec2 outTex;

void main()
{
    gl_Position = /*projection * view **/ projection * view * model * vec4(pos, 1.0);
	outColor = color;
	outTex = tex;
}
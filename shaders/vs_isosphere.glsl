#version 330 core

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 textureCoord;
layout (location = 2) in vec3 normal;

out vec3 outNormal;
out vec2 outTextureCoord;
out vec3 outPosition;

void main()
{
	mat4 modelView = view * model;
	vec4 mvPos = modelView * vec4(position, 1.0);
	gl_Position = projection * mvPos;
	outTextureCoord = textureCoord;
	outNormal = normalize(modelView * vec4(normal, 0.0)).xyz;
	outPosition = mvPos.xyz;
}

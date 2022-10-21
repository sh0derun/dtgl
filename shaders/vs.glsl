#version 330 core

layout (location = 0) in vec3 pos;
layout (location = 1) in vec3 nor;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

out vec3 position;
out vec3 normal;

void main()
{
	mat4 to_view = view * model;
	vec4 vPos = to_view * vec4(pos, 1.0);
    gl_Position = projection * vPos;
	normal = normalize(to_view * vec4(nor, 0.0)).xyz;
	position = vPos.xyz;

	/*gl_Position = projection * view * model * vec4(pos, 1.0);
	position = vec3(model * vec4(pos, 1.0));
	normal = nor;
	outTex = tex;*/
}
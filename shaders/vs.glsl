#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec4 aColor;
layout (location = 2) in vec2 aTex;

//uniform mat4 pr_mat;
//uniform mat4 vw_mat = mat4(1.0);
//uniform mat4 ml_mat;
  
out vec4 outColor;
out vec2 outTex;

void main()
{
    gl_Position = /*pr_mat * ml_mat */ vec4(aPos, 1.0);
	outColor = aColor;
	outTex = aTex;
}
#version 410 core
out vec4 FragColor;
  
in vec4 vertexColor;

uniform float time;
uniform vec2 res;

void main() {
	vec2 uv = gl_FragCoord.xy / res * 2.0 - 1.0;
	uv.x *= res.x / res.y;
    FragColor = vec4(uv*100.0,1.0,1.0);
}
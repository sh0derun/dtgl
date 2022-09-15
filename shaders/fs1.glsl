#version 330 core
out vec4 FragColor;
  
in vec4 outColor;
in vec2 outTex;

//uniform float time;
uniform vec2 resolution;

uniform sampler2D texture2;

void main() {
	vec2 uv = (gl_FragCoord.xy / resolution)*2.0-1.0;
    FragColor = texture(texture2, outTex);//outColor;texture(texture0, outTex);//*outColor*mod(sin((uv.x*uv.y)*145235.213997)*9214.2158, 1.0);//*(sin(time*uv.x)*0.5+0.5)*mod(uv.y*uv.x, 2.0);
}
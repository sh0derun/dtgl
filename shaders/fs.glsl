#version 330 core
out vec4 FragColor;
  
in vec4 outColor;
in vec2 outTex;

uniform float time;
//uniform vec2 res;

uniform sampler2D texture0;
uniform sampler2D texture1;

void main() {
	vec2 uv = (gl_FragCoord.xy / vec2(500,500))*2.0-1.0;
    FragColor = mix(texture(texture0, outTex),texture(texture1, outTex), sin(time)*0.5+0.5);//*outColor*mod(sin((uv.x*uv.y)*145235.213997)*9214.2158, 1.0);//*(sin(time*uv.x)*0.5+0.5)*mod(uv.y*uv.x, 2.0);
}
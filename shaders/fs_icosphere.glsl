#version 330 core

in vec3 outNormal;
in vec2 outTextureCoord;
in vec3 outPosition;

out vec4 FragColor;

uniform vec3 model_color;
uniform vec3 light_color;
uniform vec3 light_pos;

struct Material{
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};

Material getPhongMaterial(vec3 lightColor, vec3 lightPos, float shininess){
    vec3 ambientComponent = vec3(0.1);

    vec3 lightDir = normalize(lightPos - outPosition);
    float normalLightAngle = max(0, dot(outNormal, lightDir));
    vec3 diffuseComponent = lightColor * normalLightAngle;

    vec3 from_light_source = -lightDir;
    vec3 reflected_light = normalize(reflect(from_light_source, outNormal));
    float specularFactor = pow(max(0, dot(normalize(-outPosition), reflected_light)), shininess);
    vec3 specularComponent = vec3(1,1,1) * specularFactor * lightColor;

    return Material(ambientComponent, diffuseComponent, specularComponent);
}

void main() {
    Material phongRes = getPhongMaterial(light_color, light_pos, 15);
    FragColor = vec4(model_color * (phongRes.ambient + phongRes.diffuse + phongRes.specular), 1);
}

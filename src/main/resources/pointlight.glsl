struct PointLight{
    vec3 position;
    vec3 color;
};

vec3 getPhongMaterial(vec3 ambient, vec3 diffuse, vec3 specular, float shininess, PointLight pointLight, vec3 position, vec3 normal){
    vec3 ambientComponent =  vec3((0.212671f*ambient.r + 0.715160f*ambient.g + 0.072169f*ambient.b)/(0.212671f*diffuse.r + 0.715160f*diffuse.g + 0.072169f*diffuse.b));
    ambientComponent *= ambient;
    vec3 lightDir = normalize(pointLight.position - position);
    float normalLightAngle = max(0, dot(normal, lightDir));
    vec3 diffuseComponent = diffuse * pointLight.color * normalLightAngle;

    vec3 from_light_source = -lightDir;
    vec3 reflected_light = normalize(reflect(from_light_source, normal));
    float specularFactor = pow(max(0, dot(normalize(-position), reflected_light)), shininess);
    vec3 specularComponent = specular * specularFactor * pointLight.color;

    return vec3(ambientComponent + diffuseComponent + specularComponent);
}
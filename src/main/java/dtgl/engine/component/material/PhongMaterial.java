package dtgl.engine.component.material;

import dtgl.math.Vec3;

public class PhongMaterial {

    private String name;
    private Vec3 ambient;
    private Vec3 diffuse;
    private Vec3 specular;
    private float shininess;

    public PhongMaterial(String name, Vec3 ambient, Vec3 diffuse, Vec3 specular, float shininess) {
        this.name = name;
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vec3 getAmbient() {
        return ambient;
    }

    public void setAmbient(Vec3 ambient) {
        this.ambient = ambient;
    }

    public Vec3 getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(Vec3 diffuse) {
        this.diffuse = diffuse;
    }

    public Vec3 getSpecular() {
        return specular;
    }

    public void setSpecular(Vec3 specular) {
        this.specular = specular;
    }

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    @Override
    public String toString() {
        return "Material{" +
                "ambient=" + ambient +
                ", diffuse=" + diffuse +
                ", specular=" + specular +
                ", shininess=" + shininess +
                '}';
    }
}

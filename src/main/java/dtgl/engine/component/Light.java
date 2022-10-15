package dtgl.engine.component;

import dtgl.math.Vec3;

public abstract class Light {
    protected Vec3 color;

    public Light(Vec3 color) {
        this.color = color;
    }

    public Vec3 getColor() {
        return color;
    }

    public void setColor(Vec3 color) {
        this.color = color;
    }
}

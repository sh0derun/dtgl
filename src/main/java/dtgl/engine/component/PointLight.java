package dtgl.engine.component;

import dtgl.math.Vec3;

public class PointLight extends Light{
    private Vec3 position;

    public PointLight(Vec3 color, Vec3 position) {
        super(color);
        this.position = position;
    }

    public Vec3 getPosition() {
        return position;
    }

    public void setPosition(Vec3 position) {
        this.position = position;
    }
}

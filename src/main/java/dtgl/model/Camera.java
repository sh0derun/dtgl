package dtgl.model;

import dtgl.math.Vec3;

public class Camera {
    private Vec3 pos, target, direction, up, right;
    private float pitch, yaw, roll;

    public Camera(){
        this.pos = new Vec3(0,0,-15);
        this.target = Vec3.ZERO;
        this.buildCameraVectors();
    }

    public Camera(Vec3 pos){
        this.pos = pos;
        this.target = Vec3.ZERO;
        this.buildCameraVectors();
    }

    public Camera(Vec3 pos, Vec3 target, float pitch, float yaw, float roll){
        this.pos = pos;
        this.target = target;
        this.buildCameraVectors();
    }

    private void buildCameraVectors(){
        this.direction = this.target.sub(this.pos).normalize();
        this.right = this.direction.cross(Vec3.UP).normalize();
        this.up = this.right.cross(this.direction);
    }

    public Vec3 getPos() {
        return pos;
    }

    public void setPos(Vec3 pos) {
        this.pos = pos;
        buildCameraVectors();
    }

    public Vec3 getTarget() {
        return target;
    }

    public void setTarget(Vec3 target) {
        this.target = target;
    }

    public Vec3 getDirection() {
        return direction;
    }

    public void setDirection(Vec3 direction) {
        this.direction = direction;
    }

    public Vec3 getUp() {
        return up;
    }

    public void setUp(Vec3 up) {
        this.up = up;
    }

    public Vec3 getRight() {
        return right;
    }

    public void setRight(Vec3 right) {
        this.right = right;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getRoll() {
        return roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }
}

package dtgl.math;

import java.util.Arrays;

public class Vec3 extends Vec{

	float[] coords;

	public static final Vec3 ZERO = new Vec3(0,0,0);
	public static final Vec3 ONE = new Vec3(1,1,1);
	public static final Vec3 UP = new Vec3(0,1,0);
	public static final Vec3 RIGHT = new Vec3(1,0,0);
	public static final Vec3 TOWARD = new Vec3(0,0,1);
	
	public Vec3() {
		this.coords = new float[3];
	}
	
	public Vec3(float x, float y, float z) {
		this.coords = new float[]{x,y,z};
	}

	public Vec3 add(Vec3 vec) {
		Vec3 res = Vec3.ZERO;
		for(int i = 0; i < coords.length; res.coords[i] = this.coords[i] + vec.coords[i],i++);
		return res;
	}

	public Vec3 add(Vec3 a, Vec3 b) {
		Vec3 res = Vec3.ZERO;
		for(int i = 0; i < coords.length; res.coords[i] = a.coords[i] + b.coords[i],i++);
		return res;
	}

	public Vec3 sub(Vec3 vec) {
		Vec3 res = Vec3.ZERO;
		for(int i = 0; i < coords.length; res.coords[i] = this.coords[i] - vec.coords[i],i++);
		return res;
	}

	public Vec3 sub(Vec3 a, Vec3 b) {
		Vec3 res = Vec3.ZERO;
		for(int i = 0; i < coords.length; res.coords[i] = a.coords[i] - b.coords[i],i++);
		return res;
	}

	public Vec3 mult(Vec3 vec) {
		Vec3 res = Vec3.ZERO;
		for(int i = 0; i < coords.length; res.coords[i] = this.coords[i] * vec.coords[i],i++);
		return res;
	}

	public Vec3 mult(Vec3 a, Vec3 b) {
		Vec3 res = Vec3.ZERO;
		for(int i = 0; i < coords.length; res.coords[i] = a.coords[i] * b.coords[i],i++);
		return res;
	}

	public Vec3 mult(float s) {
		Vec3 res = Vec3.ZERO;
		for(int i = 0; i < coords.length; res.coords[i] = this.coords[i] * s,i++);
		return res;
	}

	public Vec3 div(Vec3 vec) {
		Vec3 res = Vec3.ZERO;
		for(int i = 0; i < coords.length; res.coords[i] = coords[i] / vec.coords[i],i++);
		return this;
	}

	public Vec3 div(float s) {
		Vec3 res = Vec3.ZERO;
		for(int i = 0; i < coords.length; res.coords[i] = coords[i] / s,i++);
		return res;
	}

	public float length() {
		float sum = 0.0f;
		for(float n : coords) {
			sum += n*n;
		}
		return (float) Math.sqrt(sum);
	}

	public Vec3 normalize() {
		float length = this.length();
		for(int i = 0; i < coords.length; coords[i] /= length,i++);
		return this;
	}

	public double dot(Vec3 vec) {
		float sum = 0.0f;
		for(int i = 0; i < coords.length; sum += coords[i] * vec.coords[i],i++);
		return sum;
	}

	public float[] getCoords() {
		return coords;
	}

	public String toString() {
		return "Vec3 [coords=" + Arrays.toString(coords) + "]";
	}
	
	public Vec3 cross(Vec3 vec) {
		return new Vec3(coords[1]*vec.coords[2] - coords[2]*vec.coords[1],
						coords[0]*vec.coords[2] - coords[2]*vec.coords[0],
						coords[0]*vec.coords[1] - coords[1]*vec.coords[0]);
	}

}

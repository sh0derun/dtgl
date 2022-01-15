package dtgl.math;

import java.util.Arrays;

public class Vec3 extends Vec{
	
	public Vec3() {
		super(3);
	}
	
	public Vec3(float x, float y, float z) {
		super(3, x, y, z);
	}

	public String toString() {
		return "Vec3 [coords=" + Arrays.toString(coords) + "]";
	}
	
	public Vec cross(Vec vec) {
		checkVecSizes(vec);
		return new Vec3(coords[1]*vec.coords[2] - coords[2]*vec.coords[1], 
						coords[0]*vec.coords[2] - coords[2]*vec.coords[0],
						coords[0]*vec.coords[1] - coords[1]*vec.coords[0]);
	}
	
	public static Vec3 UP() {
		return new Vec3(0,1,0);
	}
	
	public static Vec3 RIGHT() {
		return new Vec3(1,0,0);
	}
	
	public static Vec3 TOWARD() {
		return new Vec3(0,0,1);
	}

}

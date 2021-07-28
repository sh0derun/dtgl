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

}

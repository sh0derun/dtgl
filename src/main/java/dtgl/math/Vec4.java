package dtgl.math;

import java.util.Arrays;

public class Vec4 extends Vec{
	
	public Vec4() {
		super(4);
	}
	
	public Vec4(float x, float y, float z, float w) {
		super(4, x, y, z, w);
	}

	public String toString() {
		return "Vec4 [coords=" + Arrays.toString(coords) + "]";
	}
	
	public Vec cross(Vec vec) {
		throw new UnsupportedOperationException("");
	}

}

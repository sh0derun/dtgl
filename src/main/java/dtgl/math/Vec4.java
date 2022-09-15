package dtgl.math;

import java.util.Arrays;

public class Vec4 extends Vec{

	float[] coords;

	public Vec4() {
		this.coords = new float[4];
	}

	public Vec4(float x, float y, float z, float w) {
		this.coords = new float[]{x,y,z,w};
	}

	public float[] getCoords() {
		return coords;
	}

	public String toString() {
		return "Vec4 [coords=" + Arrays.toString(coords) + "]";
	}

}

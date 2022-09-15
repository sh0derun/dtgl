package dtgl.math;

import java.util.Arrays;

public class Vec2 extends Vec{

	float[] coords;

	public Vec2() {
		this.coords = new float[2];
	}
	public Vec2(float x, float y) {
		this.coords = new float[]{x,y};
	}

	public float[] getCoords() {
		return coords;
	}

	public String toString() {
		return "Vec2 [coords= ]";
	}

}

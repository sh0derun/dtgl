package dtgl.math;

import java.util.Arrays;

public class Vec2 extends Vec{
	
	public Vec2() {
		super(2);
	}
	
	public Vec2(float x, float y) {
		super(2, x, y);
	}

	public String toString() {
		return "Vec2 [coords=" + Arrays.toString(coords) + "]";
	}

	public Vec cross(Vec vec) {
		throw new UnsupportedOperationException("");
	}

}

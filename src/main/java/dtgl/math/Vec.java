package dtgl.math;

public abstract class Vec {

	float[] coords;
	
	public Vec(int n) {
		coords = new float[n];
	}
	
	public Vec(int n, float...cds) {
		coords = new float[n];
		for(int i = 0; i < coords.length; i++) {
			coords[i] = cds[i];
		}
	}
	
	protected void checkVecSizes(Vec vec) {
		if(coords.length != vec.coords.length)
			throw new RuntimeException("can't add non equal sizes vectors");
	}
	
	public Vec add(Vec vec) {
		checkVecSizes(vec);
		for(int i = 0; i < coords.length; coords[i] += vec.coords[i],i++);
		return this;
	}
	
	public Vec sub(Vec vec) {
		checkVecSizes(vec);
		for(int i = 0; i < coords.length; coords[i] -= vec.coords[i],i++);
		return this;
	}
	
	public Vec mult(Vec vec) {
		checkVecSizes(vec);
		for(int i = 0; i < coords.length; coords[i] *= vec.coords[i],i++);
		return this;
	}
	
	public Vec mult(float s) {
		for(int i = 0; i < coords.length; coords[i] *= s,i++);
		return this;
	}
	
	public Vec div(Vec vec) {
		checkVecSizes(vec);
		for(int i = 0; i < coords.length; coords[i] /= vec.coords[i],i++);
		return this;
	}
	
	public Vec div(float s) {
		for(int i = 0; i < coords.length; coords[i] /= s,i++);
		return this;
	}
	
	public float length() {
		float sum = 0.0f;
		for(float n : coords) {
			sum += n*n;
		}
		return (float) Math.sqrt(sum);
	}
	
	public Vec normalize() {
		float length = this.length();
		for(int i = 0; i < coords.length; coords[i] /= length,i++);
		return this;
	}
	
	public double dot(Vec vec) {
		checkVecSizes(vec);
		float sum = 0.0f;
		for(int i = 0; i < coords.length; sum += coords[i] * vec.coords[i],i++);
		return sum;
	}
	
	abstract protected Vec cross(Vec vec);

	public float[] getCoords() {
		return coords;
	}
	
	
	
}

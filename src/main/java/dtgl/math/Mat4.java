package dtgl.math;

import java.util.Arrays;

public class Mat4 {
	
	float[] values = new float[16];
	
	public static Mat4 identity() {
		Mat4 mat4 = new Mat4();
		mat4.values[0+0*4] = 1.0f;
		mat4.values[1+1*4] = 1.0f;
		mat4.values[2+2*4] = 1.0f;
		mat4.values[3+3*4] = 1.0f;
		return mat4;
	}
	
	public static Mat4 scale(float n) {
		Mat4 mat4 = new Mat4();
		mat4.values[0+0*4] = n;
		mat4.values[1+1*4] = n;
		mat4.values[2+2*4] = n;
		mat4.values[3+3*4] = 1.0f;
		return mat4;
	}
	
	public static Mat4 translate(Vec3 d) {
		Mat4 mat4 = Mat4.identity();
		mat4.values[0+3*4] = d.coords[0];
		mat4.values[1+3*4] = d.coords[1];
		mat4.values[2+3*4] = d.coords[2];
		return mat4;
	}
	
	public static Mat4 rotate(double angle, Vec3 axis) {
		Mat4 mat4 = Mat4.identity();
		double rad = Math.toRadians(angle);
		float c = (float) Math.cos(rad), 
			  s = (float) Math.sin(rad),
			  omcos = 1 - c;
		mat4.values[0 + 0 * 4] = axis.coords[0] * axis.coords[0] * omcos + c;
		mat4.values[0 + 1 * 4] = axis.coords[1] * axis.coords[0] * omcos + axis.coords[2] * s;
		mat4.values[0 + 2 * 4] = axis.coords[0] * axis.coords[2] * omcos - axis.coords[1] * s;
		mat4.values[1 + 0 * 4] = axis.coords[0] * axis.coords[1] * omcos - axis.coords[2] * s;
		mat4.values[1 + 1 * 4] = axis.coords[1] * axis.coords[1] * omcos + c;
		mat4.values[1 + 2 * 4] = axis.coords[1] * axis.coords[2] * omcos + axis.coords[0] * s;
		mat4.values[2 + 0 * 4] = axis.coords[0] * axis.coords[2] * omcos + axis.coords[1] * s;
		mat4.values[2 + 1 * 4] = axis.coords[1] * axis.coords[2] * omcos - axis.coords[0] * s;
		mat4.values[2 + 2 * 4] = axis.coords[2] * axis.coords[2] * omcos + c;
		return mat4;
	}
	
	public static Mat4 rotZ(float angle) {
		double rad = Math.toRadians(angle);
		float c = (float) Math.cos(rad), 
			  s = (float) Math.sin(rad);
		Mat4 mat4 = Mat4.identity();
		mat4.values[0 + 0 * 4] = c;
		mat4.values[0 + 1 * 4] = -s;
		mat4.values[1 + 0 * 4] = s;
		mat4.values[1 + 1 * 4] = c;
		return mat4;
	}
	
	public static Mat4 rotX(float angle) {
		double rad = Math.toRadians(angle);
		float c = (float) Math.cos(rad), 
			  s = (float) Math.sin(rad);
		Mat4 mat4 = Mat4.identity();
		mat4.values[1 + 1 * 4] = c;
		mat4.values[1 + 2 * 4] = -s;
		mat4.values[2 + 1 * 4] = s;
		mat4.values[2 + 2 * 4] = c;
		return mat4;
	}
	
	public static Mat4 rotY(float angle) {
		double rad = Math.toRadians(angle);
		float c = (float) Math.cos(rad), 
			  s = (float) Math.sin(rad);
		Mat4 mat4 = Mat4.identity();
		mat4.values[0 + 0 * 4] = c;
		mat4.values[0 + 2 * 4] = -s;
		mat4.values[2 + 0 * 4] = s;
		mat4.values[2 + 2 * 4] = c;
		return mat4;
	}
	
	public static Mat4 getTransformationMat(Vec3 translation, Vec3 rotation, float scale) {
		Mat4 transformation = Mat4.identity();
		transformation = Mat4.scale(scale).mult(transformation);
		transformation = Mat4.rotZ(rotation.coords[2]).mult(Mat4.rotY(rotation.coords[1]).mult(Mat4.rotX(rotation.coords[0]))).mult(transformation);
		transformation = Mat4.translate(translation).mult(transformation);
		return transformation;
	}
	
	public static Mat4 perspective(float fov, float aspect, float far, float near) {
		Mat4 mat4 = new Mat4();
		float phi = (float) Math.tan(fov/2);
		mat4.values[0+0*4] = 1/(aspect*phi);
		mat4.values[1+1*4] = 1/phi;
		mat4.values[2+2*4] = (near+far)/(near-far);
		mat4.values[2+3*4] = (2*near*far)/(near-far);
		mat4.values[3+2*4] = -1;
		return mat4;
	}
	
	public static Mat4 orthographic(float right, float left, float top, float bottom, float far, float near) {
		Mat4 mat4 = Mat4.identity();
		float RmL = right - left,
			  TmB = top - bottom,
			  FmN = far - near;
		mat4.values[0+0*4] = 2/RmL;
		mat4.values[1+1*4] = 2/TmB;
		mat4.values[2+2*4] = 2/FmN;
		mat4.values[0+3*4] = -((right + left)/RmL);
		mat4.values[1+3*4] = -((top + bottom)/TmB);
		mat4.values[1+3*4] = -((far + near)/FmN);
		return mat4;
	}
	
	public Mat4 mult(Mat4 mat4) {
		Mat4 res = new Mat4();
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				float sum = 0.0f;
				for(int k = 0; k < 4; k++) {
					sum += values[i+k*4]*mat4.values[k+j*4];
				}
				res.values[i+j*4] = sum;
			}
		}
		return res;
	}
	
	public Vec4 mult(Vec4 vec4) {
		Vec4 res = new Vec4();
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				float sum = 0.0f;
				for(int k = 0; k < 4; k++) {
					sum += values[i+k*4]*vec4.coords[j];
				}
				res.coords[j] = sum;
			}
		}
		return res;	
	}

	public String toString() {
		return "Mat4 [values=" + Arrays.toString(values) + "]";
	}

	public float[] getValues() {
		return values;
	}

}

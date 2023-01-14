package dtgl.math;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.Arrays;

/*REMEMBER THAT MATRICES NEEDS TO BE WRITTEN AS TRANSPOSED
* BUT IF NOT YOU NEED TO SET SECOND ARGUMENT OF glUniformMatrix4fv
* TO true TO TELL OPENGL THAT THE FEEDED MATRIX ISN'T TRANSPOSED YET
* OTHERWISE MATRIX WILL NOT DO THE JOB BECAUSE OPENGL STORES MATRICES
* ONLY IN COLUMN MAJOR FORMAT.
* IN Mat4 ALL STORED AS COLUMN MAJOR FORMAT*/

public class Mat4 {
	
	float[] values = new float[16];

	public Mat4(){

	}

	public Mat4(float[] values){
		if(values != null) {
			System.arraycopy(values, 0, this.values, 0, this.values.length);
		}
	}
	
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
		mat4.values[1 + 0 * 4] = axis.coords[1] * axis.coords[0] * omcos + axis.coords[2] * s;
		mat4.values[2 + 0 * 4] = axis.coords[0] * axis.coords[2] * omcos - axis.coords[1] * s;

		mat4.values[0 + 1 * 4] = axis.coords[0] * axis.coords[1] * omcos - axis.coords[2] * s;
		mat4.values[1 + 1 * 4] = axis.coords[1] * axis.coords[1] * omcos + c;
		mat4.values[2 + 1 * 4] = axis.coords[1] * axis.coords[2] * omcos + axis.coords[0] * s;

		mat4.values[0 + 2 * 4] = axis.coords[0] * axis.coords[2] * omcos + axis.coords[1] * s;
		mat4.values[1 + 2 * 4] = axis.coords[1] * axis.coords[2] * omcos - axis.coords[0] * s;
		mat4.values[2 + 2 * 4] = axis.coords[2] * axis.coords[2] * omcos + c;

		return mat4;
	}
	
	public static Mat4 rotZ(float angle) {
		double rad = Math.toRadians(angle);
		float c = (float) Math.cos(rad), 
			  s = (float) Math.sin(rad);
		Mat4 mat4 = Mat4.identity();
		mat4.values[0 + 0 * 4] = c;
		mat4.values[1 + 0 * 4] = -s;
		mat4.values[0 + 1 * 4] = s;
		mat4.values[1 + 1 * 4] = c;
		return mat4;
	}
	
	public static Mat4 rotX(float angle) {
		double rad = Math.toRadians(angle);
		float c = (float) Math.cos(rad), 
			  s = (float) Math.sin(rad);
		Mat4 mat4 = Mat4.identity();
		mat4.values[1 + 1 * 4] = c;
		mat4.values[2 + 1 * 4] = -s;
		mat4.values[1 + 2 * 4] = s;
		mat4.values[2 + 2 * 4] = c;
		return mat4;
	}
	
	public static Mat4 rotY(float angle) {
		double rad = Math.toRadians(angle);
		float c = (float) Math.cos(rad), 
			  s = (float) Math.sin(rad);
		Mat4 mat4 = Mat4.identity();
		mat4.values[0 + 0 * 4] = c;
		mat4.values[2 + 0 * 4] = s;
		mat4.values[0 + 2 * 4] = -s;
		mat4.values[2 + 2 * 4] = c;
		return mat4;
	}
	
	public static Mat4 getTransformationMat(Vec3 translation, Vec3 rotation, float rotationAngle, float scale) {
		Mat4 transformation = Mat4.identity();
		float x = rotation.getCoords()[0];
		float y = rotation.getCoords()[1];
		float z = rotation.getCoords()[2];
		transformation = Mat4.scale(scale).mult(transformation);
		transformation = Mat4.rotate(x, new Vec3(1,0,0)).mult(transformation);
		transformation = Mat4.rotate(y, new Vec3(0,1,0)).mult(transformation);
		transformation = Mat4.rotate(z, new Vec3(0,0,1)).mult(transformation);
		transformation = Mat4.translate(translation).mult(transformation);
		return transformation;
	}
	
	public static Mat4 perspective(float fov, float aspect, float near, float far) {
		double halfpi = Math.PI * 0.5;
		float f = (float) Math.tan(halfpi - 0.5 * Math.toRadians(fov));
		float rangeInv = 1.0f / (near - far);

		return new Mat4(new float[]{
				f / aspect, 0, 0					    ,  0,
				0		  , f, 0					    ,  0,
				0		  , 0, (near + far) * rangeInv  , -1,
				0		  , 0, near * far * rangeInv * 2,  0});
	}
	
	public static Mat4 orthographic(float right, float left, float top, float bottom, float far, float near) {
		Mat4 mat4 = Mat4.identity();
		float RmL = right - left,
			  TmB = top - bottom,
			  FmN = far - near;
		mat4.values[0+0*4] = 2/RmL;
		mat4.values[1+1*4] = 2/TmB;
		mat4.values[2+2*4] = 2/FmN;
		mat4.values[3+0*4] = -((right + left)/RmL);
		mat4.values[3+1*4] = -((top + bottom)/TmB);
		mat4.values[3+2*4] = -((far + near)/FmN);
		return mat4;
	}
	
	public Mat4 mult(Mat4 mat4) {
		Mat4 res = new Mat4();
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				float sum = 0.0f;
				for(int k = 0; k < 4; k++) {
					sum += values[j+k*4]*mat4.values[k+i*4];
				}
				res.values[j+i*4] = sum;
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

	public static Mat4 lookAt(Vec3 camera, Vec3 target, Vec3 up){
		Mat4 result = Mat4.identity();

		Vec3 z = camera.sub(target).normalize();
		Vec3 x = up.cross(z).normalize();
		Vec3 y = z.cross(x).normalize();

		Vec3 invCamera = camera.mult(-1);
		float dotx = x.dot(invCamera);
		float doty = y.dot(invCamera);
		float dotz = z.dot(invCamera);

		result.values[0] = x.getCoords()[0];
		result.values[1] = y.getCoords()[0];
		result.values[2] = z.getCoords()[0];

		result.values[4] = x.getCoords()[1];
		result.values[5] = y.getCoords()[1];
		result.values[6] = z.getCoords()[1];

		result.values[8] = x.getCoords()[2];
		result.values[9] = y.getCoords()[2];
		result.values[10] = z.getCoords()[2];

		result.values[12] = dotx;
		result.values[13] = doty;
		result.values[14] = dotz;

		return result;
	}

	public String toString() {
		return "Mat4 [values=" + Arrays.toString(values) + "]";
	}

	public float[] getValues() {
		return values;
	}

}
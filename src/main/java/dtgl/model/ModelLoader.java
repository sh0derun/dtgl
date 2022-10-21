package dtgl.model;

import dtgl.math.Vec3;
import dtgl.shader.Shader;
import dtgl.utils.obj.OBJModel;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL30.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public final class ModelLoader {

	private List<Integer> vaoList = new ArrayList<>(),
						  vboList = new ArrayList<>(),
						  eboList = new ArrayList<>();

	public Model load(OBJModel model, Texture[] textures, Shader shader){
		int vao = glGenVertexArrays();
		glBindVertexArray(vao);
		vaoList.add(vao);

		createAndLoadEBO(model.getIndices());

		createAndLoadVBO(0, 3, model.getPositions());
		createAndLoadVBO(1, 2, model.getTextureCoords());
		createAndLoadVBO(2, 3, model.getNormals());

		glBindVertexArray(0);

		return textures == null ? new PrimitiveModel(vao, model.getIndices().length, new int[]{0,1,2}, shader) : new TexturedModel(vao, model.getIndices().length, textures, new int[]{0,1,2}, shader);
	}

	public Model load(float[] vertices, int[] indices, Texture[] textures, Shader shader) {
		int vao = glGenVertexArrays();
		glBindVertexArray(vao);
		vaoList.add(vao);

		int vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		vboList.add(vbo);
		glBufferData(GL_ARRAY_BUFFER, floatArrayToFloatBuffer(vertices), GL_STATIC_DRAW);

		createAndLoadEBO(indices);

		int posSize = 3;
		int texSize = 2;
		int normalSize = 3;
		int vertexSizeBytes = (posSize+normalSize)*Float.BYTES;

		glVertexAttribPointer(0, posSize, GL_FLOAT, false, vertexSizeBytes, 0);
		glEnableVertexAttribArray(0);

		glVertexAttribPointer(1, normalSize, GL_FLOAT, false, vertexSizeBytes, (posSize) * Float.BYTES);
		glEnableVertexAttribArray(1);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);

		return textures == null ? new PrimitiveModel(vao, indices.length, new int[]{0,1}, shader) : new TexturedModel(vao, indices.length, textures, new int[]{0,1}, shader);
	}

	public Model loadCube(float size, Texture[] textures, Shader shader){

		float cube[] = {
				-size, -size, -size, 0.0f, 0.0f, -1.0f,
				size, -size, -size, 0.0f, 0.0f, -1.0f,
				size, size, -size, 0.0f, 0.0f, -1.0f,
				-size, size, -size, 0.0f, 0.0f, -1.0f,

				-size, -size, size, 0.0f, 0.0f, 1.0f,
				size, -size, size, 0.0f, 0.0f, 1.0f,
				size, size, size, 0.0f, 0.0f, 1.0f,
				-size, size, size, 0.0f, 0.0f, 1.0f,

				-size, size, size, -1.0f, 0.0f, 0.0f,
				-size, size, -size, -1.0f, 0.0f, 0.0f,
				-size, -size, -size, -1.0f, 0.0f, 0.0f,
				-size, -size, size, -1.0f, 0.0f, 0.0f,

				size, size, size, 1.0f, 0.0f, 0.0f,
				size, size, -size, 1.0f, 0.0f, 0.0f,
				size, -size, -size, 1.0f, 0.0f, 0.0f,
				size, -size, size, 1.0f, 0.0f, 0.0f,

				-size, -size, -size, 0.0f, -1.0f, 0.0f,
				size, -size, -size, 0.0f, -1.0f, 0.0f,
				size, -size, size, 0.0f, -1.0f, 0.0f,
				-size, -size, size, 0.0f, -1.0f, 0.0f,

				-size, size, -size, 0.0f, 1.0f, 0.0f,
				size, size, -size, 0.0f, 1.0f, 0.0f,
				size, size, size, 0.0f, 1.0f, 0.0f,
				-size, size, size, 0.0f, 1.0f, 0.0f
		};

		int[] cubeElements = {
				0,1,2,2,3,0,
				4,5,6,6,7,4,
				8,9,10,10,11,8,
				12,13,14,14,15,12,
				16,17,18,18,19,16,
				20,21,22,22,23,20
		};

		return this.load(cube, cubeElements, textures, shader);
	}

	private void createAndLoadVBO(int attributeId, int dataSize, float[] data){
		int vbo = glGenBuffers();
		vboList.add(vbo);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, floatArrayToFloatBuffer(data), GL_STATIC_DRAW);
		glVertexAttribPointer(attributeId, dataSize, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	private void createAndLoadEBO(int[] indices) {
		int ebo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
		eboList.add(ebo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, intArrayToIntBuffer(indices), GL_STATIC_DRAW);
	}

	public void clean() {
		vaoList.forEach(GL30::glDeleteVertexArrays);
		vboList.forEach(GL30::glDeleteFramebuffers);
		eboList.forEach(GL30::glDeleteFramebuffers);
	}

	private FloatBuffer floatArrayToFloatBuffer(float[] data) {
		ByteBuffer buf = ByteBuffer.allocateDirect(data.length * 4);
		buf.order(ByteOrder.nativeOrder());
		FloatBuffer buffer = buf.asFloatBuffer();
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private IntBuffer intArrayToIntBuffer(int[] data) {
		ByteBuffer buf = ByteBuffer.allocateDirect(data.length * 4);
		buf.order(ByteOrder.nativeOrder());
		IntBuffer buffer = buf.asIntBuffer();
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

}

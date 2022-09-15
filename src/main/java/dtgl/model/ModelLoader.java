package dtgl.model;

import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL30.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class ModelLoader {

	private List<Integer> vaoList = new ArrayList<>(),
						  vboList = new ArrayList<>(),
						  eboList = new ArrayList<>();
	

	public Model load(float[] positions, int[] indices, Texture[] textures) {
		int vao = glGenVertexArrays();
		glBindVertexArray(vao);
		vaoList.add(vao);
		
		int vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		vboList.add(vbo);
		glBufferData(GL_ARRAY_BUFFER, floatArrayToFloatBuffer(positions), GL_STATIC_DRAW);
		
		int ebo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
		eboList.add(ebo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, intArrayToIntBuffer(indices), GL_STATIC_DRAW);
		
		int posSize = 3, colSize = 4, texSize = 2, vertexSizeBytes = (posSize+colSize+texSize)*Float.BYTES;
		
		glVertexAttribPointer(0, posSize, GL_FLOAT, false, vertexSizeBytes, 0);
		glEnableVertexAttribArray(0);
		
		glVertexAttribPointer(1, colSize, GL_FLOAT, false, vertexSizeBytes, posSize * Float.BYTES);
		glEnableVertexAttribArray(1);
	   
		glVertexAttribPointer(2, texSize, GL_FLOAT, false, vertexSizeBytes, (posSize+colSize) * Float.BYTES);
		glEnableVertexAttribArray(2);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
		
		return textures == null ? new PrimitiveModel(vao, indices.length) : new TexturedModel(vao, indices.length, textures);
	}

	public Model loadCube(float size, Texture[] textures){
		float[] cube = {
//				0.5f,  0.5f, 0.0f,   1.0f, 0.0f, 0.0f, 1,   1.0f, 1.0f,
//				0.5f, -0.5f, 0.0f,   0.0f, 1.0f, 0.0f, 1,   1.0f, 0.0f,
//				-0.5f, -0.5f, 0.0f,   0.0f, 0.0f, 1.0f, 1,   0.0f, 0.0f,
//				-0.5f,  0.5f, 0.0f,   1.0f, 1.0f, 0.0f, 1,   0.0f, 1.0f
				-size, -size,  size, 1.0f, 0.0f, 0.0f, 1, 1.0f, 1.0f,
				size, -size,  size, 0.0f, 1.0f, 0.0f, 1, 1.0f, 0.0f,
				size,  size,  size, 0.0f, 0.0f, 1.0f, 1, 0.0f, 0.0f,
				-size,  size,  size, 1.0f, 1.0f, 1.0f, 1, 0.0f, 1.0f,

				-size, -size, -size, 1.0f, 0.0f, 0.0f, 1, 1.0f, 1.0f,
				size, -size, -size, 0.0f, 1.0f, 0.0f, 1, 1.0f, 0.0f,
				size,  size, -size, 0.0f, 0.0f, 1.0f, 1, 0.0f, 0.0f,
				-size,  size, -size, 1.0f, 1.0f, 1.0f, 1, 0.0f, 1.0f
		};

		int[] cubeElements = {
				// front
				0, 1, 2,
				2, 3, 0,
				// right
				1, 5, 6,
				6, 2, 1,
				// back
				7, 6, 5,
				5, 4, 7,
				// left
				4, 0, 3,
				3, 7, 4,
				// bottom
				4, 5, 1,
				1, 0, 4,
				// top
				3, 2, 6,
				6, 7, 3
		};

		return this.load(cube, cubeElements, textures);
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

package dtgl.model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL30;

public class ModelLoader {

	private List<Integer> vaoList = new ArrayList<Integer>(),
						  vboList = new ArrayList<Integer>(),
						  eboList = new ArrayList<Integer>();

	public Model load(float[] positions, int[] indices) {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		vaoList.add(vao);
		
		int vbo = GL30.glGenBuffers();
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vbo);
		vboList.add(vbo);
		GL30.glBufferData(GL30.GL_ARRAY_BUFFER, floatArrayToFloatBuffer(positions), GL30.GL_STATIC_DRAW);
		
		int ebo = GL30.glGenBuffers();
		GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, ebo);
		eboList.add(ebo);
		GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, intArrayToIntBuffer(indices), GL30.GL_STATIC_DRAW);
		
		GL30.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, 0, 0);
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
		
		return new Model(vao, indices.length);
	}

	public void clean() {
		vaoList.forEach(vao -> GL30.glDeleteVertexArrays(vao));
		vboList.forEach(vbo -> GL30.glDeleteFramebuffers(vbo));
		eboList.forEach(ebo -> GL30.glDeleteFramebuffers(ebo));
	}
	
	public FloatBuffer floatArrayToFloatBuffer(float[] data) {
		ByteBuffer buf = ByteBuffer.allocateDirect(data.length * 4);
		buf.order(ByteOrder.nativeOrder());
		FloatBuffer buffer = buf.asFloatBuffer();
		buffer.put(data);
		buffer.rewind();
		return buffer;
	}
	
	public IntBuffer intArrayToIntBuffer(int[] data) {
		ByteBuffer buf = ByteBuffer.allocateDirect(data.length * 4);
		buf.order(ByteOrder.nativeOrder());
		IntBuffer buffer = buf.asIntBuffer();
		buffer.put(data);
		buffer.rewind();
		return buffer;
	}

}

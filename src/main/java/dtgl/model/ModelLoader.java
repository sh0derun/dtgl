package dtgl.model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;

public class ModelLoader {

	public List<Integer> vaoList = new ArrayList<Integer>(),
						  vboList = new ArrayList<Integer>(),
						  eboList = new ArrayList<Integer>(),
						  texList = new ArrayList<Integer>();

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
		
		int posSize = 3, colSize = 4, texSize = 2, vertexSizeBytes = (posSize+colSize+texSize)*Float.BYTES;
		
		GL30.glVertexAttribPointer(0, posSize, GL30.GL_FLOAT, false, vertexSizeBytes, 0);
		GL30.glEnableVertexAttribArray(0);
		
		GL30.glVertexAttribPointer(1, colSize, GL30.GL_FLOAT, false, vertexSizeBytes, posSize * Float.BYTES);
		GL30.glEnableVertexAttribArray(1);
	   
		GL30.glVertexAttribPointer(2, 2, GL30.GL_FLOAT, false, vertexSizeBytes, (posSize+colSize) * Float.BYTES);
		GL30.glEnableVertexAttribArray(2);
		
		int tex = GL30.glGenTextures();
		GL30.glBindTexture(GL30.GL_TEXTURE_2D, tex);
		System.out.println("text = "+tex);
		texList.add(tex);
		
		IntBuffer width  	 = BufferUtils.createIntBuffer(1),
				  height 	 = BufferUtils.createIntBuffer(1), 
				  nrChannels = BufferUtils.createIntBuffer(1);
		
		STBImage.stbi_set_flip_vertically_on_load(true);
		ByteBuffer data = STBImage.stbi_load("res/checker.png", width, height, nrChannels, 0);
		
		GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_S, GL30.GL_CLAMP_TO_EDGE);	
		GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_T, GL30.GL_CLAMP_TO_EDGE);
		GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_LINEAR);
		GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_LINEAR);
		
		if(data != null) {
			GL30.glTexImage2D(GL30.GL_TEXTURE_2D, 0, GL30.GL_LUMINANCE, width.get(0), height.get(0), 0,GL30.GL_LUMINANCE, GL30.GL_UNSIGNED_BYTE, data);
			//GL30.glTexImage2D(GL30.GL_TEXTURE_2D, 0, GL30.GL_RGBA8, width.get(0), height.get(0), 0,GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE, data);
			GL30.glGenerateMipmap(GL30.GL_TEXTURE_2D);
		}
		
		STBImage.stbi_image_free(data);
		
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
		
		return new Model(vao, indices.length);
	}

	public void clean() {
		vaoList.forEach(vao -> GL30.glDeleteVertexArrays(vao));
		vboList.forEach(vbo -> GL30.glDeleteFramebuffers(vbo));
		eboList.forEach(ebo -> GL30.glDeleteFramebuffers(ebo));
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

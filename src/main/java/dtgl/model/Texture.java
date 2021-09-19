package dtgl.model;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class Texture {
	
	private int id;
	private String texturePath;
	private int width, height;
	private String uniformName;
	
	public Texture(String texturePath, String uniformName) {
		this.id = glGenTextures();
		if(uniformName.trim().length() == 0) {
			assert false : "uniform name lenght should be more than 0 !";
		}
		if(texturePath.trim().length() == 0) {
			assert false : "texture path lenght should be more than 0 !";
		}
		this.texturePath = texturePath;
		this.uniformName = uniformName;
	}
	
	public void bindTexture() {
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	public void unbindTexture() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public void setTextureParams(int wrapS, int wrapT, int magFilter, int minFilter) {
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrapS);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrapT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minFilter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magFilter);
	}
	
	public void loadTexture(int internalFormat, int format) {
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		IntBuffer nbChannels = BufferUtils.createIntBuffer(1);
		
		stbi_set_flip_vertically_on_load(true);
		ByteBuffer pixelData = stbi_load(this.texturePath, w, h, nbChannels, 0);
		
		if(pixelData != null) {
			this.width = w.get();
			this.height = h.get();
			glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, this.width, this.height, 0, format, GL_UNSIGNED_BYTE, pixelData);
			glGenerateMipmap(GL_TEXTURE_2D);
		}
		else {
			assert false : "texture couldn't be loaded : "+this.texturePath;
		}
		
		stbi_image_free(pixelData);
	}

	public int getId() {
		return id;
	}

	public String getTexturePath() {
		return texturePath;
	}

	public void setTexturePath(String texturePath) {
		if(texturePath.trim().length() > 0) {
			this.texturePath = texturePath;
		}
		else {
			assert false : "texture path lenght should be more than 0 !";
		}
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		if(width > 0) {
			this.width = width;
		}
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		if(height > 0) {
			this.height = height;
		}
	}

	public String getUniformName() {
		return uniformName;
	}

	public void setUniformName(String uniformName) {
		if(uniformName.trim().length() > 0) {
			this.uniformName = uniformName;
		}
		else {
			assert false : "uniform name lenght should be more than 0 !";
		}
	}
	
	

}

package dtgl.display;

import dtgl.display.listener.Listener;
import dtgl.exception.ApplicationRuntimeException;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {

	private long windowId;
	private String title;
	private int width, height;
	private static final Window WINDOW = new Window();

	private Window() {
		title = "DTGL";
		width = 800;
		height = 800;
		createWindow(title, width, height);
	}

	public static Window getInstance() {
		return WINDOW;
	}

	private void createWindow(String title, int width, int height) {

		System.out.println(Version.getVersion());

		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, 0);
		glfwWindowHint(GLFW_RESIZABLE, 1);

		windowId = glfwCreateWindow(width, height, title, 0, 0);

		if (windowId == 0) {
			throw new ApplicationRuntimeException("Could not create window !");
		}

		glfwMakeContextCurrent(windowId);
		glfwSwapInterval(1);
		glfwShowWindow(windowId);
		GL.createCapabilities();
		
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_BLEND);

		glEnable(GL_DEPTH_TEST);
		glDepthMask(true);

		glfwSetWindowSizeCallback(windowId, (window, w, h) -> {
			WINDOW.width = w;
			WINDOW.height = h;
			GL11.glViewport(0, 0, w, h);
		});
		
		glfwSetKeyCallback(windowId, Listener.getInstance()::key_callback);
		glfwSetMouseButtonCallback(windowId, Listener.getInstance()::mouse_button_callback);
		glfwSetCursorPosCallback(windowId, Listener.getInstance()::cursor_position_callback);

		System.out.println(GL11.glGetString(GL11.GL_VERSION));
	}

	public void free() {
		glfwFreeCallbacks(windowId);
		glfwDestroyWindow(windowId);
		glfwTerminate();
	}

	public boolean isClosed() {
		return glfwWindowShouldClose(windowId);
	}

	public void clear() {
		glClearColor(0,0,0,1);
		//glClearDepth(1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void update() {
		glfwPollEvents();
		glfwSwapBuffers(windowId);
	}

	public long getWindowId(){
		return windowId;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}

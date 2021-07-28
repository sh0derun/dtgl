package dtgl.display;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;

import java.nio.IntBuffer;
import java.util.Arrays;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import dtgl.display.listener.Listener;

public class Window {

	private long windowId;
	private String title;
	private int width, height;
	private static final Window WINDOW = new Window();

	private Window() {
		title = "DTGL";
		width = 500;
		height = 500;
		createWindow(title, width, height);
	}

	public static Window getInstance() {
		return WINDOW;
	}

	private void createWindow(String title, int width, int height) {

		System.out.println(Version.getVersion());

		GLFWErrorCallback.createPrint(System.err).set();

		if (!GLFW.glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, 0);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, 1);

		windowId = GLFW.glfwCreateWindow(width, height, title, 0, 0);

		if (windowId == 0) {
			throw new RuntimeException("Could not create window !");
		}

		GLFW.glfwMakeContextCurrent(windowId);
		GLFW.glfwSwapInterval(1);
		GLFW.glfwShowWindow(windowId);
		GL.createCapabilities();

		GLFW.glfwSetWindowSizeCallback(windowId, (window, m_width, m_height) -> {
			WINDOW.width = m_width;
			WINDOW.height = m_height;
			GL11.glViewport(0, 0, m_width, m_height);
		});
		
		GLFW.glfwSetKeyCallback(windowId, Listener.getInstance()::key_callback);
		GLFW.glfwSetMouseButtonCallback(windowId, Listener.getInstance()::mouse_button_callback);
		GLFW.glfwSetCursorPosCallback(windowId, Listener.getInstance()::cursor_position_callback);

		System.out.println(GL11.glGetString(GL11.GL_VERSION));
	}

	public void free() {
		glfwFreeCallbacks(windowId);
		GLFW.glfwDestroyWindow(windowId);
		GLFW.glfwTerminate();
	}

	public boolean isClosed() {
		return GLFW.glfwWindowShouldClose(windowId);
	}

	public void clear() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void update() {
		GLFW.glfwPollEvents();
		GLFW.glfwSwapBuffers(windowId);
	}

	public int[] toArray(IntBuffer b) {
		if (b.hasArray()) {
			if (b.arrayOffset() == 0)
				return b.array();

			return Arrays.copyOfRange(b.array(), b.arrayOffset(), b.array().length);
		}

		b.rewind();
		int[] foo = new int[b.remaining()];
		b.get(foo);

		return foo;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}

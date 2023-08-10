package dtgl.display.listener;

import java.util.Arrays;

import org.lwjgl.glfw.GLFW;

import dtgl.math.Vec2;

public class Listener {
	double x, y;
	double offx, offy;
	boolean[] mouseBtnStates;
	boolean[] keyboardBtnStates;
	static final int MAX_MOUSE_BTN = 10;
	static final int MAX_KEYBOARD_BTN = 400;
	
	private final static Listener LISTENER = new Listener();
	
	private Listener() {
		mouseBtnStates = new boolean[MAX_MOUSE_BTN];
		Arrays.fill(mouseBtnStates, false);
		
		keyboardBtnStates = new boolean[MAX_KEYBOARD_BTN];
		Arrays.fill(keyboardBtnStates, false);
	}
	
	public static Listener getInstance() {
		return LISTENER;
	}
	
	public void key_callback(long window, int key, int scancode, int action, int mods) {
		if(key == -1)
			return;
		getInstance().keyboardBtnStates[key] = action != GLFW.GLFW_RELEASE;
	}
	
	public void mouse_button_callback(long window, int button, int action, int mods) {
		getInstance().mouseBtnStates[button] = action != GLFW.GLFW_RELEASE;
	}
	
	public void cursor_position_callback(long window, double xpos, double ypos) {
		x = xpos;
		y = ypos;
	}
	
	public boolean isKeyPressed(int key) {
		if(key < 0 || key >= getInstance().keyboardBtnStates.length)
			throw new RuntimeException("provided keycode '"+key+"' is out of range !");
		return getInstance().keyboardBtnStates[key];
	}
	
	public boolean isMouseButtonPressed(int button) {
		if(button < 0 || button >= getInstance().mouseBtnStates.length)
			throw new RuntimeException("provided button '"+button+"' is out of range !");
		return getInstance().mouseBtnStates[button];
	}
	
	public Vec2 getMousePos() {
		return new Vec2((float)x, (float)y);
	}
	
}

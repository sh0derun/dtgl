package dtgl.engine;

import dtgl.display.Window;
import dtgl.display.listener.Listener;
import dtgl.model.AbstractRenderer;
import dtgl.model.ModelLoader;
import org.lwjgl.glfw.GLFW;


/*
Main engine class DTGLEngine
*/
public class DTGLEngine {

    private Window window = Window.getInstance();
    private Listener listener = Listener.getInstance();
    private ModelLoader modelLoader;
    private AbstractRenderer renderer;

    public DTGLEngine(AbstractRenderer abstractRenderer){
        modelLoader = new ModelLoader();
        renderer = abstractRenderer;
    }

    public void handleInputs(){
        if(listener.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            GLFW.glfwSetWindowShouldClose(window.getWindowId(), true);
        }
    }

    public Window getWindow() {
        return window;
    }

    public ModelLoader getModelLoader() {
        return modelLoader;
    }

    public AbstractRenderer getRenderer() {
        return renderer;
    }
}

package dtgl.renderer;

import dtgl.display.Window;
import dtgl.model.Model;
import dtgl.shader.UniformsHandler;

import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

public abstract class AbstractRenderer {

    UniformsHandler uniformsHandler;
    Window window;

    protected AbstractRenderer(UniformsHandler handler){
        uniformsHandler = handler;
        window = Window.getInstance();
    }

    public abstract void render(Model model);

    protected void enableVertexAttribArrays(int... indices) {
        for (int index : indices) {
            glEnableVertexAttribArray(index);
        }
    }

    protected void disableVertexAttribArrays(int... indices) {
        for (int index : indices) {
            glDisableVertexAttribArray(index);
        }
    }

    public Window getWindow() {
        return window;
    }
}

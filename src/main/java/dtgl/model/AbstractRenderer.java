package dtgl.model;

import dtgl.display.Window;
import dtgl.shader.Shader;
import dtgl.shader.UniformsHandler;

import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

public abstract class AbstractRenderer {

    UniformsHandler uniformsHandler;

    protected AbstractRenderer(UniformsHandler handler){
        uniformsHandler = handler;
    }

    public abstract void render(Model model, Shader shader, Window window);

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

}

package dtgl.engine;

import dtgl.display.Window;
import dtgl.display.listener.Listener;
import dtgl.model.Model;
import dtgl.model.ModelLoader;
import dtgl.renderer.AbstractRenderer;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;


/*
* Main engine class DTGLEngine
**/
public class DTGLEngine {

    private Listener listener = Listener.getInstance();
    private ModelLoader modelLoader;
    private AbstractRenderer renderer;
    private Window window;
    private EngineLogic engineLogic;

    private List<Model> models;

    public DTGLEngine(AbstractRenderer abstractRenderer, EngineSetup engineSetup, EngineLogic logic){
        modelLoader = new ModelLoader();
        renderer = abstractRenderer;
        models = new ArrayList<>();
        window = renderer.getWindow();
        engineLogic = logic;

        loadResources(engineSetup);
    }

    public void loadResources(EngineSetup engineSetup){
        addObjects(engineSetup.setup(modelLoader));
    }

    public void loop(){
        while(!window.isClosed()) {
            float time = (float)GLFW.glfwGetTime();
            window.clear();

            process(time);
            handleInputs();

            window.update();
        }
        modelLoader.clean();
        window.free();
    }

    public void process(float time){
        engineLogic.logic(renderer, time);
    }

    private void addObject(Model object){
        models.add(object);
    }

    private void addObjects(List<Model> objects){
        this.models.addAll(objects);
    }

    public void handleInputs(){
        if(listener.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            window.close();
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

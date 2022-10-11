package dtgl.engine;

import dtgl.display.Window;
import dtgl.display.listener.Listener;
import dtgl.math.Vec3;
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

    private Window window = Window.getInstance();
    private Listener listener = Listener.getInstance();
    private ModelLoader modelLoader;
    private AbstractRenderer renderer;

    private List<Model> models;

    public DTGLEngine(AbstractRenderer abstractRenderer, EngineSetup engineSetup){
        modelLoader = new ModelLoader();
        renderer = abstractRenderer;
        models = new ArrayList<>();
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
        for (Model model : models) {
            model.getShader().activate();
            // TODO: 12/10/2022 Model logic needs to be declared by user
            renderer.render(model, window, ()->model.setRot(new Vec3(time*20,time*20, time*20)));
            model.getShader().deactivate();
        }
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

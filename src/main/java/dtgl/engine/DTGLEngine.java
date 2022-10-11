package dtgl.engine;

import dtgl.display.Window;
import dtgl.display.listener.Listener;
import dtgl.math.Vec3;
import dtgl.renderer.AbstractRenderer;
import dtgl.model.Model;
import dtgl.model.ModelLoader;
import dtgl.shader.Shader;
import dtgl.utils.obj.OBJModel;
import dtgl.utils.obj.OBJModelLoader;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;


/*
Main engine class DTGLEngine
*/
public class DTGLEngine {

    private Window window = Window.getInstance();
    private Listener listener = Listener.getInstance();
    private ModelLoader modelLoader;
    private AbstractRenderer renderer;

    private List<Model> objects;

    public DTGLEngine(AbstractRenderer abstractRenderer){
        modelLoader = new ModelLoader();
        renderer = abstractRenderer;
        objects = new ArrayList<>();
        loadResources();
    }

    public void loadResources(){
        Shader shader = new Shader("shaders/vs_isosphere.glsl", "shaders/fs_isosphere.glsl");
        OBJModel objModel = OBJModelLoader.LoadModelDataFromOBJ("res/isosphere.obj");
        Model model = modelLoader.load(objModel, null, shader);
        model.setScale(2);
        addObject(model);
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
        for (Model model : objects) {
            model.getShader().activate();
            renderer.render(model, window, ()->model.setRot(new Vec3(time*20,time*20, time*20)));
            model.getShader().deactivate();
        }
    }

    private void addObject(Model object){
        objects.add(object);
    }

    private void addObjects(List<Model> objects){
        this.objects.addAll(objects);
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

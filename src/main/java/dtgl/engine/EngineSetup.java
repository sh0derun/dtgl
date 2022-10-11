package dtgl.engine;

import dtgl.model.Model;
import dtgl.model.ModelLoader;

import java.util.List;

/*
* interface to setup models before engine loop
* */
public interface EngineSetup {

    List<Model> setup(ModelLoader modelLoader);

}

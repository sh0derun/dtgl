package dtgl.engine;

import dtgl.renderer.AbstractRenderer;

/*
 * Interface to setup engine logic that will goes in main loop
 * */
public interface EngineLogic {

    void logic(AbstractRenderer renderer, float time);

}

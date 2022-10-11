package dtgl;

import dtgl.engine.DTGLEngine;
import dtgl.renderer.ModelRenderer;

public class Main {

	public static void main(String[] args) {
		DTGLEngine engine = new DTGLEngine(new ModelRenderer());
		engine.loop();
	}

}
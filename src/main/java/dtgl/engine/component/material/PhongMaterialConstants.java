package dtgl.engine.component.material;

import dtgl.math.Vec3;

/*From http://devernay.free.fr/cours/opengl/materials.html*/

public final class PhongMaterialConstants {

    public static final PhongMaterial[] materials = {

            new PhongMaterial("Brass",

                    new Vec3(0.329412f, 0.223529f, 0.027451f),

                    new Vec3(0.780392f, 0.568627f, 0.113725f),

                    new Vec3(0.992157f, 0.941176f, 0.807843f),

                    27.8974f),

            new PhongMaterial("Bronze",

                    new Vec3(0.2125f, 0.1275f, 0.054f),

                    new Vec3(0.714f, 0.4284f, 0.18144f),

                    new Vec3(0.393548f, 0.271906f, 0.166721f),

                    25.6f),

            new PhongMaterial("Polished Bronze",

                    new Vec3(0.25f, 0.148f, 0.06475f),

                    new Vec3(0.4f, 0.2368f, 0.1036f),

                    new Vec3(0.774597f, 0.458561f, 0.200621f),

                    76.8f),

            new PhongMaterial("Chrome",

                    new Vec3(0.25f, 0.25f, 0.25f),

                    new Vec3(0.4f, 0.4f, 0.4f),

                    new Vec3(0.774597f, 0.774597f, 0.774597f),

                    76.8f),

            new PhongMaterial("Copper",

                    new Vec3(0.19125f, 0.0735f, 0.0225f),

                    new Vec3(0.7038f, 0.27048f, 0.0828f),

                    new Vec3(0.256777f, 0.137622f, 0.086014f),

                    12.8f),

            new PhongMaterial("Polished Copper",

                    new Vec3(0.2295f, 0.08825f, 0.0275f),

                    new Vec3(0.5508f, 0.2118f, 0.066f),

                    new Vec3(0.580594f, 0.223257f, 0.0695701f),

                    51.2f),

            new PhongMaterial("Gold",

                    new Vec3(0.24725f, 0.1995f, 0.0745f),

                    new Vec3(0.75164f, 0.60648f, 0.22648f),

                    new Vec3(0.628281f, 0.555802f, 0.366065f),

                    51.2f),

            new PhongMaterial("Polished Gold",

                    new Vec3(0.24725f, 0.2245f, 0.0645f),

                    new Vec3(0.34615f, 0.3143f, 0.0903f),

                    new Vec3(0.797357f, 0.723991f, 0.208006f),

                    83.2f),

            new PhongMaterial("Pewter",

                    new Vec3(0.105882f, 0.058824f, 0.113725f),

                    new Vec3(0.427451f, 0.470588f, 0.541176f),

                    new Vec3(0.333333f, 0.333333f, 0.521569f),

                    9.84615f),

            new PhongMaterial("Silver",

                    new Vec3(0.19225f, 0.19225f, 0.19225f),

                    new Vec3(0.50754f, 0.50754f, 0.50754f),

                    new Vec3(0.508273f, 0.508273f, 0.508273f),

                    51.2f),

            new PhongMaterial("Polished Silver",

                    new Vec3(0.23125f, 0.23125f, 0.23125f),

                    new Vec3(0.2775f, 0.2775f, 0.2775f),

                    new Vec3(0.773911f, 0.773911f, 0.773911f),

                    89.6f),

            new PhongMaterial("Emerald",

                    new Vec3(0.0215f, 0.1745f, 0.0215f),

                    new Vec3(0.07568f, 0.61424f, 0.07568f),

                    new Vec3(0.633f, 0.727811f, 0.633f),

                    76.8f),

            new PhongMaterial("Jade",

                    new Vec3(0.135f, 0.2225f, 0.1575f),

                    new Vec3(0.54f, 0.89f, 0.63f),

                    new Vec3(0.316228f, 0.316228f, 0.316228f),

                    12.8f),

            new PhongMaterial("Obsidian",

                    new Vec3(0.05375f, 0.05f, 0.06625f),

                    new Vec3(0.18275f, 0.17f, 0.22525f),

                    new Vec3(0.332741f, 0.328634f, 0.346435f),

                    38.4f),

            new PhongMaterial("Pearl",

                    new Vec3(0.25f, 0.20725f, 0.20725f),

                    new Vec3(1.0f, 0.829f, 0.829f),

                    new Vec3(0.296648f, 0.296648f, 0.296648f),

                    11.264f),

            new PhongMaterial("Ruby",

                    new Vec3(0.1745f, 0.01175f, 0.01175f),

                    new Vec3(0.61424f, 0.04136f, 0.04136f),

                    new Vec3(0.727811f, 0.626959f, 0.626959f),

                    76.8f),

            new PhongMaterial("Turquoise",

                    new Vec3(0.1f, 0.18725f, 0.1745f),

                    new Vec3(0.396f, 0.74151f, 0.69102f),

                    new Vec3(0.297254f, 0.30829f, 0.306678f),

                    12.8f),

            new PhongMaterial("Black Plastic",

                    new Vec3(0.0f, 0.0f, 0.0f),

                    new Vec3(0.01f, 0.01f, 0.01f),

                    new Vec3(0.50f, 0.50f, 0.50f),

                    32.0f),

            new PhongMaterial("Black Rubber",

                    new Vec3(0.02f, 0.02f, 0.02f),

                    new Vec3(0.01f, 0.01f, 0.01f),

                    new Vec3(0.4f, 0.4f, 0.4f),

                    10.0f)
    };

}

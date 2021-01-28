package engine.main;

import engine.objects.Model;
import engine.render.Display;
import engine.render.Shader;
import engine.render.Texture;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.opengl.KHRDebug;

public class Main {
    private Display display;
    private Model[] models;
    private Texture texture;

    private boolean increment = true;
    private float r = 0.00f, g = 0.00f, b = 0.00f;

    private float[] positions = {
            -0.5f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, 0.5f, 0.0f,
            0.5f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
    };

    private float[] textureCoords = {
            0, 1,
            0, 0,
            1, 0,
            1, 1
    };

    private void start() throws InterruptedException {
        init();
        loop();
        close();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        display = new Display(1280, 720, "Testing Window");

        GL11.glEnable(KHRDebug.GL_DEBUG_OUTPUT_SYNCHRONOUS);
        GLUtil.setupDebugMessageCallback();

        models = new Model[] {
            new Model(positions, textureCoords)
        };

        GL20.glActiveTexture(GL20.GL_TEXTURE0);

        texture = new Texture("test.png");

        Shader.loadAllShaders();
    }

    private void loop() throws InterruptedException {
        while(!display.shouldClose()) {
            display.pollEvents();

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            GL11.glClearColor(0.5f, 0.5f, 0.5f, 0);

            texture.bind();
            Shader.basic.bind();
            Shader.basic.setUniform1i("texSampler", 0);

            for(Model m : models) {
                m.render();
            }

            Shader.basic.unbind();
            texture.unbind();

            display.render();
        }
    }

    private void close() {
        for(Model model : models) {
            model.cleanup();
        }

        display.close();
    }

    public static void main(String[] args) throws InterruptedException {
        new Main().start();
    }
}
package engine.render;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Display {
    private long window;

    public Display(int width, int height, String title) {
        create(width, height, title);
    }

    private void create(int width, int height, String title) {
        System.out.println("Creating Window");

        if(!glfwInit())
            throw new IllegalStateException("Failed to initialize GLFW.");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);

        window = glfwCreateWindow(width, height, title, NULL, NULL);

        if(window == NULL)
            throw new RuntimeException("Failed to create the GLFW window.");

        glfwMakeContextCurrent(window);
        glfwShowWindow(window);

        GL.createCapabilities();

//        GL11.glEnable(GL11.GL_BLEND);
//        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void pollEvents() {
        glfwPollEvents();
    }

    public void render() {
        int error = GL11.glGetError();

        if(error != GL11.GL_NO_ERROR)
            System.out.println("OpenGL Error: " + error);

        glfwSwapBuffers(window);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public void close() {
        Callbacks.glfwFreeCallbacks(window);

        glfwDestroyWindow(window);
        glfwTerminate();
    }
}
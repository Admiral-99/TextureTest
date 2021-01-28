package engine.render;

import engine.utils.FileUtils;
import org.lwjgl.opengl.GL20;

public class Shader {
    public static Shader basic;

    private int id;
    private String[] sources;

    public Shader(String name) {
        sources = FileUtils.loadShaderStrings("shaders/" + name);

        create(sources);
    }

    public static void loadAllShaders() {
        basic = new Shader("basic");
    }

    private void create(String[] sources) {
        String vertex = sources[0], fragment = sources[1];

        id = GL20.glCreateProgram();

        int vertexId = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        int fragmentId = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);

        GL20.glShaderSource(vertexId, vertex);
        GL20.glShaderSource(fragmentId, fragment);

        GL20.glCompileShader(vertexId);

        if(GL20.glGetShaderi(vertexId, GL20.GL_COMPILE_STATUS) == GL20.GL_FALSE) {
            System.err.println("Failed to compile vertex shader.");
            System.err.println(GL20.glGetShaderInfoLog(vertexId));

            return;
        }

        GL20.glCompileShader(fragmentId);

        if(GL20.glGetShaderi(fragmentId, GL20.GL_COMPILE_STATUS) == GL20.GL_FALSE) {
            System.err.println("Failed to compile fragment shader.");
            System.err.println(GL20.glGetShaderInfoLog(fragmentId));

            return;
        }

        GL20.glAttachShader(id, vertexId);
        GL20.glAttachShader(id, fragmentId);

        GL20.glLinkProgram(id);
        GL20.glValidateProgram(id);

        GL20.glDeleteShader(vertexId);
        GL20.glDeleteShader(fragmentId);
    }

    public void setUniform1i(String name, int value) {
        GL20.glUniform1i(getUniformLocation(name), value);
    }

    public void setUniform1f(String name, float value) {
        GL20.glUniform1f(getUniformLocation(name), value);
    }

    public void setUniform2f(String name, float x, float y) {
        GL20.glUniform2f(getUniformLocation(name), x, y);
    }

    public void setUniform3f(String name, float x, float y, float z) {
        GL20.glUniform3f(getUniformLocation(name), x, y, z);
    }

//    public void setUniformMat4(String name, Matrix4f matrix) {
//        GL20.glUniformMatrix4fv(getUniformLocation(name), false, matrix.getFloatBuffer());
//    }

    private int getUniformLocation(String name) {
        return GL20.glGetUniformLocation(id, name);
    }

    public void bind() {
        GL20.glUseProgram(id);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }
}
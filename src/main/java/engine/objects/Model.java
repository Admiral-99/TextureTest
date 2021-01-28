package engine.objects;

import engine.utils.BufferUtil;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private int vaoId, vertexCount;
    private List<Integer> vaos = new ArrayList<Integer>(), vbos = new ArrayList<Integer>();

    public Model(float[] positions, float[] textureCoords) {
        createBindAndStoreVao();

        vertexCount = positions.length;

        createVboAndStoreData(0, 3, positions);
        createVboAndStoreData(1, 2, textureCoords);

        GL30.glBindVertexArray(0);
    }

    private void createBindAndStoreVao() {
        vaoId = GL30.glGenVertexArrays();

        vaos.add(vaoId);

        GL30.glBindVertexArray(vaoId);
    }

    private void createVboAndStoreData(int index, int size, float[] data) {
        int id = GL15.glGenBuffers();

        vbos.add(id);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, BufferUtil.createFloatBuffer(data), GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public void cleanup() {
        for(int id : vaos) {
            GL30.glDeleteVertexArrays(id);
        }

        for(int id : vbos) {
            GL15.glDeleteBuffers(id);
        }

        vaos.clear();
        vbos.clear();
    }

    public void render() {
        GL30.glBindVertexArray(vaoId);
        GL20.glEnableVertexAttribArray(0);
        GL15.glDrawArrays(GL15.GL_TRIANGLES, 0, vertexCount);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }
}
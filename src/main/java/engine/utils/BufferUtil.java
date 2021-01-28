package engine.utils;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtil {
    public static FloatBuffer createFloatBuffer(float[] data) {
        return BufferUtils.createFloatBuffer(data.length).put(data).flip();
    }

    public static IntBuffer createIntBuffer(int[] data) {
        return BufferUtils.createIntBuffer(data.length).put(data).flip();
    }

    public static IntBuffer createTextureIntBuffer(int[] data) {
        IntBuffer result = ByteBuffer.allocateDirect(data.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();

        result.put(data).flip();

        return result;
    }
}
package engine.math;

public class Matrix4f {
    private static final int size = 4 * 4;
    private final float[] elements = new float[size];

    public Matrix4f() {

    }

    public static Matrix4f identity() {
        Matrix4f matrix = new Matrix4f();

        for(int i = 0; i < size; i++) {
            matrix.elements[i] = 0.0f;
        }

        matrix.elements[4 * 0 + 0] = 1.0f;
        matrix.elements[4 * 1 + 1] = 1.0f;
        matrix.elements[4 * 2 + 2] = 1.0f;
        matrix.elements[4 * 3 + 3] = 1.0f;

        return matrix;
    }

//    public static Matrix4f projection() {
//        Matrix4f matrix = Matrix4f.identity();
//
//        return matrix;
//    }
}
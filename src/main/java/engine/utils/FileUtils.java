package engine.utils;

import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class FileUtils {
    public static String[] loadShaderStrings(String shaderName) {
        String[] files = new String[2];
        StringBuilder builder = new StringBuilder();

        try {
            for(int i = 0; i < 2; i++) {
                ClassLoader loader = FileUtils.class.getClassLoader();
                InputStream stream = loader.getResourceAsStream((i == 0) ? shaderName + ".vert" : shaderName + ".frag");
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                String line = "";

                while((line = reader.readLine()) != null) {
                    builder.append(line + "\n");
                }

                files[i] = builder.toString();

                builder.setLength(0);
            }
        } catch(IOException e) {
            System.err.println("An error occurred while attempting to load '" + shaderName + "' shader files.");
            e.printStackTrace();
        }

        return files;
    }

    public static int loadTexture(String path) {
        int[] pixels = null;
        int width = 1, height = 1;

        try {
            BufferedImage image = ImageIO.read(new FileInputStream(path));

//            width = image.getWidth();
//            height = image.getHeight();
//
//            pixels = new int[width * height];
            pixels = new int[] { 0x00000000 };

            image.getRGB(0, 0, width, height, pixels, 0, width);
        } catch(IOException e) {
            System.err.println("An error occurred while loading an image.");
            e.printStackTrace();
        }

        int[] data = new int[width * height];

        for(int i = 0; i < width * height; i++) {
            int a = (pixels[i] & 0xff000000) >> 24;
            int r = (pixels[i] & 0xff0000) >> 16;
            int g = (pixels[i] & 0xff00) >> 8;
            int b = (pixels[i] & 0xff);

            data[i] = a << 24 | b << 16 | g << 8 | r;
        }

        int result = GL11.glGenTextures();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, result);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, BufferUtil.createTextureIntBuffer(data));

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        return result;
    }
}
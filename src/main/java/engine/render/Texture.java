package engine.render;

import engine.utils.BufferUtil;
import engine.utils.FileUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Texture {
    private int textureId;

    public Texture(String path) {
        textureId = FileUtils.loadTexture("src/main/java/engine/" + path);
    }

    public void bind() {
        GL20.glBindTexture(GL20.GL_TEXTURE_2D, textureId);
    }

    public void unbind() {
        GL20.glBindTexture(GL20.GL_TEXTURE_2D, 0);
    }
}
package opengl.game.texture;

import opengl.game.util.PNGDecoder;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.annotation.Nonnull;

public class Texture implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Texture.class);

    private final int id;
    @Nonnull
    private final TextureType textureType;
    @Nonnull
    private final ByteBuffer buffer;
    private final boolean hasAlpha;
    private final int width;
    private final int height;

    Texture(@Nonnull final TextureType textureType) throws IOException {
        this.textureType = textureType;
        final String resource = textureType.getResource();
        try (final InputStream inputStream = Texture.class.getClassLoader().getResourceAsStream(resource)) {
            if (inputStream == null) {
                throw new IOException("Texture " + textureType + " resource not found: " + resource);
            }

            final PNGDecoder decoder = new PNGDecoder(inputStream);
            hasAlpha = decoder.hasAlpha();
            width = decoder.getWidth();
            height = decoder.getHeight();
            final PNGDecoder.Format format = hasAlpha ? PNGDecoder.Format.RGBA : PNGDecoder.Format.RGB;
            buffer = ByteBuffer.allocateDirect(format.getNumComponents() * width * height);
            decoder.decode(buffer, width * format.getNumComponents(), format);
            buffer.flip();
        }
        id = GL11.glGenTextures();
    }

    public int getId() {
        return id;
    }

    @Nonnull
    public TextureType getTextureType() {
        return textureType;
    }

    @Nonnull
    public ByteBuffer getBuffer() {
        return buffer;
    }

    public boolean hasAlpha() {
        return hasAlpha;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void bind() {
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        if (hasAlpha) {
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA,
                    GL11.GL_UNSIGNED_BYTE, buffer);
        } else {
            GL11.glTexImage2D(
                    GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB8, width, height, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, buffer);
        }
    }

    public void render() {
        GL13.glActiveTexture(textureType.getIndex());
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
    }

    @Override
    public void close() {
        GL11.glDeleteTextures(id);
    }
}

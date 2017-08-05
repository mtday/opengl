package opengl.game.texture;

import opengl.game.util.PNGDecoder;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.annotation.Nonnull;

public class Texture {
    private static final Logger LOGGER = LoggerFactory.getLogger(Texture.class);

    private final int id;
    @Nonnull
    private final TextureType textureType;

    Texture(@Nonnull final TextureType textureType) {
        this.textureType = textureType;
        final String resource = textureType.getResource();
        try (final InputStream inputStream = Texture.class.getClassLoader().getResourceAsStream(resource)) {
            if (inputStream == null) {
                throw new RuntimeException("Texture " + textureType + " resource not found: " + resource);
            }

            final PNGDecoder decoder = new PNGDecoder(inputStream);
            final boolean hasAlpha = decoder.hasAlpha();
            final int width = decoder.getWidth();
            final int height = decoder.getHeight();
            final PNGDecoder.Format format = hasAlpha ? PNGDecoder.Format.RGBA : PNGDecoder.Format.RGB;
            final ByteBuffer buffer = ByteBuffer.allocateDirect(format.getNumComponents() * width * height);
            decoder.decode(buffer, width * format.getNumComponents(), format);
            buffer.flip();

            id = GL11.glGenTextures();
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
            if (hasAlpha) {
                GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA,
                        GL11.GL_UNSIGNED_BYTE, buffer);
            } else {
                GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB8, width, height, 0, GL11.GL_RGB,
                        GL11.GL_UNSIGNED_BYTE, buffer);
            }
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
            GL11.glDeleteTextures(id);
        } catch (final IOException ioException) {
            throw new RuntimeException("Failed to load texture " + textureType, ioException);
        }
    }

    public int getId() {
        return id;
    }

    @Nonnull
    public TextureType getTextureType() {
        return textureType;
    }

    public void activate() {
        GL13.glActiveTexture(textureType.getIndex());
    }
}

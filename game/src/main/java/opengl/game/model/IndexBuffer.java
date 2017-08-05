package opengl.game.model;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.nio.IntBuffer;

import javax.annotation.Nonnull;

public class IndexBuffer implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexBuffer.class);

    private final int id;
    @Nonnull
    private final IntBuffer buffer;
    private final int count;

    public IndexBuffer(@Nonnull final IntBuffer buffer) {
        this.buffer = buffer;
        count = buffer.remaining();
        id = GL15.glGenBuffers();
    }

    public IndexBuffer(@Nonnull final int[] array) {
        this(toBuffer(array));
    }

    public int getId() {
        return id;
    }

    @Nonnull
    public IntBuffer getBuffer() {
        return buffer;
    }

    public int getCount() {
        return count;
    }

    @Nonnull
    private static IntBuffer toBuffer(@Nonnull final int[] array) {
        final IntBuffer buffer = BufferUtils.createIntBuffer(array.length);
        buffer.put(array);
        buffer.flip();
        return buffer;
    }

    public void bind() {
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, id);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    @Override
    public void close() {
        GL15.glDeleteBuffers(id);
    }
}

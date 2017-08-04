package opengl.game.model;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import java.io.Closeable;
import java.nio.FloatBuffer;

import javax.annotation.Nonnull;

public class AttributeBuffer implements Closeable {
    private final int id;
    @Nonnull
    private final AttributeType attributeType;
    @Nonnull
    private final FloatBuffer buffer;
    private final int count;

    public AttributeBuffer(@Nonnull final AttributeType attributeType, @Nonnull final FloatBuffer buffer) {
        this.attributeType = attributeType;
        this.buffer = buffer;
        count = buffer.remaining() / attributeType.getSize();
        id = GL15.glGenBuffers();
    }

    public AttributeBuffer(@Nonnull final AttributeType attributeType, @Nonnull final float[] array) {
        this(attributeType, toBuffer(array));
    }

    @Nonnull
    private static FloatBuffer toBuffer(@Nonnull final float[] array) {
        final FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
        buffer.put(array);
        buffer.flip();
        return buffer;
    }

    @Nonnull
    public AttributeType getAttributeType() {
        return attributeType;
    }

    @Nonnull
    public FloatBuffer getBuffer() {
        return buffer;
    }

    public int getCount() {
        return count;
    }

    public void bind() {
        GL15.glBindBuffer(attributeType.getType(), id);
        GL15.glBufferData(attributeType.getType(), buffer, attributeType.getUsage());
        GL20.glVertexAttribPointer(attributeType.getIndex(), attributeType.getSize(), GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(attributeType.getType(), 0);
    }

    @Override
    public void close() {
        GL15.glDeleteBuffers(id);
    }
}

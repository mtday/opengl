package opengl.game.model;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;

public class AttributeBuffer {
    private final int id;
    private final AttributeType attributeType;
    private final FloatBuffer buffer;
    private final int count;

    public AttributeBuffer(AttributeType attributeType, FloatBuffer buffer) {
        this.attributeType = attributeType;
        this.buffer = buffer;
        count = buffer.remaining() / attributeType.getSize();
        id = GL15.glGenBuffers();
    }

    public AttributeBuffer(AttributeType attributeType, float[] array) {
        this(attributeType, toBuffer(array));
    }

    private static FloatBuffer toBuffer(float[] array) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
        buffer.put(array);
        buffer.flip();
        return buffer;
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public FloatBuffer getBuffer() {
        return buffer;
    }

    public int getCount() {
        return count;
    }

    public void start() {
        GL15.glBindBuffer(attributeType.getType(), id);
        GL15.glBufferData(attributeType.getType(), buffer, attributeType.getUsage());
        GL20.glVertexAttribPointer(attributeType.getIndex(), attributeType.getSize(), GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(attributeType.getType(), 0);
        GL20.glEnableVertexAttribArray(attributeType.getIndex());
    }

    public void stop() {
        GL20.glDisableVertexAttribArray(attributeType.getIndex());
    }

    public void close() {
        GL15.glDeleteBuffers(id);
    }
}

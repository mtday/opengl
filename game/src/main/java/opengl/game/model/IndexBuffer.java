package opengl.game.model;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import java.nio.IntBuffer;

public class IndexBuffer {
    private final int id;
    private final IntBuffer buffer;
    private final int count;

    public IndexBuffer(IntBuffer buffer) {
        this.buffer = buffer;
        count = buffer.remaining();
        id = GL15.glGenBuffers();
    }

    public IndexBuffer(int[] array) {
        this(toBuffer(array));
    }

    public int getId() {
        return id;
    }

    public IntBuffer getBuffer() {
        return buffer;
    }

    public int getCount() {
        return count;
    }

    private static IntBuffer toBuffer(int[] array) {
        IntBuffer buffer = BufferUtils.createIntBuffer(array.length);
        buffer.put(array);
        buffer.flip();
        return buffer;
    }

    public void start() {
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, id);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    public void stop() {
    }

    public void close() {
        GL15.glDeleteBuffers(id);
    }
}

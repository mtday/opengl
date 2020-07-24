package opengl.game.model;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public final class Model {
    private final int id;
    private final IndexBuffer indexBuffer;
    private final Map<AttributeType, AttributeBuffer> attributeBuffers;

    public Model(IndexBuffer indexBuffer, Map<AttributeType, AttributeBuffer> attributeBuffers) {
        this.indexBuffer = indexBuffer;
        this.attributeBuffers = attributeBuffers;
        id = GL30.glGenVertexArrays();
    }

    public void start() {
        GL30.glBindVertexArray(id);
        indexBuffer.start();
        attributeBuffers.values().forEach(AttributeBuffer::start);
    }

    public void render() {
        GL11.glDrawElements(GL11.GL_TRIANGLES, indexBuffer.getCount(), GL11.GL_UNSIGNED_INT, 0);
    }

    public void stop() {
        indexBuffer.stop();
        attributeBuffers.values().forEach(AttributeBuffer::stop);
        GL30.glBindVertexArray(0);
    }

    public void close() {
        indexBuffer.close();
        attributeBuffers.values().forEach(AttributeBuffer::close);
        GL30.glDeleteVertexArrays(id);
    }
}

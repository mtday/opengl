package opengl.game.model;

import static java.util.Objects.requireNonNull;

import opengl.game.texture.Texture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class Model implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Model.class);

    private final int id;
    @Nonnull
    private final IndexBuffer indexBuffer;
    @Nonnull
    private final Map<AttributeType, AttributeBuffer> attributeBuffers;

    @Nullable
    private final Texture texture;

    private Model(
            @Nonnull final IndexBuffer indexBuffer, @Nonnull final Map<AttributeType, AttributeBuffer> attributeBuffers,
            @Nullable final Texture texture) {
        this.indexBuffer = indexBuffer;
        this.attributeBuffers = attributeBuffers;
        this.texture = texture;
        id = GL30.glGenVertexArrays();
    }

    public void bind() {
        GL30.glBindVertexArray(id);
        indexBuffer.bind();
        attributeBuffers.values().forEach(AttributeBuffer::bind);
        if (texture != null) {
            texture.bind();
        }
    }

    public void render() {
        attributeBuffers.keySet().forEach(attributeType -> GL20.glEnableVertexAttribArray(attributeType.getIndex()));
        if (texture != null) {
            texture.render();
        }
        GL11.glDrawElements(GL11.GL_TRIANGLES, indexBuffer.getCount(), GL11.GL_UNSIGNED_INT, 0);
        attributeBuffers.keySet().forEach(attributeType -> GL20.glDisableVertexAttribArray(attributeType.getIndex()));
    }

    @Nonnull
    public IndexBuffer getIndexBuffer() {
        return indexBuffer;
    }

    @Nonnull
    public Map<AttributeType, AttributeBuffer> getAttributeBuffers() {
        return attributeBuffers;
    }

    @Nullable
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void close() {
        indexBuffer.close();
        attributeBuffers.values().forEach(AttributeBuffer::close);
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(id);
    }

    public static class Builder {
        @Nullable
        private IndexBuffer indexBuffer;
        @Nonnull
        private final Map<AttributeType, AttributeBuffer> attributeBuffers = new HashMap<>();
        @Nullable
        private Texture texture;

        @Nonnull
        public Builder withIndexBuffer(@Nonnull final IndexBuffer indexBuffer) {
            this.indexBuffer = indexBuffer;
            return this;
        }

        @Nonnull
        public Builder withAttributeBuffer(@Nonnull final AttributeBuffer attributeBuffer) {
            attributeBuffers.put(attributeBuffer.getAttributeType(), attributeBuffer);
            return this;
        }

        @Nonnull
        public Builder withTexture(@Nonnull final Texture texture) {
            this.texture = texture;
            return this;
        }

        @Nonnull
        public Model build() {
            requireNonNull(indexBuffer, "An index buffer must be provided.");
            return new Model(indexBuffer, attributeBuffers, texture);
        }
    }
}

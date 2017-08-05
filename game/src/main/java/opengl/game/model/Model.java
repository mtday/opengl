package opengl.game.model;

import opengl.game.texture.Texture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class Model implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Model.class);

    private final int id;
    @Nonnull
    private final Obj obj;
    /*
    @Nonnull
    private final ModelType modelType;
    @Nonnull
    private final IndexBuffer indexBuffer;
    @Nonnull
    private final Map<AttributeType, AttributeBuffer> attributeBuffers;
    */

    @Nullable
    private final Texture texture;

    public Model(@Nonnull final ModelType modelType, @Nullable final Texture texture) {
        this.obj = new Obj(modelType);
        //this.modelType = modelType;
        //this.indexBuffer = indexBuffer;
        //this.attributeBuffers = attributeBuffers;
        this.texture = texture;
        id = GL30.glGenVertexArrays();
    }

    public void bind() {
        GL30.glBindVertexArray(id);
        obj.getIndexBuffer().bind();
        obj.getAttributeBuffers().values().forEach(AttributeBuffer::bind);
    }

    public void render() {
        obj.getAttributeBuffers().keySet()
                .forEach(attributeType -> GL20.glEnableVertexAttribArray(attributeType.getIndex()));
        if (texture != null) {
            texture.activate();
        }
        GL11.glDrawElements(GL11.GL_TRIANGLES, obj.getIndexBuffer().getCount(), GL11.GL_UNSIGNED_INT, 0);
        obj.getAttributeBuffers().keySet()
                .forEach(attributeType -> GL20.glDisableVertexAttribArray(attributeType.getIndex()));
    }

    @Nonnull
    public Obj getObj() {
        return obj;
    }

    /*
    @Nonnull
    public ModelType getModelType() {
        return modelType;
    }

    @Nonnull
    public IndexBuffer getIndexBuffer() {
        return indexBuffer;
    }

    @Nonnull
    public Map<AttributeType, AttributeBuffer> getAttributeBuffers() {
        return attributeBuffers;
    }
    */

    @Nullable
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void close() {
        obj.getIndexBuffer().close();
        obj.getAttributeBuffers().values().forEach(AttributeBuffer::close);
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(id);
    }
}

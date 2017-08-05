package opengl.game.entity;

import opengl.game.model.ModelType;
import opengl.game.texture.TextureType;
import org.joml.Matrix4f;

import javax.annotation.Nonnull;

public class Entity {
    @Nonnull
    private final ModelType modelType;
    @Nonnull
    private final TextureType textureType;

    @Nonnull
    private final Matrix4f transformation;

    public Entity(
            @Nonnull final ModelType modelType, @Nonnull final TextureType textureType,
            @Nonnull final Matrix4f transformation) {
        this.modelType = modelType;
        this.textureType = textureType;
        this.transformation = transformation;
    }

    @Nonnull
    public ModelType getModelType() {
        return modelType;
    }

    @Nonnull
    public TextureType getTextureType() {
        return textureType;
    }

    @Nonnull
    public Matrix4f getTransformation() {
        return transformation;
    }
}

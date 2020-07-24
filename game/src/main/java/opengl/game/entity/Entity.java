package opengl.game.entity;

import opengl.game.model.ModelType;
import opengl.game.texture.TextureType;
import org.joml.Matrix4f;

public class Entity {
    private final ModelType modelType;
    private final TextureType textureType;

    private final Matrix4f transformation;

    public Entity(ModelType modelType, TextureType textureType, Matrix4f transformation) {
        this.modelType = modelType;
        this.textureType = textureType;
        this.transformation = transformation;
    }

    public ModelType getModelType() {
        return modelType;
    }

    public TextureType getTextureType() {
        return textureType;
    }

    public Matrix4f getTransformation() {
        return transformation;
    }
}

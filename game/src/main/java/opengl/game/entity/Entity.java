package opengl.game.entity;

import opengl.game.model.Model;
import org.joml.Matrix4f;

import java.io.Closeable;

import javax.annotation.Nonnull;

public class Entity implements Closeable {
    @Nonnull
    private final Model model;
    @Nonnull
    private final Matrix4f transformation;

    public Entity(
            @Nonnull final Model model, @Nonnull final Matrix4f transformation) {
        this.model = model;
        this.transformation = transformation;
    }

    @Nonnull
    public Model getModel() {
        return model;
    }

    @Nonnull
    public Matrix4f getTransformation() {
        return transformation;
    }

    public void bind() {
        model.bind();
    }

    public void render() {
        model.render();
    }

    @Override
    public void close() {
    }
}

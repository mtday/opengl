package opengl.game.entity;

import static opengl.game.uniform.UniformType.TRANSFORMATION_MATRIX;

import opengl.game.model.Model;
import opengl.game.shader.Program;
import org.joml.Matrix4f;

import java.io.Closeable;

import javax.annotation.Nonnull;

public class Entity implements Closeable {
    @Nonnull
    private final Model model;
    @Nonnull
    private final Matrix4f transformation;

    public Entity(@Nonnull final Model model) {
        this(model, new Matrix4f().identity().scale(1f));
    }

    public Entity(@Nonnull final Model model, @Nonnull final Matrix4f transformation) {
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

    public void render(@Nonnull final Program program) {
        program.getUniform(TRANSFORMATION_MATRIX).load(transformation);
        model.render();
    }

    @Override
    public void close() {
    }
}

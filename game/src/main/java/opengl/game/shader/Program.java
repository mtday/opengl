package opengl.game.shader;

import opengl.game.model.AttributeType;
import org.lwjgl.opengl.GL20;

import java.util.List;

import javax.annotation.Nonnull;

public abstract class Program implements AutoCloseable {
    private final int id;
    @Nonnull
    private final List<Shader> shaders;

    public Program(@Nonnull final List<Shader> shaders) {
        id = GL20.glCreateProgram();
        this.shaders = shaders;
    }

    public int getId() {
        return id;
    }

    public abstract void bindAttributes();

    protected void bindAttribute(@Nonnull final AttributeType attributeType) {
        GL20.glBindAttribLocation(id, attributeType.getIndex(), attributeType.getVariableName());
    }

    public void start() {
        for (final Shader shader : shaders) {
            GL20.glAttachShader(id, shader.getId());
        }
        GL20.glLinkProgram(id);
        bindAttributes();
        GL20.glValidateProgram(id);
        GL20.glUseProgram(id);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    @Override
    public void close() {
        stop();
        for (final Shader shader : shaders) {
            GL20.glDetachShader(id, shader.getId());
            GL20.glDeleteShader(shader.getId());
        }
        GL20.glDeleteProgram(id);
    }
}

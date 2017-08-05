package opengl.game.shader;

import opengl.game.entity.EntityManager;
import opengl.game.model.AttributeType;
import opengl.game.render.Renderer;
import org.lwjgl.opengl.GL20;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

public abstract class Program implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Program.class);

    private final int id;
    @Nonnull
    private final Map<ShaderType, Shader> shaders;

    @Nonnull
    private final Renderer renderer;

    public Program(
            @Nonnull final EntityManager entityManager, @Nonnull final Projection projection,
            @Nonnull final Camera camera, @Nonnull final Light light, @Nonnull final List<ShaderType> shaderTypes,
            @Nonnull final List<AttributeType> attributeTypes) {
        id = GL20.glCreateProgram();

        shaders = new HashMap<>();
        shaderTypes.stream().map(type -> new Shader(id, type))
                .forEach(shader -> shaders.put(shader.getShaderType(), shader));

        attributeTypes.forEach(attributeType -> GL20
                .glBindAttribLocation(id, attributeType.getIndex(), attributeType.getVariableName()));

        GL20.glLinkProgram(id);
        GL20.glValidateProgram(id);

        renderer = new Renderer(id, entityManager, projection, camera, light);
    }

    public void start() {
        GL20.glUseProgram(id);
        renderer.start();
    }

    public void render() {
        renderer.render();
    }

    public void stop() {
        renderer.stop();
        GL20.glUseProgram(0);
    }

    @Override
    public void close() {
        renderer.close();
        shaders.values().forEach(Shader::close);
        GL20.glDeleteProgram(id);
    }
}

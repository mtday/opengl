package opengl.game.shader;

import opengl.game.entity.EntityManager;
import opengl.game.model.AttributeType;
import opengl.game.render.Renderer;
import org.lwjgl.opengl.GL20;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Program implements AutoCloseable {
    private final int id;
    private final Map<ShaderType, Shader> shaders;

    private final Renderer renderer;

    public Program(EntityManager entityManager,
                   Projection projection,
                   Camera camera,
                   Light light,
                   List<ShaderType> shaderTypes,
                   List<AttributeType> attributeTypes) {
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

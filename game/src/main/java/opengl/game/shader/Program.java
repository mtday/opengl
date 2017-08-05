package opengl.game.shader;

import static java.util.Optional.ofNullable;

import opengl.game.model.AttributeType;
import opengl.game.uniform.UniformManager;
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
    private final List<AttributeType> attributeTypes;

    @Nonnull
    private final UniformManager uniformManager;

    public Program(
            @Nonnull final Projection projection, @Nonnull final Camera camera, @Nonnull final Light light,
            @Nonnull final List<ShaderType> shaderTypes, @Nonnull final List<AttributeType> attributeTypes) {
        id = GL20.glCreateProgram();

        this.shaders = new HashMap<>();
        shaderTypes.stream().map(type -> new Shader(id, type))
                .forEach(shader -> shaders.put(shader.getShaderType(), shader));

        this.attributeTypes = attributeTypes;
        attributeTypes.forEach(attributeType ->
                GL20.glBindAttribLocation(id, attributeType.getIndex(), attributeType.getVariableName()));

        GL20.glLinkProgram(id);
        GL20.glValidateProgram(id);

        uniformManager = new UniformManager(id, projection, camera, light);
    }

    public int getId() {
        return id;
    }

    @Nonnull
    public Map<ShaderType, Shader> getShaders() {
        return shaders;
    }

    @Nonnull
    public Shader getShader(@Nonnull final ShaderType shaderType) {
        return ofNullable(shaders.get(shaderType))
                .orElseThrow(() -> new IllegalArgumentException("Shader type not found: " + shaderType));
    }

    @Nonnull
    public List<AttributeType> getAttributeTypes() {
        return attributeTypes;
    }

    @Nonnull
    public UniformManager getUniformManager() {
        return uniformManager;
    }

    public void start() {
        GL20.glUseProgram(id);

        uniformManager.loadViewMatrix();
        uniformManager.loadLightPosition();
        uniformManager.loadLightColor();
        uniformManager.loadAmbientLight();
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    @Override
    public void close() {
        shaders.values().forEach(Shader::close);
        GL20.glDeleteProgram(id);
    }
}

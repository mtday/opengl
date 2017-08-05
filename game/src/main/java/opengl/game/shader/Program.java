package opengl.game.shader;

import static java.util.Optional.ofNullable;

import opengl.game.model.AttributeType;
import opengl.game.uniform.Uniform;
import opengl.game.uniform.UniformType;
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
    private final Map<UniformType, Uniform> uniforms;

    public Program(
            @Nonnull final List<ShaderType> shaderTypes, @Nonnull final List<AttributeType> attributeTypes,
            @Nonnull final List<UniformType> uniformTypes) {
        id = GL20.glCreateProgram();

        this.shaders = new HashMap<>();
        shaderTypes.stream().map(type -> new Shader(id, type))
                .forEach(shader -> shaders.put(shader.getShaderType(), shader));

        this.attributeTypes = attributeTypes;
        attributeTypes.forEach(attributeType ->
            GL20.glBindAttribLocation(id, attributeType.getIndex(), attributeType.getVariableName()));

        GL20.glLinkProgram(id);
        GL20.glValidateProgram(id);

        uniforms = new HashMap<>();
        uniformTypes.stream().map(type -> new Uniform(id, type))
                .forEach(uniform -> uniforms.put(uniform.getUniformType(), uniform));
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
    public Map<UniformType, Uniform> getUniforms() {
        return uniforms;
    }

    @Nonnull
    public Uniform getUniform(@Nonnull final UniformType uniformType) {
        return ofNullable(uniforms.get(uniformType))
                .orElseThrow(() -> new IllegalArgumentException("Uniform type not found: " + uniformType));
    }

    public void start() {
        GL20.glUseProgram(id);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    @Override
    public void close() {
        shaders.values().forEach(Shader::close);
        uniforms.values().forEach(Uniform::close);
        GL20.glDeleteProgram(id);
    }
}

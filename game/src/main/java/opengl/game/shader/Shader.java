package opengl.game.shader;

import static java.nio.charset.StandardCharsets.UTF_8;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.annotation.Nonnull;

public class Shader implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Shader.class);

    private final int programId;
    private final int id;
    @Nonnull
    private final ShaderType shaderType;

    public Shader(final int programId, @Nonnull final ShaderType shaderType) {
        this.programId = programId;
        this.shaderType = shaderType;
        id = load(shaderType);
        GL20.glAttachShader(programId, id);
    }

    public int getProgramId() {
        return programId;
    }

    public int getId() {
        return id;
    }

    @Nonnull
    public ShaderType getShaderType() {
        return shaderType;
    }

    private int load(@Nonnull final ShaderType shaderType) {
        final StringBuilder source = new StringBuilder();
        final String resource = shaderType.getResource();
        try (final InputStream inputStream = Shader.class.getClassLoader().getResourceAsStream(resource);
             final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, UTF_8);
             final BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                source.append(line);
                source.append("\n");
            }
        } catch (final IOException ioException) {
            throw new RuntimeException("Failed to read shader resource: " + shaderType.getResource(), ioException);
        }
        final int shaderId = GL20.glCreateShader(shaderType.getType());
        GL20.glShaderSource(shaderId, source);
        GL20.glCompileShader(shaderId);
        if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            final String error = GL20.glGetShaderInfoLog(shaderId);
            throw new RuntimeException("Failed to load shader from " + shaderType.getResource() + ": " + error);
        }
        return shaderId;
    }

    @Override
    public void close() {
        GL20.glDetachShader(programId, id);
        GL20.glDeleteShader(id);
    }
}

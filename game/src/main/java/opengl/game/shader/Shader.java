package opengl.game.shader;

import static java.nio.charset.StandardCharsets.UTF_8;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.annotation.Nonnull;

public class Shader {
    private static final Logger LOGGER = LoggerFactory.getLogger(Shader.class);

    private final int id;

    public Shader(@Nonnull final ShaderType type) throws IOException {
        id = load(type);
    }

    public int getId() {
        return id;
    }

    private int load(@Nonnull final ShaderType type) throws IOException {
        final StringBuilder source = new StringBuilder();
        try (final InputStream inputStream = Shader.class.getClassLoader().getResourceAsStream(type.getResource());
             final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, UTF_8);
             final BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                source.append(line);
                source.append("\n");
            }
        }
        final int shaderId = GL20.glCreateShader(type.getType());
        GL20.glShaderSource(shaderId, source);
        GL20.glCompileShader(shaderId);
        if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            final String error = GL20.glGetShaderInfoLog(shaderId);
            throw new IOException("Failed to load shader from resource " + type.getResource() + ": " + error);
        }
        return shaderId;
    }
}

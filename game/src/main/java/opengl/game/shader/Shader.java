package opengl.game.shader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Shader implements Closeable {
    private final int programId;
    private final int id;
    private final ShaderType shaderType;

    public Shader(int programId, ShaderType shaderType) {
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

    public ShaderType getShaderType() {
        return shaderType;
    }

    private int load(ShaderType shaderType) {
        StringBuilder source = new StringBuilder();
        String resource = shaderType.getResource();
        try (InputStream inputStream = Shader.class.getClassLoader().getResourceAsStream(resource)) {
            if (inputStream == null) {
                throw new IOException("Shader type " + shaderType + " resource not found: " + resource);
            }
             try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, UTF_8);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                 String line;
                 while ((line = bufferedReader.readLine()) != null) {
                     source.append(line);
                     source.append("\n");
                 }
             }
        } catch (IOException ioException) {
            throw new RuntimeException("Failed to read shader resource: " + shaderType.getResource(), ioException);
        }
        int shaderId = GL20.glCreateShader(shaderType.getType());
        GL20.glShaderSource(shaderId, source);
        GL20.glCompileShader(shaderId);
        if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            String error = GL20.glGetShaderInfoLog(shaderId);
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

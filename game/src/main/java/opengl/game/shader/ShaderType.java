package opengl.game.shader;

import org.lwjgl.opengl.GL20;

import javax.annotation.Nonnull;

public enum ShaderType {
    VERTEX_SHADER("shader/vertex.glsl", GL20.GL_VERTEX_SHADER),
    FRAGMENT_SHADER("shader/fragment.glsl", GL20.GL_FRAGMENT_SHADER);

    @Nonnull
    private final String resource;
    private final int type;

    ShaderType(@Nonnull final String resource, final int type) {
        this.resource = resource;
        this.type = type;
    }

    @Nonnull
    public String getResource() {
        return resource;
    }

    public int getType() {
        return type;
    }
}

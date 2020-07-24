package opengl.game.shader;

import org.lwjgl.opengl.GL20;

public enum ShaderType {
    VERTEX_SHADER("shader/vertex.glsl", GL20.GL_VERTEX_SHADER),
    FRAGMENT_SHADER("shader/fragment.glsl", GL20.GL_FRAGMENT_SHADER);

    private final String resource;
    private final int type;

    ShaderType(String resource, int type) {
        this.resource = resource;
        this.type = type;
    }

    public String getResource() {
        return resource;
    }

    public int getType() {
        return type;
    }
}

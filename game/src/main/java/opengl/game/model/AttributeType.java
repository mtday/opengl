package opengl.game.model;

import org.lwjgl.opengl.GL15;

public enum AttributeType {
    VERTEX_ATTRIBUTE(0, GL15.GL_ARRAY_BUFFER, GL15.GL_STATIC_DRAW, 3, "position"),
    TEXTURE_ATTRIBUTE(1, GL15.GL_ARRAY_BUFFER, GL15.GL_STATIC_DRAW, 2, "texture"),
    NORMAL_ATTRIBUTE(2, GL15.GL_ARRAY_BUFFER, GL15.GL_STATIC_DRAW, 3, "normal");

    private final int index;
    private final int type;
    private final int usage;
    private final int size;
    private final String variableName;

    AttributeType(int index, int type, int usage, int size, String variableName) {
        this.index = index;
        this.type = type;
        this.usage = usage;
        this.size = size;
        this.variableName = variableName;
    }

    public int getIndex() {
        return index;
    }

    public int getType() {
        return type;
    }

    public int getUsage() {
        return usage;
    }

    public int getSize() {
        return size;
    }

    public String getVariableName() {
        return variableName;
    }
}

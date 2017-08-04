package opengl.game.model;

import org.lwjgl.opengl.GL15;

import javax.annotation.Nonnull;

public enum AttributeType {
    VERTEX(0, GL15.GL_ARRAY_BUFFER, GL15.GL_STATIC_DRAW, 3, "position"),
    TEXTURE(1, GL15.GL_ARRAY_BUFFER, GL15.GL_STATIC_DRAW, 2, "texture");

    private final int index;
    private final int type;
    private final int usage;
    private final int size;
    @Nonnull
    private final String variableName;

    AttributeType(
            final int index, final int type, final int usage, final int size, @Nonnull final String variableName) {
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

    @Nonnull
    public String getVariableName() {
        return variableName;
    }
}

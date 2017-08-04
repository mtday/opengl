package opengl.game.texture;

import org.lwjgl.opengl.GL13;

import javax.annotation.Nonnull;

public enum TextureType {
    TEXTURE(GL13.GL_TEXTURE0, "texture/texture.png");

    private final int index;
    @Nonnull
    private final String resource;

    TextureType(final int index, @Nonnull final String resource) {
        this.index = index;
        this.resource = resource;
    }

    public int getIndex() {
        return index;
    }

    @Nonnull
    public String getResource() {
        return resource;
    }
}

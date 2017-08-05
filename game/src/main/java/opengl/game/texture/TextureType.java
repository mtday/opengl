package opengl.game.texture;

import org.lwjgl.opengl.GL13;

import javax.annotation.Nonnull;

public enum TextureType {
    STALL_TEXTURE(GL13.GL_TEXTURE0, "texture/stall.png"),
    //EXAMPLE_TEXTURE(GL13.GL_TEXTURE1, "texture/example.png"),
    ;

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

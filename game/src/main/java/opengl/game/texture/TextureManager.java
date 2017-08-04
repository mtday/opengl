package opengl.game.texture;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

public class TextureManager implements AutoCloseable {
    @Nonnull
    private final Map<TextureType, Texture> textures;

    public TextureManager() throws IOException {
        textures = new HashMap<>();

        for (final TextureType textureType : TextureType.values()) {
            textures.put(textureType, new Texture(textureType));
        }
    }

    @Nonnull
    public Texture getTexture(@Nonnull final TextureType textureType) {
        return textures.get(textureType);
    }

    @Override
    public void close() {
        for (final Texture texture : textures.values()) {
            texture.close();
        }
    }
}

package opengl.game.texture;

import static java.util.Arrays.stream;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

public class TextureManager {
    @Nonnull
    private final Map<TextureType, Texture> textures;

    public TextureManager() {
        textures = new HashMap<>();
        stream(TextureType.values()).forEach(textureType -> textures.put(textureType, new Texture(textureType)));
    }

    @Nonnull
    public Texture getTexture(@Nonnull final TextureType textureType) {
        return textures.get(textureType);
    }
}

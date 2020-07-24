package opengl.game.texture;

import static java.util.Arrays.stream;

import java.util.HashMap;
import java.util.Map;

public class TextureManager {
    private final Map<TextureType, Texture> textures;

    public TextureManager() {
        textures = new HashMap<>();
        stream(TextureType.values()).forEach(textureType -> textures.put(textureType, new Texture(textureType)));
    }

    public Texture getTexture(TextureType textureType) {
        return textures.get(textureType);
    }

    public void close() {
        textures.values().forEach(Texture::close);
    }
}

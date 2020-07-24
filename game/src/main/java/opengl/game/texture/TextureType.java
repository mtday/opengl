package opengl.game.texture;

import org.lwjgl.opengl.GL13;

public enum TextureType {
    STALL_TEXTURE(GL13.GL_TEXTURE0, "texture/stall.png", 1, 0),
    EXAMPLE_TEXTURE(GL13.GL_TEXTURE1, "texture/example.png", 1f, 0.1f),
    ;

    private final int index;
    private final String resource;
    private final float shineDampener;
    private final float reflectivity;

    TextureType(int index, String resource, float shineDampener, float reflectivity) {
        this.index = index;
        this.resource = resource;
        this.shineDampener = shineDampener;
        this.reflectivity = reflectivity;
    }

    public int getIndex() {
        return index;
    }

    public String getResource() {
        return resource;
    }

    public float getShineDampener() {
        return shineDampener;
    }

    public float getReflectivity() {
        return reflectivity;
    }
}

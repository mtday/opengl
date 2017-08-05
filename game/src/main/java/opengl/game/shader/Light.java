package opengl.game.shader;

import org.joml.Vector3f;

import javax.annotation.Nonnull;

public class Light {
    @Nonnull
    private final Vector3f position;
    @Nonnull
    private final Vector3f color;
    private final float ambientLight;

    public Light(@Nonnull final Vector3f position, @Nonnull final Vector3f color, final float ambientLight) {
        this.position = position;
        this.color = color;
        this.ambientLight = ambientLight;
    }

    @Nonnull
    public Vector3f getPosition() {
        return position;
    }

    @Nonnull
    public Vector3f getColor() {
        return color;
    }

    public float getAmbientLight() {
        return ambientLight;
    }
}

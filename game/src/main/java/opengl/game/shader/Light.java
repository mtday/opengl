package opengl.game.shader;

import org.joml.Vector3f;

import javax.annotation.Nonnull;

public class Light {
    @Nonnull
    private final Vector3f position;
    @Nonnull
    private final Vector3f color;

    public Light(@Nonnull final Vector3f position, @Nonnull final Vector3f color) {
        this.position = position;
        this.color = color;
    }

    @Nonnull
    public Vector3f getPosition() {
        return position;
    }

    @Nonnull
    public Vector3f getColor() {
        return color;
    }
}

package opengl.game.shader;

import org.joml.Vector3f;

public class Light {
    private final Vector3f position;
    private final Vector3f color;
    private final float ambientLight;

    public Light(Vector3f position, Vector3f color, float ambientLight) {
        this.position = position;
        this.color = color;
        this.ambientLight = ambientLight;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getColor() {
        return color;
    }

    public float getAmbientLight() {
        return ambientLight;
    }
}

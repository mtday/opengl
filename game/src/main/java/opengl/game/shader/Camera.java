package opengl.game.shader;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
    private Vector3f position = new Vector3f();
    private float roll;
    private float pitch;
    private float yaw;

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getRoll() {
        return roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public Matrix4f getView() {
        Matrix4f viewMatrix = new Matrix4f().identity();
        viewMatrix.rotate(pitch, new Vector3f(1, 0, 0));
        viewMatrix.rotate(yaw, new Vector3f(0, 1, 0));
        viewMatrix.rotate(roll, new Vector3f(0, 0, 1));
        viewMatrix.translate(-position.x, -position.y, -position.z);
        return viewMatrix;
    }
}

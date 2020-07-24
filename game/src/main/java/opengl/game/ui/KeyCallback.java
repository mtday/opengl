package opengl.game.ui;

import opengl.game.shader.Camera;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

public class KeyCallback implements GLFWKeyCallbackI {
    private static final float MOVEMENT_DELTA = 1f;

    private final Camera camera;

    public KeyCallback(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void invoke(long windowHandle, int key, int scanCode, int action, int modes) {
        if (action != GLFW.GLFW_RELEASE) {
            if (key == GLFW.GLFW_KEY_W) {
                camera.getPosition().add(0, 0, -MOVEMENT_DELTA);
            } else if (key == GLFW.GLFW_KEY_S) {
                camera.getPosition().add(0, 0, MOVEMENT_DELTA);
            } else if (key == GLFW.GLFW_KEY_A) {
                camera.getPosition().add(-MOVEMENT_DELTA, 0, 0);
            } else if (key == GLFW.GLFW_KEY_D) {
                camera.getPosition().add(MOVEMENT_DELTA, 0, 0);
            }
        } else if (key == GLFW.GLFW_KEY_ESCAPE) {
            GLFW.glfwSetWindowShouldClose(windowHandle, true);
        }
    }
}

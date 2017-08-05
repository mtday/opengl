package opengl.game.ui;

import opengl.game.shader.Camera;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

public class KeyCallback implements GLFWKeyCallbackI {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeyCallback.class);

    @Nonnull
    private final Camera camera;

    public KeyCallback(@Nonnull final Camera camera) {
        this.camera = camera;
    }

    @Override
    public void invoke(final long windowHandle, final int key, final int scanCode, final int action, final int modes) {
        if (action != GLFW.GLFW_RELEASE) {
            if (key == GLFW.GLFW_KEY_W) {
                camera.getPosition().add(0, 0, -0.02f);
            } else if (key == GLFW.GLFW_KEY_S) {
                camera.getPosition().add(0, 0, 0.02f);
            } else if (key == GLFW.GLFW_KEY_A) {
                camera.getPosition().add(-0.02f, 0, 0);
            } else if (key == GLFW.GLFW_KEY_D) {
                camera.getPosition().add(0.02f, 0, 0);
            }
        } else if (key == GLFW.GLFW_KEY_ESCAPE) {
            GLFW.glfwSetWindowShouldClose(windowHandle, true);
        }
    }
}

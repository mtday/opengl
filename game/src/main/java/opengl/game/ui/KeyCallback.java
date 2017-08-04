package opengl.game.ui;

import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyCallback implements GLFWKeyCallbackI {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeyCallback.class);

    @Override
    public void invoke(final long windowHandle, final int key, final int scanCode, final int action, final int modes) {
        LOGGER.info("Key Callback: {}", key);
    }
}

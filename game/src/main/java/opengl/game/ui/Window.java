package opengl.game.ui;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import java.io.Closeable;

import static org.lwjgl.glfw.GLFW.*;

public class Window implements Closeable {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final String TITLE = "Game";

    private final long windowHandle;

    public Window(KeyCallback keyCallback) {
        // Create the error callback.
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            throw new IllegalArgumentException("Failed to create game window.");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // The window will stay hidden after creation.
        GLFW.glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // The window will be resizable.
        GLFW.glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        GLFW.glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
        GLFW.glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE); // Use the core profile for modern opengl.
        GLFW.glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);

        windowHandle = GLFW.glfwCreateWindow(WIDTH, HEIGHT, TITLE, MemoryUtil.NULL, MemoryUtil.NULL);
        if (windowHandle == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        GLFW.glfwSetKeyCallback(windowHandle, keyCallback);

        // Make the OpenGL context current.
        GLFW.glfwMakeContextCurrent(windowHandle);
        // Enable v-sync.
        GLFW.glfwSwapInterval(1);

        // Make the window visible.
        GLFW.glfwShowWindow(windowHandle);

        // This line is critical for LWJGL's inter-operation with GLFW's OpenGL context, or any context that is
        // managed externally. LWJGL detects the context that is current in the current thread, creates the
        // GLCapabilities instance and makes the OpenGL bindings available for use.
        GL.createCapabilities();

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);

        clear();
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(windowHandle);
    }

    public void clear() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(1, 1, 1, 1);
    }

    public void update() {
        GLFW.glfwSwapBuffers(windowHandle); // Swap the color buffers.

        // Poll for window events. The key callback above will only be invoked during this call.
        GLFW.glfwPollEvents();
    }

    @Override
    public void close() {
        // Free the window callbacks and destroy the window
        Callbacks.glfwFreeCallbacks(windowHandle);
        GLFW.glfwDestroyWindow(windowHandle);

        // Terminate GLFW and free the error callback
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }
}

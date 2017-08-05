package opengl.game;

import static opengl.game.model.ModelType.STALL_MODEL;
import static opengl.game.texture.TextureType.STALL_TEXTURE;

import opengl.game.entity.Entity;
import opengl.game.model.Model;
import opengl.game.shader.Camera;
import opengl.game.shader.Program;
import opengl.game.shader.ProgramStatic;
import opengl.game.shader.Projection;
import opengl.game.texture.TextureManager;
import opengl.game.ui.KeyCallback;
import opengl.game.ui.Window;
import org.joml.Matrix4f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void run() {
        final Camera camera = new Camera();
        final KeyCallback keyCallback = new KeyCallback(camera);
        final Window window = new Window(keyCallback);

        final Projection projection = new Projection(window.getWidth(), window.getHeight());
        final Program programStatic = new ProgramStatic(projection, camera);
        final TextureManager textureManager = new TextureManager();

        final Model model = new Model(STALL_MODEL, textureManager.getTexture(STALL_TEXTURE));

        final Entity entity = new Entity(model, new Matrix4f().translate(0, 0, -20f));

        new GameLoop().run(now -> {
            window.clear();
            programStatic.start();

            entity.getTransformation().rotate((float) Math.toRadians(1), 0, 1.5f, 0);
            programStatic.getUniformManager().loadTransformationMatrix(entity);

            entity.bind();
            entity.render();

            programStatic.stop();
            window.update();
            return !window.shouldClose();
        });

        entity.close();
    }

    public static void main(@Nullable final String... args) {
        Main.run();
    }
}

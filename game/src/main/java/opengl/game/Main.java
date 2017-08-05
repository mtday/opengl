package opengl.game;

import static opengl.game.model.ModelType.DRAGON_MODEL;
import static opengl.game.texture.TextureType.EXAMPLE_TEXTURE;

import opengl.game.entity.Entity;
import opengl.game.entity.EntityManager;
import opengl.game.shader.Camera;
import opengl.game.shader.Light;
import opengl.game.shader.Program;
import opengl.game.shader.ProgramStatic;
import opengl.game.shader.Projection;
import opengl.game.ui.KeyCallback;
import opengl.game.ui.Window;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import javax.annotation.Nullable;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void run() {
        final Camera camera = new Camera();
        final KeyCallback keyCallback = new KeyCallback(camera);
        final Window window = new Window(keyCallback);

        final Projection projection = new Projection(window.getWidth(), window.getHeight());
        final Light light = new Light(new Vector3f(0, 0, -15), new Vector3f(1, 1, 1), 0.2f);

        final EntityManager entityManager = new EntityManager();
        final Program programStatic = new ProgramStatic(entityManager, projection, camera, light);

        final Random random = new Random(1L);
        for (int i = 0; i < 30; i++) {
            final float tx = (random.nextFloat() * 100) - 50;
            final float ty = (random.nextFloat() * 100) - 50;
            final float tz = random.nextFloat() * -100 - 5;
            final float r = (float) (random.nextFloat() * 2 * Math.PI);
            entityManager.add(new Entity(DRAGON_MODEL, EXAMPLE_TEXTURE,
                    new Matrix4f().translate(tx, ty, tz).rotate(r, 1, 1, 1)));
        }

        new GameLoop().run(now -> {
            window.clear();

            //entity.getTransformation().rotate((float) Math.toRadians(0.3), 0f, 1f, 0f);

            programStatic.start();
            programStatic.render();
            programStatic.stop();
            window.update();
            return !window.shouldClose();
        });

        programStatic.close();
    }

    public static void main(@Nullable final String... args) {
        Main.run();
    }
}

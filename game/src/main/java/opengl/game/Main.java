package opengl.game;

import opengl.game.entity.Entity;
import opengl.game.entity.EntityManager;
import opengl.game.shader.*;
import opengl.game.ui.KeyCallback;
import opengl.game.ui.Window;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.Random;

import static opengl.game.model.ModelType.DRAGON_MODEL;
import static opengl.game.texture.TextureType.EXAMPLE_TEXTURE;

public class Main {
    public static void run() {
        Camera camera = new Camera();
        KeyCallback keyCallback = new KeyCallback(camera);
        Window window = new Window(keyCallback);

        Projection projection = new Projection(window.getWidth(), window.getHeight());
        Light light = new Light(new Vector3f(0, 0, -15), new Vector3f(1, 1, 1), 0.2f);

        EntityManager entityManager = new EntityManager();
        Program programStatic = new ProgramStatic(entityManager, projection, camera, light);

        Random random = new Random(1L);
        for (int i = 0; i < 30; i++) {
            float tx = (random.nextFloat() * 100) - 50;
            float ty = (random.nextFloat() * 100) - 50;
            float tz = random.nextFloat() * -100 - 5;
            float r = (float) (random.nextFloat() * 2 * Math.PI);
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

    public static void main(String... args) {
        Main.run();
    }
}

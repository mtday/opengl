package opengl.game;

import static opengl.game.model.AttributeType.TEXTURE;
import static opengl.game.model.AttributeType.VERTEX;

import opengl.game.entity.Entity;
import opengl.game.model.AttributeBuffer;
import opengl.game.model.IndexBuffer;
import opengl.game.model.Model;
import opengl.game.model.ModelType;
import opengl.game.shader.Camera;
import opengl.game.shader.Program;
import opengl.game.shader.ProgramStatic;
import opengl.game.shader.Projection;
import opengl.game.texture.TextureManager;
import opengl.game.texture.TextureType;
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

        final float[] vertices = {
                -0.5f,0.5f,-0.5f, -0.5f,-0.5f,-0.5f, 0.5f,-0.5f,-0.5f, 0.5f,0.5f,-0.5f,
                -0.5f,0.5f,0.5f, -0.5f,-0.5f,0.5f, 0.5f,-0.5f,0.5f, 0.5f,0.5f,0.5f,
                0.5f,0.5f,-0.5f, 0.5f,-0.5f,-0.5f, 0.5f,-0.5f,0.5f, 0.5f,0.5f,0.5f,
                -0.5f,0.5f,-0.5f, -0.5f,-0.5f,-0.5f, -0.5f,-0.5f,0.5f, -0.5f,0.5f,0.5f,
                -0.5f,0.5f,0.5f, -0.5f,0.5f,-0.5f, 0.5f,0.5f,-0.5f, 0.5f,0.5f,0.5f,
                -0.5f,-0.5f,0.5f, -0.5f,-0.5f,-0.5f, 0.5f,-0.5f,-0.5f, 0.5f,-0.5f,0.5f
        };

        final float[] textures = {
                0,0, 0,1, 1,1, 1,0, 0,0, 0,1, 1,1, 1,0, 0,0, 0,1, 1,1, 1,0, 0,0, 0,1, 1,1,
                1,0, 0,0, 0,1, 1,1, 1,0, 0,0, 0,1, 1,1, 1,0
        };

        int[] indices = {
                0,1,3,
                3,1,2,
                4,5,7,
                7,5,6,
                8,9,11,
                11,9,10,
                12,13,15,
                15,13,14,
                16,17,19,
                19,17,18,
                20,21,23,
                23,21,22
        };

        final Model model = new Model.Builder()
                .withModelType(ModelType.TYPE)
                .withIndexBuffer(new IndexBuffer(indices))
                .withAttributeBuffer(new AttributeBuffer(VERTEX, vertices))
                .withAttributeBuffer(new AttributeBuffer(TEXTURE, textures))
                .withTexture(textureManager.getTexture(TextureType.TEXTURE)).build();

        final Entity entity = new Entity(model, new Matrix4f().identity().translate(0f, 0f, -5f));

        new GameLoop().run(now -> {
            window.clear();
            programStatic.start();

            entity.getTransformation().rotate((float) Math.toRadians(1), 1, 1, 0);
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

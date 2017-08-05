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
        final KeyCallback keyCallback = new KeyCallback();
        final Window window = new Window(keyCallback);

        final Camera camera = new Camera();
        final Projection projection = new Projection(window.getWidth(), window.getHeight());
        final Program programStatic = new ProgramStatic(projection, camera);
        final TextureManager textureManager = new TextureManager();

        final int[] indices = new int[] {0, 1, 3, 3, 1, 2};
        final float[] vertices = new float[] {-0.5f, 0.5f, 0f, -0.5f, -0.5f, 0f, 0.5f, -0.5f, 0f, 0.5f, 0.5f, 0f};
        final float[] textures = new float[] {0, 0, 0, 1, 1, 1, 1, 0};

        final Model model = new Model.Builder()
                .withModelType(ModelType.TYPE)
                .withIndexBuffer(new IndexBuffer(indices))
                .withAttributeBuffer(new AttributeBuffer(VERTEX, vertices))
                .withAttributeBuffer(new AttributeBuffer(TEXTURE, textures))
                .withTexture(textureManager.getTexture(TextureType.TEXTURE)).build();

        final Entity entity = new Entity(model, new Matrix4f().identity().translate(0f, 0f, -0.5f));

        new GameLoop().run(now -> {
            window.clear();
            programStatic.start();

            entity.getTransformation().translate(0, 0, -0.1f);
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

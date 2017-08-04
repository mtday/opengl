package opengl.game;

import static opengl.game.model.AttributeType.TEXTURE;
import static opengl.game.model.AttributeType.VERTEX;

import opengl.game.model.AttributeBuffer;
import opengl.game.model.IndexBuffer;
import opengl.game.model.Model;
import opengl.game.shader.Program;
import opengl.game.shader.ProgramStatic;
import opengl.game.texture.TextureManager;
import opengl.game.texture.TextureType;
import opengl.game.ui.KeyCallback;
import opengl.game.ui.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.annotation.Nullable;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void run() throws IOException {
        final KeyCallback keyCallback = new KeyCallback();
        try (final Window window = new Window(keyCallback);
             final Program programStatic = new ProgramStatic();
             final TextureManager textureManager = new TextureManager()) {

            final int[] indices = new int[] {0, 1, 3, 3, 1, 2};
            final float[] vertices = new float[] {-0.5f, 0.5f, 0f, -0.5f, -0.5f, 0f, 0.5f, -0.5f, 0f, 0.5f, 0.5f, 0f};
            final float[] textures = new float[] {0, 0, 0, 1, 1, 1, 1, 0};

            final Model model = new Model.Builder().withIndexBuffer(new IndexBuffer(indices))
                    .withAttributeBuffer(new AttributeBuffer(VERTEX, vertices))
                    .withAttributeBuffer(new AttributeBuffer(TEXTURE, textures))
                    .withTexture(textureManager.getTexture(TextureType.TEXTURE)).build();

            new GameLoop().run(now -> {
                window.clear();
                programStatic.start();

                model.bind();
                model.render();

                programStatic.stop();

                window.update();
                return !window.shouldClose();
            });

            model.close();
        }
    }

    public static void main(@Nullable final String... args) throws IOException {
        Main.run();
    }
}

/*
final int vaoid = GL30.glGenVertexArrays();
final int vertexid = GL15.glGenBuffers();
final int indexid = GL15.glGenBuffers();
try {
    GL30.glBindVertexArray(vaoid);

    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexid);
    GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
    GL20.glVertexAttribPointer(INDEX.getIndex(), 1, GL11.GL_UNSIGNED_INT, false, 0, 0); // TODO: 1?

    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexid);
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);
    GL20.glVertexAttribPointer(VERTEX.getIndex(), 3, GL11.GL_FLOAT, false, 0, 0);
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

    GL20.glEnableVertexAttribArray(VERTEX.getIndex());

    programStatic.start();
    GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_INT, 0);
    programStatic.stop();

    GL20.glDisableVertexAttribArray(VERTEX.getIndex());

    GL30.glBindVertexArray(0);
} finally {
    GL15.glDeleteBuffers(vertexid);
    GL15.glDeleteBuffers(indexid);
    GL30.glDeleteVertexArrays(vaoid);
}
*/

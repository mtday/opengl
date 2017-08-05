package opengl.game.render;

import com.google.common.collect.Multimap;
import opengl.game.entity.Entity;
import opengl.game.entity.EntityManager;
import opengl.game.model.Model;
import opengl.game.model.ModelManager;
import opengl.game.model.ModelType;
import opengl.game.shader.Camera;
import opengl.game.shader.Light;
import opengl.game.shader.Projection;
import opengl.game.texture.Texture;
import opengl.game.texture.TextureManager;
import opengl.game.texture.TextureType;
import opengl.game.uniform.UniformManager;
import org.lwjgl.opengl.GL20;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import javax.annotation.Nonnull;

public class Renderer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Renderer.class);

    @Nonnull
    private final ModelManager modelManager;
    @Nonnull
    private final EntityManager entityManager;
    @Nonnull
    private final TextureManager textureManager;
    @Nonnull
    private final UniformManager uniformManager;
    @Nonnull
    private final Camera camera;
    @Nonnull
    private final Light light;

    public Renderer(
            final int programId, @Nonnull final EntityManager entityManager, @Nonnull final Projection projection,
            @Nonnull final Camera camera, @Nonnull final Light light) {
        this.camera = camera;
        this.light = light;
        this.entityManager = entityManager;

        this.modelManager = new ModelManager();
        this.textureManager = new TextureManager();
        this.uniformManager = new UniformManager(programId);

        GL20.glUseProgram(programId);
        uniformManager.loadProjection(projection);
        GL20.glUseProgram(0);
    }

    public void start() {
        uniformManager.loadCamera(camera);
        uniformManager.loadLight(light);
    }

    public void render() {
        for (final Map.Entry<ModelType, Multimap<TextureType, Entity>> entry : entityManager.getEntities().entrySet()) {
            final Model model = modelManager.getModel(entry.getKey());
            model.start();

            for (final TextureType textureType : entry.getValue().keySet()) {
                final Texture texture = textureManager.getTexture(textureType);
                texture.activate();
                uniformManager.loadTexture(textureType);

                for (final Entity entity : entry.getValue().get(textureType)) {
                    uniformManager.loadEntity(entity);
                    model.render();
                }
            }

            model.stop();
        }
    }

    public void stop() {
    }

    public void close() {
        modelManager.close();
        textureManager.close();
        uniformManager.close();
    }
}

package opengl.game.render;

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

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Renderer {
    private final ModelManager modelManager;
    private final EntityManager entityManager;
    private final TextureManager textureManager;
    private final UniformManager uniformManager;
    private final Camera camera;
    private final Light light;

    public Renderer(int programId,
                    EntityManager entityManager,
                    Projection projection,
                    Camera camera,
                    Light light) {
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
        for (Entry<ModelType, Map<TextureType, List<Entity>>> entry : entityManager.getEntities().entrySet()) {
            Model model = modelManager.getModel(entry.getKey());
            model.start();

            for (TextureType textureType : entry.getValue().keySet()) {
                Texture texture = textureManager.getTexture(textureType);
                texture.activate();
                uniformManager.loadTexture(textureType);

                for (Entity entity : entry.getValue().get(textureType)) {
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

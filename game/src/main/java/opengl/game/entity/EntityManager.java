package opengl.game.entity;

import opengl.game.model.ModelType;
import opengl.game.texture.TextureType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityManager {
    private final Map<ModelType, Map<TextureType, List<Entity>>> entities = new HashMap<>();

    public EntityManager() {
    }

    public void add(Entity entity) {
        entities
                .computeIfAbsent(entity.getModelType(), modelType -> new HashMap<>())
                .computeIfAbsent(entity.getTextureType(), textureType -> new ArrayList<>())
                .add(entity);
    }

    public Map<ModelType, Map<TextureType, List<Entity>>> getEntities() {
        return entities;
    }

    public void close() {
    }
}

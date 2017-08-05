package opengl.game.entity;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import opengl.game.model.ModelType;
import opengl.game.texture.TextureType;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

public class EntityManager {
    @Nonnull
    private final Map<ModelType, Multimap<TextureType, Entity>> entities = new HashMap<>();

    public EntityManager() {
    }

    public void add(@Nonnull final Entity entity) {
        final Multimap<TextureType, Entity> textureMap =
                entities.computeIfAbsent(entity.getModelType(), modelType -> HashMultimap.create());
        textureMap.put(entity.getTextureType(), entity);
    }

    @Nonnull
    public Map<ModelType, Multimap<TextureType, Entity>> getEntities() {
        return entities;
    }

    public void close() {
    }
}

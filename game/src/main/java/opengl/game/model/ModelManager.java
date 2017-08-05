package opengl.game.model;

import static java.util.Arrays.stream;
import static opengl.game.model.ObjLoader.load;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

public class ModelManager {
    @Nonnull
    private final Map<ModelType, Model> models;

    public ModelManager() {
        models = new HashMap<>();
        stream(ModelType.values()).forEach(modelType -> models.put(modelType, load(modelType)));
    }

    @Nonnull
    public Model getModel(@Nonnull final ModelType modelType) {
        return models.get(modelType);
    }

    public void close() {
        models.values().forEach(Model::close);
    }
}

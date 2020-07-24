package opengl.game.model;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static opengl.game.model.ObjLoader.load;

public class ModelManager {
    private final Map<ModelType, Model> models;

    public ModelManager() {
        models = new HashMap<>();
        stream(ModelType.values()).forEach(modelType -> models.put(modelType, load(modelType)));
    }

    public Model getModel(ModelType modelType) {
        return models.get(modelType);
    }

    public void close() {
        models.values().forEach(Model::close);
    }
}

package opengl.game.uniform;

import opengl.game.entity.Entity;
import opengl.game.shader.Camera;
import opengl.game.shader.Light;
import opengl.game.shader.Projection;
import opengl.game.texture.TextureType;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static opengl.game.uniform.UniformType.*;

public class UniformManager {
    private final Map<UniformType, Integer> locations;

    public UniformManager(int programId) {
        locations = new HashMap<>();
        stream(UniformType.values())
                .forEach(type -> locations.put(type, GL20.glGetUniformLocation(programId, type.getVariableName())));
    }

    public void loadEntity(Entity entity) {
        load(locations.get(TRANSFORMATION_MATRIX), entity.getTransformation());
    }

    public void loadProjection(Projection projection) {
        load(locations.get(PROJECTION_MATRIX), projection.getProjection());
    }

    public void loadCamera(Camera camera) {
        load(locations.get(VIEW_MATRIX), camera.getView());
    }

    public void loadLight(Light light) {
        load(locations.get(LIGHT_POSITION), light.getPosition());
        load(locations.get(LIGHT_COLOR), light.getColor());
        load(locations.get(AMBIENT_LIGHT), light.getAmbientLight());
    }

    public void loadTexture(TextureType textureType) {
        load(locations.get(SHINE_DAMPENER), textureType.getShineDampener());
        load(locations.get(REFLECTIVITY), textureType.getReflectivity());
    }

    private void load(int location, Matrix4f value) {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
        value.get(floatBuffer);
        GL20.glUniformMatrix4fv(location, false, floatBuffer);
    }

    private void load(int location, Vector3f value) {
        GL20.glUniform3f(location, value.x, value.y, value.z);
    }

    private void load(int location, float value) {
        GL20.glUniform1f(location, value);
    }

    public void close() {
    }
}

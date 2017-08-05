package opengl.game.uniform;

import static java.util.Arrays.stream;
import static opengl.game.uniform.UniformType.AMBIENT_LIGHT;
import static opengl.game.uniform.UniformType.LIGHT_COLOR;
import static opengl.game.uniform.UniformType.LIGHT_POSITION;
import static opengl.game.uniform.UniformType.PROJECTION_MATRIX;
import static opengl.game.uniform.UniformType.REFLECTIVITY;
import static opengl.game.uniform.UniformType.SHINE_DAMPENER;
import static opengl.game.uniform.UniformType.TRANSFORMATION_MATRIX;
import static opengl.game.uniform.UniformType.VIEW_MATRIX;

import opengl.game.entity.Entity;
import opengl.game.shader.Camera;
import opengl.game.shader.Light;
import opengl.game.shader.Projection;
import opengl.game.texture.TextureType;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

public class UniformManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(UniformManager.class);

    @Nonnull
    private final Map<UniformType, Integer> locations;

    public UniformManager(final int programId) {
        locations = new HashMap<>();
        stream(UniformType.values())
                .forEach(type -> locations.put(type, GL20.glGetUniformLocation(programId, type.getVariableName())));
    }

    public void loadEntity(@Nonnull final Entity entity) {
        load(locations.get(TRANSFORMATION_MATRIX), entity.getTransformation());
    }

    public void loadProjection(@Nonnull final Projection projection) {
        load(locations.get(PROJECTION_MATRIX), projection.getProjection());
    }

    public void loadCamera(@Nonnull final Camera camera) {
        load(locations.get(VIEW_MATRIX), camera.getView());
    }

    public void loadLight(@Nonnull final Light light) {
        load(locations.get(LIGHT_POSITION), light.getPosition());
        load(locations.get(LIGHT_COLOR), light.getColor());
        load(locations.get(AMBIENT_LIGHT), light.getAmbientLight());
    }

    public void loadTexture(@Nonnull final TextureType textureType) {
        load(locations.get(SHINE_DAMPENER), textureType.getShineDampener());
        load(locations.get(REFLECTIVITY), textureType.getReflectivity());
    }

    private void load(final int location, @Nonnull final Matrix4f value) {
        final FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
        value.get(floatBuffer);
        GL20.glUniformMatrix4fv(location, false, floatBuffer);
    }

    private void load(final int location, @Nonnull final Vector3f value) {
        GL20.glUniform3f(location, value.x, value.y, value.z);
    }

    private void load(final int location, final float value) {
        GL20.glUniform1f(location, value);
    }

    public void close() {
    }
}

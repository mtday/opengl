package opengl.game.uniform;

import static java.util.Arrays.stream;
import static opengl.game.uniform.UniformType.PROJECTION_MATRIX;
import static opengl.game.uniform.UniformType.TRANSFORMATION_MATRIX;
import static opengl.game.uniform.UniformType.VIEW_MATRIX;

import opengl.game.entity.Entity;
import opengl.game.shader.Camera;
import opengl.game.shader.Projection;
import org.joml.Matrix4f;
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
    private final Projection projection;
    @Nonnull
    private final Camera camera;

    @Nonnull
    private final Map<UniformType, Integer> locations;

    public UniformManager(final int programId, @Nonnull final Projection projection, @Nonnull final Camera camera) {
        this.camera = camera;
        this.projection = projection;

        locations = new HashMap<>();
        stream(UniformType.values())
                .forEach(type -> locations.put(type, GL20.glGetUniformLocation(programId, type.getVariableName())));

        GL20.glUseProgram(programId);
        loadProjectionMatrix();
        GL20.glUseProgram(0);
    }

    public void loadProjectionMatrix() {
        load(locations.get(PROJECTION_MATRIX), projection.getProjection());
    }

    public void loadViewMatrix() {
        load(locations.get(VIEW_MATRIX), camera.getView());
    }

    public void loadTransformationMatrix(@Nonnull final Entity entity) {
        load(locations.get(TRANSFORMATION_MATRIX), entity.getTransformation());
    }

    private void load(final int location, @Nonnull final Matrix4f value) {
        final FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
        value.get(floatBuffer);
        GL20.glUniformMatrix4fv(location, false, floatBuffer);
    }
}

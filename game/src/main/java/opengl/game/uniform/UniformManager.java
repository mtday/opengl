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
import opengl.game.texture.Texture;
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
import javax.annotation.Nullable;

public class UniformManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(UniformManager.class);

    @Nonnull
    private final Projection projection;
    @Nonnull
    private final Camera camera;
    @Nonnull
    private final Light light;

    @Nonnull
    private final Map<UniformType, Integer> locations;

    public UniformManager(
            final int programId, @Nonnull final Projection projection, @Nonnull final Camera camera,
            @Nonnull final Light light) {
        this.projection = projection;
        this.camera = camera;
        this.light = light;

        locations = new HashMap<>();
        stream(UniformType.values())
                .forEach(type -> locations.put(type, GL20.glGetUniformLocation(programId, type.getVariableName())));

        GL20.glUseProgram(programId);
        loadProjectionMatrix();
        GL20.glUseProgram(0);
    }

    public void loadTransformationMatrix(@Nonnull final Entity entity) {
        load(locations.get(TRANSFORMATION_MATRIX), entity.getTransformation());
    }

    public void loadProjectionMatrix() {
        load(locations.get(PROJECTION_MATRIX), projection.getProjection());
    }

    public void loadViewMatrix() {
        load(locations.get(VIEW_MATRIX), camera.getView());
    }

    public void loadLightPosition() {
        load(locations.get(LIGHT_POSITION), light.getPosition());
    }

    public void loadLightColor() {
        load(locations.get(LIGHT_COLOR), light.getColor());
    }

    public void loadAmbientLight() {
        load(locations.get(AMBIENT_LIGHT), light.getAmbientLight());
    }

    public void loadShineDampener(@Nullable final Texture texture) {
        final float shineDampener = texture == null ? 1f : texture.getTextureType().getShineDampener();
        load(locations.get(SHINE_DAMPENER), shineDampener);
    }

    public void loadReflectivity(@Nullable final Texture texture) {
        final float reflectivity = texture == null ? 1f : texture.getTextureType().getReflectivity();
        load(locations.get(REFLECTIVITY), reflectivity);
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
}

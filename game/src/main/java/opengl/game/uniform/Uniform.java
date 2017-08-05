package opengl.game.uniform;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.nio.FloatBuffer;

import javax.annotation.Nonnull;

public class Uniform implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Uniform.class);

    private final int programId;
    @Nonnull
    private final UniformType uniformType;
    private final int location;

    public Uniform(final int programId, @Nonnull final UniformType uniformType) {
        this.programId = programId;
        this.uniformType = uniformType;
        location = GL20.glGetUniformLocation(programId, uniformType.getVariableName());
    }

    public int getProgramId() {
        return programId;
    }

    @Nonnull
    public UniformType getUniformType() {
        return uniformType;
    }

    public int getLocation() {
        return location;
    }

    @Override
    public void close() {
    }

    public void load(final boolean value) {
        load(value ? 1f : 0f);
    }

    public void load(final float value) {
        GL20.glUniform1f(location, value);
    }

    public void load(@Nonnull final Vector3f value) {
        GL20.glUniform3f(location, value.x, value.y, value.z);
    }

    public void load(@Nonnull final Matrix4f value) {
        final FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
        value.get(floatBuffer);
        GL20.glUniformMatrix4fv(location, false, floatBuffer);
    }
}

package opengl.game.uniform;

import javax.annotation.Nonnull;

public enum UniformType {
    TRANSFORMATION_MATRIX("transformationMatrix");

    @Nonnull
    private final String variableName;

    UniformType(@Nonnull final String variableName) {
        this.variableName = variableName;
    }

    @Nonnull
    public String getVariableName() {
        return variableName;
    }
}

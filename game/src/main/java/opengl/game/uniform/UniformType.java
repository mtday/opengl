package opengl.game.uniform;

public enum UniformType {
    TRANSFORMATION_MATRIX("transformationMatrix"),
    PROJECTION_MATRIX("projectionMatrix"),
    VIEW_MATRIX("viewMatrix"),
    LIGHT_POSITION("lightPosition"),
    LIGHT_COLOR("lightColor"),
    AMBIENT_LIGHT("ambientLight"),
    SHINE_DAMPENER("shineDampener"),
    REFLECTIVITY("reflectivity"),
    ;

    private final String variableName;

    UniformType(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableName() {
        return variableName;
    }
}

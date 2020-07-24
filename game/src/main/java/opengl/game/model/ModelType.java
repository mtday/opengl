package opengl.game.model;

public enum ModelType {
    STALL_MODEL("obj/stall.obj"),
    DRAGON_MODEL("obj/dragon.obj"),
    ;

    private final String resource;

    ModelType(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }
}

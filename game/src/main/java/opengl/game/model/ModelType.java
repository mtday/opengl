package opengl.game.model;

import javax.annotation.Nonnull;

public enum ModelType {
    STALL_MODEL("obj/stall.obj"),
    DRAGON_MODEL("obj/dragon.obj"),
    ;

    @Nonnull
    private final String resource;

    ModelType(@Nonnull final String resource) {
        this.resource = resource;
    }

    @Nonnull
    public String getResource() {
        return resource;
    }
}

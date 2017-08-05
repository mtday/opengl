package opengl.game.shader;

import static java.util.Arrays.asList;
import static opengl.game.model.AttributeType.TEXTURE;
import static opengl.game.model.AttributeType.VERTEX;
import static opengl.game.shader.ShaderType.FRAGMENT_SHADER;
import static opengl.game.shader.ShaderType.VERTEX_SHADER;

import opengl.game.model.AttributeType;

import java.util.List;

import javax.annotation.Nonnull;

public class ProgramStatic extends Program {
    private static final List<ShaderType> SHADER_TYPES = asList(VERTEX_SHADER, FRAGMENT_SHADER);
    private static final List<AttributeType> ATTRIBUTE_TYPES = asList(VERTEX, TEXTURE);

    public ProgramStatic(@Nonnull final Projection projection, @Nonnull final Camera camera) {
        super(projection, camera, SHADER_TYPES, ATTRIBUTE_TYPES);
    }
}

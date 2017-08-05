package opengl.game.shader;

import static java.util.Arrays.asList;
import static opengl.game.model.AttributeType.NORMAL_ATTRIBUTE;
import static opengl.game.model.AttributeType.TEXTURE_ATTRIBUTE;
import static opengl.game.model.AttributeType.VERTEX_ATTRIBUTE;
import static opengl.game.shader.ShaderType.FRAGMENT_SHADER;
import static opengl.game.shader.ShaderType.VERTEX_SHADER;

import opengl.game.entity.EntityManager;
import opengl.game.model.AttributeType;

import java.util.List;

import javax.annotation.Nonnull;

public class ProgramStatic extends Program {
    private static final List<ShaderType> SHADER_TYPES = asList(VERTEX_SHADER, FRAGMENT_SHADER);
    private static final List<AttributeType> ATTRIBUTE_TYPES =
            asList(VERTEX_ATTRIBUTE, TEXTURE_ATTRIBUTE, NORMAL_ATTRIBUTE);

    public ProgramStatic(
            @Nonnull final EntityManager entityManager, @Nonnull final Projection projection,
            @Nonnull final Camera camera, @Nonnull final Light light) {
        super(entityManager, projection, camera, light, SHADER_TYPES, ATTRIBUTE_TYPES);
    }
}

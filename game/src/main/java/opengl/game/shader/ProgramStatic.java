package opengl.game.shader;

import opengl.game.entity.EntityManager;
import opengl.game.model.AttributeType;

import java.util.List;

import static java.util.Arrays.asList;
import static opengl.game.model.AttributeType.*;
import static opengl.game.shader.ShaderType.FRAGMENT_SHADER;
import static opengl.game.shader.ShaderType.VERTEX_SHADER;

public class ProgramStatic extends Program {
    private static final List<ShaderType> SHADER_TYPES = asList(VERTEX_SHADER, FRAGMENT_SHADER);
    private static final List<AttributeType> ATTRIBUTE_TYPES =
            asList(VERTEX_ATTRIBUTE, TEXTURE_ATTRIBUTE, NORMAL_ATTRIBUTE);

    public ProgramStatic(EntityManager entityManager, Projection projection, Camera camera, Light light) {
        super(entityManager, projection, camera, light, SHADER_TYPES, ATTRIBUTE_TYPES);
    }
}

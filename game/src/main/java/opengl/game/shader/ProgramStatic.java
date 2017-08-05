package opengl.game.shader;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static opengl.game.model.AttributeType.TEXTURE;
import static opengl.game.model.AttributeType.VERTEX;
import static opengl.game.shader.ShaderType.FRAGMENT_SHADER;
import static opengl.game.shader.ShaderType.VERTEX_SHADER;
import static opengl.game.uniform.UniformType.TRANSFORMATION_MATRIX;

import opengl.game.model.AttributeType;
import opengl.game.uniform.UniformType;

import java.util.List;

public class ProgramStatic extends Program {
    private static final List<ShaderType> SHADER_TYPES = asList(VERTEX_SHADER, FRAGMENT_SHADER);
    private static final List<AttributeType> ATTRIBUTE_TYPES = asList(VERTEX, TEXTURE);
    private static final List<UniformType> UNIFORM_TYPES = singletonList(TRANSFORMATION_MATRIX);

    public ProgramStatic() {
        super(SHADER_TYPES, ATTRIBUTE_TYPES, UNIFORM_TYPES);
    }
}

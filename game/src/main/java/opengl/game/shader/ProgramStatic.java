package opengl.game.shader;

import static java.util.Arrays.asList;
import static opengl.game.model.AttributeType.TEXTURE;
import static opengl.game.model.AttributeType.VERTEX;
import static opengl.game.shader.ShaderType.FRAGMENT_SHADER;
import static opengl.game.shader.ShaderType.VERTEX_SHADER;

import java.io.IOException;

public class ProgramStatic extends Program {
    public ProgramStatic() throws IOException {
        super(asList(new Shader(VERTEX_SHADER), new Shader(FRAGMENT_SHADER)));
    }

    @Override
    public void bindAttributes() {
        bindAttribute(VERTEX);
        bindAttribute(TEXTURE);
    }
}

#version 410 core

in vec3 position;
in vec2 texture;

out vec2 _texture;

uniform mat4 transformationMatrix;

void main()
{
    gl_Position = transformationMatrix * vec4(position, 1.0);
    _texture = texture;
}

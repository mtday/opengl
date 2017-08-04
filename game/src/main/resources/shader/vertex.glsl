#version 410 core

in vec3 position;
in vec2 texture;

out vec2 _texture;

void main()
{
    gl_Position = vec4(position, 1.0);
    _texture = texture;
}

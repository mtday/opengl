#version 410 core

in vec3 position;
in vec2 texture;

out vec2 _texture;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main()
{
    gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
    _texture = texture;
}

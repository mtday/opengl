#version 410 core

in vec3 position;
in vec2 texture;
in vec3 normal;

out vec2 _texture;
out vec3 _surfaceNormal;
out vec3 _toLight;
out vec3 _toCamera;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition;

void main()
{
    vec4 worldPosition = transformationMatrix * vec4(position, 1.0);

    gl_Position = projectionMatrix * viewMatrix * worldPosition;
    _texture = texture;

    _surfaceNormal = (transformationMatrix * vec4(normal, 0.0)).xyz;
    _toLight = lightPosition - worldPosition.xyz;
    _toCamera = (inverse(viewMatrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPosition.xyz;
}

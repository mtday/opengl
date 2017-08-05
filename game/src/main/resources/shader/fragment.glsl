#version 410 core

in vec2 _texture;
in vec3 _surfaceNormal;
in vec3 _toLight;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec3 lightColor;

void main(void) {
    vec3 surfaceNormalUnit = normalize(_surfaceNormal);
    vec3 toLightUnit = normalize(_toLight);

    float surfaceNormalDotToLight = dot(surfaceNormalUnit, toLightUnit);
    float brightness = max(surfaceNormalDotToLight, 0.2);
    vec3 diffuse = brightness * lightColor;

    out_Color = vec4(diffuse, 1.0) * texture(textureSampler, _texture);
}

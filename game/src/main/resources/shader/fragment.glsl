#version 410 core

in vec2 _texture;
in vec3 _surfaceNormal;
in vec3 _toLight;
in vec3 _toCamera;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec3 lightColor;
uniform float ambientLight;
uniform float shineDampener;
uniform float reflectivity;

void main(void) {
    vec3 surfaceNormalUnit = normalize(_surfaceNormal);
    vec3 toLightUnit = normalize(_toLight);
    vec3 toCameraUnit = normalize(_toCamera);
    vec3 reflectedLightDirection = reflect(-_toLight, surfaceNormalUnit);

    float surfaceNormalDotToLight = dot(surfaceNormalUnit, toLightUnit);
    float brightness = max(surfaceNormalDotToLight, ambientLight);
    vec4 diffuse = vec4((brightness * lightColor), 1.0);

    float specularFactor = max(dot(reflectedLightDirection, toCameraUnit), 0.0);
    float dampenedFactor = pow(specularFactor, shineDampener);
    vec4 specularColor = vec4((dampenedFactor * reflectivity * lightColor), 1.0);

    out_Color = diffuse * texture(textureSampler, _texture) + specularColor;
}

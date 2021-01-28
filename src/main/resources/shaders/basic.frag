#version 330 core

in vec2 tex;
in vec3 pos;

out vec4 outColor;

uniform sampler2D texSampler;

void main() {
    outColor = texture(texSampler, tex);
}
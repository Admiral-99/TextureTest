#version 330 core

layout(location = 0) in vec4 position;
layout(location = 1) in vec2 texCoord;

out vec3 pos;
out vec2 tex;

void main() {
    gl_Position = position;

    pos = position.xyz;
    tex = texCoord;
}
#version 330 core

in vec2 TexCoords;
out vec4 FragColor;

uniform sampler2D textTexture;
uniform vec3 glowColor;
uniform float glowIntensity;

void main() {
    float alpha = texture(textTexture, TexCoords).a;

    float glow = 0.0;
    for (int x = -2; x <= 2; x++) {
        for (int y = -2; y <= 2; y++) {
            vec2 offset = vec2(x, y) * 0.01;
            glow += texture(textTexture, TexCoords + offset).a;
        }
    }

    vec3 glowEffect = glowColor * glow * glowIntensity;

    vec3 textColor = vec3(1.0);
    FragColor = vec4(textColor * alpha + glowEffect, alpha);
}

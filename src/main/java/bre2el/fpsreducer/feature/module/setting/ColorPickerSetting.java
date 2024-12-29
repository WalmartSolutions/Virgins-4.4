package bre2el.fpsreducer.feature.module.setting;

import java.awt.Color;

public class ColorPickerSetting extends Setting
{
    public Color color;
    public int blue;
    public String description;
    public int green;
    public int red;
    public String name;

    public int getBlue() {
        return this.blue;
    }

    public Color getColor() {
        return this.color;
    }

    public int getRed() {
        return this.red;
    }

    int clamp(final int min, final int value, final int max) {
        return Math.max(value, Math.min(max, min));
    }

    public int getGreen() {
        return this.green;
    }

    public void setGreen(final int green) {
        this.green = this.clamp(green, 0, 255);
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(final String description) {
        this.description = description;
    }

    public ColorPickerSetting(final String defaultColor, final String description, final Color name) {
        super(defaultColor, description);
        this.name = defaultColor;
        this.description = description;
        this.color = name;
    }

    public void setColor(final Color color) {
        this.color = color;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    public void setRed(final int red) {
        this.red = this.clamp(red, 0, 255);
    }

    public void setBlue(final int blue) {
        this.blue = this.clamp(blue, 0, 255);
    }

    @Override
    public String getName() {
        return this.name;
    }
}

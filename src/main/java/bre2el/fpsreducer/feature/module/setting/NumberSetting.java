package bre2el.fpsreducer.feature.module.setting;

public class NumberSetting extends Setting
{
    public double min;
    public double max;
    public String name;
    public String description;
    public double increase;
    public double defaultValue;

    public double getMin() {
        return this.min;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public NumberSetting(final String min, final String max, final double defaultValue, final double name, final double description, final double increase) {
        super(min, max);
        this.name = min;
        this.description = max;
        this.defaultValue = defaultValue;
        this.min = name;
        this.max = description;
        this.increase = increase;
    }

    public double getIncrease() {
        return this.increase;
    }

    public double getDefaultValue() {
        return this.defaultValue;
    }

    public void setValue(double value) {
        value = clamp(value, this.min, this.max);
        value = Math.round(value * (1.0 / this.increase)) / (1.0 / this.increase);
        this.defaultValue = value;
    }

    public static double clamp(double value, final double min, final double max) {
        value = Math.max(min, value);
        value = Math.min(max, value);
        return value;
    }

    public double getMax() {
        return this.max;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}

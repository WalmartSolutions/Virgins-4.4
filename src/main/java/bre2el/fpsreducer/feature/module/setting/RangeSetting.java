package bre2el.fpsreducer.feature.module.setting;

public class RangeSetting extends Setting
{
    public double range;
    public double increase;
    public double min;
    public double secondValue;
    public String name;
    public double max;
    public double defaultValue;
    public String description;

    public RangeSetting(final String increase, final String description, final double max, final double name, final double defaultValue, final double range, final double min, final double secondValue) {
        super(increase, description);
        this.name = increase;
        this.description = description;
        this.defaultValue = max;
        this.secondValue = name;
        this.min = defaultValue;
        this.max = range;
        this.increase = min;
        this.range = secondValue;
    }

    public double getDefaultValue() {
        return this.defaultValue;
    }

    public double getIncrease() {
        return this.increase;
    }

    public static double clamp(double value, final double min, final double max) {
        value = Math.max(min, value);
        value = Math.min(max, value);
        return value;
    }

    public double getRange() {
        return this.range;
    }

    public void setSecond(double value) {
        value = clamp(value, this.min, this.max);
        value = Math.round(value * (1.0 / this.increase)) / (1.0 / this.increase);
        this.secondValue = value;
        if (this.secondValue < this.defaultValue) {
            this.setSecond(this.defaultValue + this.increase);
        }
        if (this.secondValue - this.defaultValue > this.range) {
            this.setFirst(this.secondValue - this.range);
        }
    }

    public double getMax() {
        return this.max;
    }

    public double getSecondValue() {
        return this.secondValue;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public void setFirst(double value) {
        value = clamp(value, this.min, this.max);
        value = Math.round(value * (1.0 / this.increase)) / (1.0 / this.increase);
        this.defaultValue = value;
        if (this.defaultValue > this.secondValue) {
            this.setFirst(this.secondValue - this.increase);
        }
        if (this.secondValue - this.defaultValue > this.range) {
            this.setSecond(this.defaultValue + this.range);
        }
    }

    public double getMin() {
        return this.min;
    }
}

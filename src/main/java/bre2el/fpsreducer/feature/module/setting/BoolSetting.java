package bre2el.fpsreducer.feature.module.setting;

public class BoolSetting extends Setting
{
    public boolean enabled;
    public String description;
    public String name;

    public BoolSetting(final String description, final String enabled, final boolean name) {
        super(description, enabled);
        this.name = description;
        this.description = enabled;
        this.enabled = name;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}

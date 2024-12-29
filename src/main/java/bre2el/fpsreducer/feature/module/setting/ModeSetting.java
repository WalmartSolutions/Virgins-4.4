package bre2el.fpsreducer.feature.module.setting;

import java.util.Arrays;
import java.util.List;

public class ModeSetting extends Setting
{
    public String description;
    public List<String> modes;
    public String name;
    public String mode;
    public boolean extended;

    public ModeSetting(final String description, final String modes, final String mode, final String[] name) {
        super(description, modes);
        this.name = description;
        this.description = modes;
        this.modes = Arrays.asList(name);
        this.mode = (this.modes.contains(mode) ? mode : this.modes.getFirst());
    }

    public boolean isExtended() {
        return this.extended;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(final String mode) {
        this.mode = mode;
    }

    public void setExtended(final boolean extended) {
        this.extended = extended;
    }
}

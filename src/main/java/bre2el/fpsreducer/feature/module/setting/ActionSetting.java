package bre2el.fpsreducer.feature.module.setting;

public class ActionSetting extends Setting {
    public String description;
    public boolean activated;
    public String name;

    public boolean isActivated() {
        return this.activated;
    }

    public ActionSetting(String name, String description) {
        super(name, description);
        this.name = name;
        this.description = description;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}

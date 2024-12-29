package bre2el.fpsreducer.feature.module.setting;

public class Setting {
    public String name;
    public String description;

    public void setName(String name) {
        this.name = name;
    }

    public Setting(String description, String name) {
        this.name = description;
        this.description = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public String getName() {
        return this.name;
    }
}
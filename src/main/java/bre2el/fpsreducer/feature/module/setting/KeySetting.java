package bre2el.fpsreducer.feature.module.setting;

import bre2el.fpsreducer.client.Main;
import org.lwjgl.glfw.GLFW;

public class KeySetting extends Setting {
    public String name;
    public String description;
    public int key;

    public boolean isActive(int scanCode) {
        if (scanCode != 0) {
            if (scanCode == 256 || scanCode == 261) {
                return false;
            } else {
                return scanCode >= 5
                        ? GLFW.glfwGetKey(Main.mc.getWindow().getHandle(), scanCode) == 1
                        : GLFW.glfwGetMouseButton(Main.mc.getWindow().getHandle(), scanCode) == 1;
            }
        } else {
            return false;
        }
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public KeySetting(String description, String name, int key) {
        super(description, name);
        this.name = description;
        this.description = name;
        this.key = key;
    }
}

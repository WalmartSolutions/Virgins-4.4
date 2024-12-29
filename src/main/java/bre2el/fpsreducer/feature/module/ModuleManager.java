package bre2el.fpsreducer.feature.module;

import bre2el.fpsreducer.feature.module.modules.client.SelfDestruct;
import bre2el.fpsreducer.feature.module.modules.client.Virgin;
import bre2el.fpsreducer.feature.module.modules.combat.*;
import bre2el.fpsreducer.feature.module.modules.movement.BridgeAssist;
import bre2el.fpsreducer.feature.module.modules.movement.FakeLag;
import bre2el.fpsreducer.feature.module.modules.movement.Sprint;
import bre2el.fpsreducer.feature.module.modules.utility.*;
import bre2el.fpsreducer.feature.module.modules.visual.*;
import bre2el.fpsreducer.gui.font.FontRenderers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ModuleManager {
    public static Comparator<Module> nameLengthComparator = (o2, o1) -> Integer.compare(
            (int)FontRenderers.Main.getStringWidth(o1.getName() + o1.getDetail()), (int) FontRenderers.Main.getStringWidth(o2.getName() + o2.getDetail())
    );
    public static ModuleManager INSTANCE = new ModuleManager();
    public List<Module> modules = new ArrayList();
    public static Comparator<Module> nameLengthComparatorLower = (o1, o2) -> Integer.compare(
            (int)FontRenderers.Main.getStringWidth((o2.getName() + o2.getDetail()).toLowerCase()),
            (int)FontRenderers.Main.getStringWidth((o1.getName() + o1.getDetail()).toLowerCase())
    );

    public List<Module> getModules() {
        return this.modules;
    }

    void addModules() {
        // CLIENT
        addModule(new Virgin());
        addModule(new SelfDestruct());

        // COMBAT
        addModule(new AimAssist());
        addModule(new AnchorMacro());
        addModule(new AutoBlock());
        addModule(new AutoDoubleHand());
        addModule(new AutoHitCrystal());
        addModule(new AutoInventoryTotem());
        addModule(new Backtrack());
        addModule(new CrystalClicker());
        addModule(new JumpReset());
        addModule(new Reach());
        addModule(new ShieldDisabler());
        addModule(new SideKnockback());
        addModule(new SilentAim());
        addModule(new TotemHit());
        addModule(new TriggerBot());
        addModule(new WTap());

        // UTILITY
        addModule(new AntiBot());
        addModule(new BackPlace());
        addModule(new Friends());
        addModule(new NoDelay());
        addModule(new Teams());

        // MOVEMENT
        addModule(new BridgeAssist());
        addModule(new FakeLag());
        addModule(new Sprint());

        // VISUAL
        addModule(new HUD());
        addModule(new BlockESP());
        addModule(new ItemESP());
        addModule(new PlayerESP());
        addModule(new StorageESP());
        addModule(new Tracers());
    }

    void addModule(Module module) {
        this.modules.add(module);
    }

    public List<Module> getModulesInCategory(Module.Category category) {
        ArrayList var2 = new ArrayList();

        for (Module var4 : this.modules) {
            if (var4.getCategory() == category) {
                var2.add(var4);
            }
        }

        return var2;
    }

    public Module getModuleByName(String name) {
        for (Module module : this.modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        System.out.println("Module not found: " + name);
        return null;
    }

    public int getKey(Module module) {
        return module.getKey();
    }

    public <T extends Module> T getModuleClass(Class<T> moduleClass) {
        for (Module var3 : this.modules) {
            if (moduleClass.isAssignableFrom(var3.getClass())) {
                return (T)var3;
            }
        }

        return null;
    }

    public void init() {
        this.addModules();
    }

    public List<Module> getEnabledModules(boolean lower) {
        ArrayList var2 = new ArrayList();

        for (Module var4 : this.modules) {
            if (var4.isEnabled()) {
                var2.add(var4);
                if (!lower) {
                    var2.sort(nameLengthComparator);
                } else {
                    var2.sort(nameLengthComparatorLower);
                }
            }
        }

        return var2;
    }
}

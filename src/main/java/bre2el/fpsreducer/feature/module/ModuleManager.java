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
        //Combat
        this.modules.add(new AimAssist());
        this.modules.add(new AnchorMacro());
        this.modules.add(new AutoBlock());
        this.modules.add(new AutoDoubleHand());
        this.modules.add(new AutoHitCrystal());
        this.modules.add(new AutoInventoryTotem());
        this.modules.add(new Backtrack());
        this.modules.add(new CrystalClicker());
        this.modules.add(new JumpReset());
        this.modules.add(new Reach());
        this.modules.add(new ShieldDisabler());
        this.modules.add(new SideKnockback());
        this.modules.add(new SilentAim());
        this.modules.add(new TotemHit());
        this.modules.add(new TriggerBot());
        this.modules.add(new WTap());

        //Render
        this.modules.add(new BlockESP());
        this.modules.add(new HUD());
        this.modules.add(new ItemESP());
        this.modules.add(new NoInvis());
        this.modules.add(new PlayerESP());
        this.modules.add(new StorageESP());
        this.modules.add(new Tracers());

        //Movement
        this.modules.add(new BridgeAssist());
        this.modules.add(new FakeLag());
        this.modules.add(new Sprint());

        //Utility
        this.modules.add(new AntiAction());
        this.modules.add(new AntiBot());
        this.modules.add(new AutoLoot());
        this.modules.add(new BackPlace());
        this.modules.add(new ElytraSwap());
        this.modules.add(new FastPlace());
        this.modules.add(new Friends());
        this.modules.add(new Macro());
        this.modules.add(new NoDelay());
        this.modules.add(new Refill());
        this.modules.add(new Teams());

        //Client
        this.modules.add(new SelfDestruct());
        this.modules.add(new Virgin());
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

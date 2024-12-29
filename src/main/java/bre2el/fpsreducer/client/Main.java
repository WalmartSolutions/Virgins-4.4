package bre2el.fpsreducer.client;

import bre2el.fpsreducer.event.orbit.EventBus;
import bre2el.fpsreducer.event.orbit.IEventBus;
import bre2el.fpsreducer.feature.module.ModuleManager;
import bre2el.fpsreducer.feature.module.modules.client.Virgin;
import bre2el.fpsreducer.util.RenderUtil;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;

import java.awt.*;
import java.lang.invoke.MethodHandles;

public class Main implements ModInitializer {
    public static String LICENSE;
    public static String OS;
    public static MinecraftClient mc;
    public static RenderUtil.ShaderLoader SHADERS;
    public static String PACKAGE;
    public static IEventBus EVENTBUS = new EventBus();
    public static String NAME;
    public static String MESSAGE;
    public static String VERSION;
    public static Main INSTANCE;
    //public static ConfigManager CONFIG; //im not deobfing jnic

    @Override
    public void onInitialize() {
       // mc = MinecraftClient.getInstance(); initialized in minecraftclientmixin, #bestsecurity
        LICENSE = "Smoked by walmartsolutions";
        PACKAGE = "bre2el.fpsreducer";
        NAME = "Virgin";
        VERSION = "v4.4 (troll edition)";
        MESSAGE = "#shmokedbywalmartsolutions"; // this is the reponse btw
        OS = System.getProperty("os.name");
        ModuleManager.INSTANCE.init();
       // SHADERS = new RenderUtil.ShaderLoader();

        // meteor's event manager
        EVENTBUS.registerLambdaFactory(PACKAGE, ((lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup())));
    }

    public static class Theme {

        static Virgin virgin = ModuleManager.INSTANCE.getModuleClass(Virgin.class);

        public static Color PRIMARY = virgin.primary.getColor();
        public static Color SECONDARY = virgin.secondary.getColor();
        public static Color BACKGROUND = virgin.background.getColor();
        public static Color TEXT = virgin.text.getColor();
        // copilot btw
        public static Color FOREGROUND = Color.WHITE;
        public static Color BACKGROUND_FOREGROUND = Color.WHITE;
        public static Color FOREGROUND_BACKGROUND = Color.WHITE;
        public static Color FOREGROUND_BACKGROUND_FOREGROUND = Color.WHITE;
    }
}

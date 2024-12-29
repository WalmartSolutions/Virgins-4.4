package bre2el.fpsreducer.feature.module.modules.client;

import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.ModuleManager;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.gui.impl.ClickGUI;
import com.sun.jna.Memory;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

import static bre2el.fpsreducer.client.Main.mc;

@SuppressWarnings("removal")
public final class SelfDestruct extends Module {
    public static boolean destruct = false;

    private final BoolSetting replaceMod = new BoolSetting("Replace Mod", "Replaces the mod with the original JAR file of the FPS Reducer mod", true);

    public SelfDestruct() {
        super("Self Destruct",
                "Removes the client from your game",
                Category.Client);
        addSettings(new Setting[]{replaceMod});
    }

    @Override
    public void onEnable() {
        destruct = true;

        ModuleManager.INSTANCE.getModuleClass(Virgin.class).setEnabled(false);
        //Argon.INSTANCE.getModuleManager().getModule(ClickGUI.class).setEnabled(false);
        setEnabled(false);

        // stolen from argoon
        // Argon.INSTANCE.getProfileManager().saveProfile();

        if (mc.currentScreen instanceof ClickGUI) {
            // Argon.INSTANCE.guiInitialized = false;
            mc.currentScreen.close();
        }

        if (replaceMod.isEnabled()) {
            try {
                String modUrl = "https://cdn.modrinth.com/data/iZ10HXDj/versions/9ewqcOwy/FpsReducer2-fabric-1.21-2.9.jar";
                File currentJar = getCurrentJarPath();

                if (currentJar.exists())
                    replaceModFile(modUrl, getCurrentJarPath());
            } catch (Exception e) {}
        }


        // i aint doing allat  -ablue
//        for (Module module : Argon.INSTANCE.getModuleManager().getModules()) {
//            module.setEnabled(false);
//
//            module.setName(null);
//            module.setDescription(null);
//
//            for (Setting<?> setting : module.getSettings()) {
//                setting.setName(null);
//                setting.setDescription(null);
//
//                if(setting instanceof StringSetting set)
//                    set.setValue(null);
//            }
//            module.getSettings().clear();
//        }

        Runtime runtime = Runtime.getRuntime();

        for (int i = 0; i <= 10; i++) {
            runtime.gc();
            runtime.runFinalization();

            try {
                Thread.sleep(100 * i);
                Memory.purge();
                Memory.disposeAll();
            } catch (InterruptedException ignored) {}
        }
    }

    public static File getCurrentJarPath() throws URISyntaxException {
        return new File(SelfDestruct.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
    }

    public static void replaceModFile(String downloadURL, File savePath) throws IOException {
        URL url = new URL(downloadURL);
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.setRequestMethod("GET");

        try (var in = httpConnection.getInputStream();
             var fos = new java.io.FileOutputStream(savePath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }

        httpConnection.disconnect();
    }
}
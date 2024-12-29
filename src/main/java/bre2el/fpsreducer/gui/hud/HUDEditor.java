package bre2el.fpsreducer.gui.hud;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.feature.module.ModuleManager;
import bre2el.fpsreducer.feature.module.modules.visual.HUD;
import bre2el.fpsreducer.gui.hud.impl.ArrayListRenderer;
import bre2el.fpsreducer.gui.hud.impl.KeybindHUDRenderer;
import bre2el.fpsreducer.gui.hud.impl.KeystrokesRenderer;
import bre2el.fpsreducer.gui.hud.impl.PotionHUDRenderer;
import bre2el.fpsreducer.gui.hud.impl.WatermarkRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class HUDEditor extends Screen {
    public static HUDEditor INSTANCE = new HUDEditor();

    public HUDEditor() {
        super(Text.of(""));
    }

    public void render(DrawContext mouseX, int context, int delta, float mouseY) {
        float var5 = (float)Main.mc.getWindow().getFramebufferWidth() / (float)Main.mc.getWindow().getScaledWidth();
        float var6 = (float)Main.mc.getWindow().getFramebufferHeight() / (float)Main.mc.getWindow().getScaledHeight();
        float var7 = Math.min(var5, var6) * 0.5F;
        mouseX.getMatrices().push();
        mouseX.getMatrices().scale(1.0F / var7, 1.0F / var7, 1.0F);
        int var8 = (int)((float)context * var7);
        int var9 = (int)((float)delta * var7);
        ArrayListRenderer.INSTANCE.render(mouseX, var8, var9, mouseY);
        HUD var10 = ModuleManager.INSTANCE.getModuleClass(HUD.class);
        ArrayListRenderer.INSTANCE.renderHUD(mouseX, var10.lowerCase.isEnabled());
        KeybindHUDRenderer.INSTANCE.render(mouseX, var8, var9, mouseY);
        KeybindHUDRenderer.INSTANCE.renderHUD(mouseX);
        PotionHUDRenderer.INSTANCE.render(mouseX, var8, var9, mouseY);
        PotionHUDRenderer.INSTANCE.renderHUD(mouseX);
        WatermarkRenderer.INSTANCE.render(mouseX, var8, var9, mouseY);
        WatermarkRenderer.INSTANCE.renderHUD(mouseX);
        KeystrokesRenderer.INSTANCE.render(mouseX, var8, var9, mouseY);
        KeystrokesRenderer.INSTANCE.renderHUD(mouseX);
        mouseX.getMatrices().scale(1.0F, 1.0F, 1.0F);
        mouseX.getMatrices().pop();
        super.render(mouseX, var8, delta, mouseY);
    }
}

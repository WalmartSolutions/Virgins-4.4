package bre2el.fpsreducer.gui.hud.impl;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.client.Main.Theme;
import bre2el.fpsreducer.gui.font.FontRenderers;
import bre2el.fpsreducer.util.ColorUtil;
import bre2el.fpsreducer.util.RenderUtil;
import java.awt.Color;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class MessageBox extends Screen {
    public String message;
    public String title;

    public MessageBox(String title, String message) {
        super(Text.of(""));
        this.title = title;
        this.message = message;
    }

    public void render(DrawContext mouseX, int context, int delta, float mouseY) {
        int var5 = (int)((float)Main.mc.getWindow().getScaledWidth() / 2.0F) - 75;
        int var6 = (int)((float)Main.mc.getWindow().getScaledHeight() / 2.0F) - 50;
        RenderUtil.drawBlurredShadow(mouseX.getMatrices(), (float)var5, (float)var6, 150.0F, 50.0F, 16, Color.BLACK);
        RenderUtil.renderRoundedRect(mouseX.getMatrices(), (float)var5, (float)var6, (float)(var5 + 150), (float)(var6 + 50), Color.BLACK, 6.0F);
        mouseX.drawHorizontalLine(var5, var5 + 150 - 1, var6 + 18, ColorUtil.TwoColoreffect(Theme.PRIMARY, Theme.SECONDARY, 10.0, 2.0).getRGB());
        FontRenderers.Main.drawCenteredString(mouseX.getMatrices(), this.title, (double)((float)var5 + 75.0F), (double)(var6 + 4), Color.WHITE.getRGB());
        FontRenderers.Sub.drawCenteredString(mouseX.getMatrices(), this.message, (double)((float)var5 + 75.0F), (double)(var6 + 25), Color.WHITE.getRGB());
    }
}

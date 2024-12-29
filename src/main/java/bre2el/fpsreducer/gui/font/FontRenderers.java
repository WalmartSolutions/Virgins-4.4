package bre2el.fpsreducer.gui.font;

import java.awt.Font;
import java.io.ByteArrayInputStream;

import org.jetbrains.annotations.NotNull;

public class FontRenderers {
    public static FontAdapter Small = createDefault(15);
    public static FontAdapter Title = createDefault(23);
    public static FontAdapter Sub = createDefault(15);
    public static FontAdapter Main = createDefault(17);

    public static Font loadFont(byte[] fontData, float size) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fontData)) {
            Font font = Font.createFont(Font.TRUETYPE_FONT, byteArrayInputStream).deriveFont(Font.PLAIN, size);
            return font;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load font from provided data", e);
        }
    }

    // crazy how your font still didn't save you
    @NotNull
    public static RendererFontAdapter createDefault(float size) {
        try { // thank you ablue for fix this with chatgpt
            var inputStream = FontRenderer.class.getResourceAsStream("/assets/font/WalmartSolutions.ttf");
            if (inputStream == null) {
                System.out.println("Font resource not found. Using default font.");
                Font defaultFont = new Font("Poppins", Font.PLAIN, Math.round(size));
                return new RendererFontAdapter(defaultFont, size / 2.0F);
            }
            byte[] fontData = inputStream.readAllBytes();
            inputStream.close();
            Font customFont = loadFont(fontData, size / 2.0F);
            return new RendererFontAdapter(customFont, size / 2.0F);
        } catch (Exception e) {
            throw new RuntimeException("Error loading font", e);
        }
    }
}
package online.kingdomkeys.kingdomkeys.client.gui.menu.items;

import java.awt.Color;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import online.kingdomkeys.kingdomkeys.KingdomKeys;

public class GuiElementBox extends Widget{
	Minecraft mc;
    int posX, posY, width, height, colour;

    public GuiElementBox(int posX, int posY, int width, int height, int colour) {
		super(posX, posY, width, 14, "");
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.colour = colour;
        mc = Minecraft.getInstance();
    }

    private final ResourceLocation texture = new ResourceLocation(KingdomKeys.MODID, "textures/gui/menu/menu_button.png");

    private final int borderSize = 24;
    private final int
            tlCornerU = 22, tlCornerV = 67,
            blCornerU = 22, blCornerV = 94,
            trCornerU = 49, trCornerV = 67,
            brCornerU = 49, brCornerV = 94,
            lCenterU = 22, lCenterV = 92,
            tCenterU = 47, tCenterV = 67,
            rCenterU = 49, rCenterV = 92,
            bCenterU = 47, bCenterV = 94,
            mCenterU = 47, mCenterV = 92
            ;

    public void draw() {
        Color col = Color.decode(String.valueOf(colour));
        RenderSystem.pushMatrix();
        RenderSystem.color3f(col.getRed(),col.getGreen(),col.getBlue());
        //RenderSystem.enableAlpha();
        RenderSystem.enableBlend();
        mc.textureManager.bindTexture(texture);
        //Top left corner
        blit(posX, posY, tlCornerU, tlCornerV, borderSize, borderSize);
        //Top right corner
        blit(posX + width - borderSize, posY, trCornerU, trCornerV, borderSize, borderSize);
        //Bottom left corner
        blit(posX, posY + height - borderSize, blCornerU, blCornerV, borderSize, borderSize);
        //Bottom right corner
        blit(posX + width - borderSize, posY + height - borderSize, brCornerU, brCornerV, borderSize, borderSize);
        int centerWidth = width - (borderSize * 2);
        int centerHeight = height - (borderSize * 2);
        //Center border
        for (int i = 0; i < centerWidth; i++) {
            //Top
        	blit(posX + borderSize + i, posY, tCenterU, tCenterV, 1, borderSize);
            //Bottom
            blit(posX + borderSize + i, posY + height - borderSize, bCenterU, bCenterV, 1, borderSize);
        }
        for (int i = 0; i < centerHeight; i++) {
            //Left
        	blit(posX, posY + borderSize + i, lCenterU, lCenterV, borderSize, 1);
            //Right
        	blit(posX + width - borderSize, posY + borderSize + i, rCenterU, rCenterV, borderSize, 1);
        }
        //Inside
        RenderSystem.pushMatrix();
        RenderSystem.translated(posX + borderSize, posY + borderSize, 0);
        RenderSystem.scaled(centerWidth, centerHeight,1);
        blit(0, 0, mCenterU, mCenterV, 1, 1);
        RenderSystem.popMatrix();
        RenderSystem.popMatrix();
        //drawModalRectWithCustomSizedTexture(posX + borderSize, posY + borderSize, gradientU, gradientV, centerWidth, centerHeight, gradientW, gradientH);
        //drawScaledCustomSizeModalRect(posX + borderSize, posY + borderSize, gradientU, gradientV, gradientW, gradientH, centerWidth, centerHeight, centerWidth, centerHeight);
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}

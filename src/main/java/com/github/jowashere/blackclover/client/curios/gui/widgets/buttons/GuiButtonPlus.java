package com.github.jowashere.blackclover.client.curios.gui.widgets.buttons;

import com.github.jowashere.blackclover.Main;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class GuiButtonPlus extends Button {

    final ResourceLocation gray_texture = new ResourceLocation(Main.MODID + ":textures/gui/shinobistatsbackground.png");
    final ResourceLocation brown_texture = new ResourceLocation(Main.MODID + ":textures/gui/tabedbackground.png");

    final boolean gray;
    int u = 234;
    int v = 152;
    int widthIn;
    int heightIn;


    public GuiButtonPlus(int widthIn, int heightIn, boolean gray, IPressable onPress) {
        super(widthIn, heightIn, 21, 20, new StringTextComponent(""), onPress);
        this.widthIn = widthIn;
        this.heightIn = heightIn;
        this.gray = gray;
    }

    @Override
    public void renderButton(MatrixStack stack, int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
        Minecraft mc = Minecraft.getInstance();
        if (!visible)
        {
            return;
        }
        if (gray) {
            mc.textureManager.bind(gray_texture);
            if (isHovered) {
                v = 131;
            } else {
                v = 152;
            }
        }
        else {
            mc.textureManager.bind(brown_texture);
            u = 233;
            if (isHovered) {
                v = 81;
            } else {
                v = 102;
            }
        }
        mc.gui.blit(stack, widthIn, heightIn, u, v, width, height);
    }
}

package com.github.jowashere.blackclover.client.gui.widgets.sybmols;

import com.github.jowashere.blackclover.Main;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class GuiButtonSymbol extends Button {

    final ResourceLocation texture = new ResourceLocation(Main.MODID + ":textures/gui/symbols.png");

    protected final Button.IPressable onPress;
    final int u;
    final int v;
    int widthIn;
    int heightIn;

    public void onPress() {
        this.onPress.onPress(this);
    }

    public GuiButtonSymbol(int widthIn, int heightIn, int u, int v, IPressable onPress) {
        super(widthIn, heightIn, 16, 16, new StringTextComponent(""), onPress);
        this.widthIn = widthIn;
        this.heightIn = heightIn;
        this.onPress = onPress;
        this.u = u;
        this.v = v;
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
        Minecraft mc = Minecraft.getInstance();
        mc.textureManager.bind(texture);
        if (!visible)
        {
            return;
        }
        mc.gui.blit(matrixStack, widthIn, heightIn, u, v, width, height);
    }

}

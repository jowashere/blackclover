package com.github.jowashere.blackclover.client.curios.gui.player;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.client.curios.gui.GuiButtonTab;
import com.github.jowashere.blackclover.client.curios.gui.player.spells.SpellsScreen;
import com.github.jowashere.blackclover.client.curios.gui.widgets.sybmols.GuiButtonSymbol;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;

@OnlyIn(Dist.CLIENT)
public class PlayerSpellsScreen extends AbstractTabbedBackground {

    GuiButtonSymbol primarySpellsSymbol;

    public PlayerSpellsScreen() {
        super(new TranslationTextComponent("gui." + Main.MODID + ".title.spellmenu"));
    }

    @Override
    public void registerTabsAndWidgets(IPlayerHandler playerCapability) {
        //Tabs
        addButton(new GuiButtonTab(this.getWidthInFromTab(0), this.getHeightInFromTab(0), 48, 240, 0, "Basic Magic", $ -> {
            openedTab = 0;
        }));

        //Spirit Tab
        addButton(new GuiButtonTab(this.getWidthInFromTab(1), this.getHeightInFromTab(1), 80, 240, 1, "Spirit Magic", $ -> {
            openedTab = 1;
        }));

        //Devil Tab
        addButton(new GuiButtonTab(this.getWidthInFromTab(2), this.getHeightInFromTab(2), 64, 240, 2, "Devil Magic", $ -> {
            openedTab = 2;
        }));


        //Page 1
        addButton(primarySpellsSymbol = new GuiButtonSymbol(this.guiLeft - 5, this.guiTop - 30, playerCapability.ReturnMagicAttribute().getU(), playerCapability.ReturnMagicAttribute().getV(), $ -> {

            Minecraft.getInstance().setScreen(new SpellsScreen(playerCapability.ReturnMagicAttribute()));

        }));
    }

    @Override
    public void renderPage(int openedTab, int p_render_1_, int p_render_2_, float p_render_3_) {
        AbstractClientPlayerEntity player = Minecraft.getInstance().player;
        LazyOptional<IPlayerHandler> player_cap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler playerc = player_cap.orElse(new PlayerCapability());

        if (openedTab != 0) {
            primarySpellsSymbol.visible = false;
        }

        switch (openedTab) {
            case 0:
                primarySpellsSymbol.visible = true;
                primarySpellsSymbol.renderButton(new MatrixStack(), p_render_1_, p_render_2_, p_render_3_);
                break;
            case 1:
                /*fireSymbol.visible = true;
                earthSymbol.visible = true;
                windSymbol.visible = true;
                waterSymbol.visible = true;
                lightningSymbol.visible = true;
                fireSymbol.renderButton(p_render_1_, p_render_2_, p_render_3_);
                earthSymbol.renderButton(p_render_1_, p_render_2_, p_render_3_);
                windSymbol.renderButton(p_render_1_, p_render_2_, p_render_3_);
                waterSymbol.renderButton(p_render_1_, p_render_2_, p_render_3_);
                lightningSymbol.renderButton(p_render_1_, p_render_2_, p_render_3_);*/
                break;
            case 2:
                /*magnetSymbol.visible = true;
                woodSymbol.visible = true;
                lavaSymbol.visible = true;
                boilSymbol.visible = true;
                iceSymbol.visible = true;
                stormSymbol.visible = true;
                scorchSymbol.visible = true;
                explosionSymbol.visible = true;
                magnetSymbol.renderButton(p_render_1_, p_render_2_, p_render_3_);
                woodSymbol.renderButton(p_render_1_, p_render_2_, p_render_3_);
                lavaSymbol.renderButton(p_render_1_, p_render_2_, p_render_3_);
                boilSymbol.renderButton(p_render_1_, p_render_2_, p_render_3_);
                iceSymbol.renderButton(p_render_1_, p_render_2_, p_render_3_);
                stormSymbol.renderButton(p_render_1_, p_render_2_, p_render_3_);
                scorchSymbol.renderButton(p_render_1_, p_render_2_, p_render_3_);
                explosionSymbol.renderButton(p_render_1_, p_render_2_, p_render_3_);
                break;*/
        }
    }

}

package com.github.jowashere.blackclover.client.gui.widgets.spells;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.api.interfaces.IBCMSpellButtonPress;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.client.gui.player.spells.AbstractSpellScreen;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.spells.PacketSetSpellBoolean;
import com.github.jowashere.blackclover.util.helpers.SpellHelper;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.LazyOptional;

public class GuiButtonSpell extends Button {

    ResourceLocation texture = new ResourceLocation(Main.MODID + ":textures/gui/spells.png");

    final int u;
    final int v;
    public int widthIn;
    public int heightIn;
    private final String name;
    private boolean has;

    public GuiButtonSpell(int widthIn, int heightIn, int u, int v, String name, boolean has, ResourceLocation resourceLocation) {
        super(widthIn, heightIn, 16, 16, new StringTextComponent(""), new IBCMSpellButtonPress() {
            @Override
            public void onPress(GuiButtonSpell buttonSpell, IPlayerHandler playerCapability) {
                if (Minecraft.getInstance().screen instanceof AbstractSpellScreen) {
                    boolean didBuy = buttonSpell.doSpellPress((AbstractSpellScreen) Minecraft.getInstance().screen);
                    if (didBuy) {
                        playerCapability.setSpellBoolean(SpellHelper.getSpellFromString(buttonSpell.getSpellName()), true);
                        buttonSpell.sendPackets(buttonSpell.getSpellName(), true);
                    }
                }
            }
        });
        this.widthIn = widthIn;
        this.heightIn = heightIn;
        this.u = u;
        this.v = v;
        this.name = name;
        this.has = has;
        this.texture = resourceLocation;
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
        Minecraft mc = Minecraft.getInstance();
        mc.textureManager.bind(texture);
        if (!visible)
        {
            return;
        }
        mc.gui.blit(matrixStack, widthIn, heightIn, u, v, width, height, 256, 256);
    }

    public boolean hasSpell() {
        return this.has;
    }

    public void setHasSpell(boolean has) {
        this.has = has;
    }

    public String getTranslationName() {
        return this.name;
    }

    public String getSpellName() {
        String string = getTranslationName();
        String[] parts = string.split("\\.");
        if (parts.length > 1) {
            return parts[1];
        }
        return string;
    }

    public boolean doSpellPress(AbstractSpellScreen screen) {
        PlayerEntity playerEntity = Minecraft.getInstance().player;
        LazyOptional<IPlayerHandler> player_cap = playerEntity.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler playerc = player_cap.orElse(new PlayerCapability());
        boolean flag = false;

        if (this.hasSpell())
        {
            screen.spellToggle = "spell." + this.getTranslationName();
        }
        else {
            playerEntity.sendMessage(new StringTextComponent("You don't have access to this spell."), playerEntity.getUUID());
        }
        return flag;
    }

    /*public boolean doBodyPress(AbstractBodyScreen screen, BeNMBody bodyMode) {
        PlayerEntity playerEntity = Minecraft.getInstance().player;
        boolean flag = false;
        if (this.hasJutsu())
        {
            screen.bodyToggle = "body." + this.getTranslationName();
            screen.currentBodyMode = bodyMode;
            flag = true;
        }
        else {
            playerEntity.sendMessage(new StringTextComponent("Conditions not met for " + new TranslationTextComponent(this.getTranslationName()).getString() + "."));
        }
        return flag;
    }*/

    public void sendPackets(String spellName, boolean hasSpell) {
        NetworkLoader.INSTANCE.sendToServer(new PacketSetSpellBoolean(spellName, hasSpell, false));
    }

}

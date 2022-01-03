package com.github.jowashere.blackclover.client.curios.gui.player;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.client.curios.gui.GuiButtonTab;
import com.github.jowashere.blackclover.client.curios.gui.widgets.buttons.GuiButtonPlus;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.stats.*;
import com.github.jowashere.blackclover.util.helpers.GUIHelper;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class PlayerStatsScreen extends AbstractTabbedBackground {

    private IPlayerHandler playerHandler;

    private GuiButtonPlus healthStatUp;
    private GuiButtonPlus physStatUp;
    private GuiButtonPlus manaStatUp;
    private GuiButtonPlus manaCStatUp;


    float xMouse;
    float yMouse;

    protected PlayerStatsScreen()
    {
        super(new TranslationTextComponent("gui." + Main.MODID + ".stats"));
    }

    @Override
    public void registerTabsAndWidgets(IPlayerHandler playerCapability) {

        int posX = (this.width - 256) / 2;
        int posY = (this.height - 256) / 2;

        //Tabs
        addButton(new GuiButtonTab(this.getWidthInFromTab(0), this.getHeightInFromTab(0), 0, 240, 0, "Stats", $ -> {
            openedTab = 0;
        }));

        //Page 1
        addButton(healthStatUp = new GuiButtonPlus(posX - 70, posY + 100, false, $ -> {
            healthStatUp();
        }));

        addButton(physStatUp = new GuiButtonPlus(posX - 70, posY + 120, false, $ -> {
            physicalStatUp();
        }));

        addButton(manaStatUp = new GuiButtonPlus(posX - 70, posY + 140, false, $ -> {
            manaStatUp();
        }));

        addButton(manaCStatUp = new GuiButtonPlus(posX - 70, posY + 160, false, $ -> {
            manaConStatUp();
        }));


    }

    @Override
    public void renderPage(MatrixStack stack, int openedTab, int x, int y, float f) {

        this.xMouse = (float)x;
        this.yMouse = (float)y;

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int posX = (this.width - 256) / 2;
        int posY = (this.height - 256) / 2;

        String magicLevelGUI = I18n.get("gui.blackclover.magiclevel");
        String healthGUI = I18n.get("gui.blackclover.health");
        String physicalGUI = I18n.get("gui.blackclover.physical");
        String manaGUI = I18n.get("gui.blackclover.mana");
        String manaControlExpGUI = I18n.get("gui.blackclover.manaControl");


        String magicLevel = String.valueOf(this.playerHandler.getMagicLevel());
        String healthStat = String.valueOf(this.playerHandler.getHealthStat());
        String physicalStat = String.valueOf(this.playerHandler.getPhysicalStat());
        String manaStat = String.valueOf(this.playerHandler.getManaStat());
        String manaControlStat = String.valueOf(this.playerHandler.getManaControlStat());


        GUIHelper.drawStringWithBorder(stack, this.font, TextFormatting.BOLD + magicLevelGUI + ": " + TextFormatting.RESET + magicLevel, posX - 30, posY + 70, -1);
        GUIHelper.drawStringWithBorder(stack, this.font, TextFormatting.BOLD + healthGUI + ": " + TextFormatting.RESET + healthStat, posX - 30, posY + 100, -1);
        GUIHelper.drawStringWithBorder(stack, this.font, TextFormatting.BOLD + physicalGUI + ": " + TextFormatting.RESET + physicalStat, posX - 30, posY + 120, -1);
        GUIHelper.drawStringWithBorder(stack, this.font, TextFormatting.BOLD + manaGUI + ": " + TextFormatting.RESET + manaStat, posX - 30, posY + 140, -1);
        GUIHelper.drawStringWithBorder(stack, this.font, TextFormatting.BOLD + manaControlExpGUI + ": " + TextFormatting.RESET + manaControlStat, posX - 30, posY + 160, -1);
    }

    private void healthStatUp() {
        Minecraft mc = Minecraft.getInstance();
        AbstractClientPlayerEntity player = mc.player;
        LazyOptional<IPlayerHandler> player_cap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler playerc = player_cap.orElse(new PlayerCapability());
        if (playerc.getStatPoints() >= 1) {
            playerc.addStatPoints(-1);
            playerc.addHealthStat(1);
        }
        /*else  {
            player.sendMessage(new StringTextComponent("Not Enough BeNM Points (Need 2)"));
        }*/
        NetworkLoader.INSTANCE.sendToServer(new PacketStatPointsSync(playerc.getStatPoints(), player.getId()));
        NetworkLoader.INSTANCE.sendToServer(new PacketHealthStatSync(playerc.getHealthStat(), player.getId()));
    }

    private void physicalStatUp() {
        Minecraft mc = Minecraft.getInstance();
        AbstractClientPlayerEntity player = mc.player;
        LazyOptional<IPlayerHandler> player_cap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler playerc = player_cap.orElse(new PlayerCapability());
        if (playerc.getStatPoints() >= 1) {
            playerc.addStatPoints(-1);
            playerc.addPhysicalStat(1);
        }
        /*else  {
            player.sendMessage(new StringTextComponent("Not Enough BeNM Points (Need 2)"));
        }*/
        NetworkLoader.INSTANCE.sendToServer(new PacketStatPointsSync(playerc.getStatPoints(), player.getId()));
        NetworkLoader.INSTANCE.sendToServer(new PacketPhysicalStatSync(playerc.getPhysicalStat(), player.getId()));
    }

    private void manaStatUp() {
        Minecraft mc = Minecraft.getInstance();
        AbstractClientPlayerEntity player = mc.player;
        LazyOptional<IPlayerHandler> player_cap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler playerc = player_cap.orElse(new PlayerCapability());
        if (playerc.getStatPoints() >= 1) {
            playerc.addStatPoints(-1);
            playerc.addManaStat(1);
        }
        /*else  {
            player.sendMessage(new StringTextComponent("Not Enough BeNM Points (Need 2)"));
        }*/
        NetworkLoader.INSTANCE.sendToServer(new PacketStatPointsSync(playerc.getStatPoints(), player.getId()));
        NetworkLoader.INSTANCE.sendToServer(new PacketManaStatSync(playerc.getManaStat(), player.getId()));
    }

    private void manaConStatUp() {
        Minecraft mc = Minecraft.getInstance();
        AbstractClientPlayerEntity player = mc.player;
        LazyOptional<IPlayerHandler> player_cap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler playerc = player_cap.orElse(new PlayerCapability());
        if (playerc.getStatPoints() >= 1) {
            playerc.addStatPoints(-1);
            playerc.addManaControlStat(1);
        }
        /*else  {
            player.sendMessage(new StringTextComponent("Not Enough BeNM Points (Need 2)"));
        }*/
        NetworkLoader.INSTANCE.sendToServer(new PacketStatPointsSync(playerc.getStatPoints(), player.getId()));
        NetworkLoader.INSTANCE.sendToServer(new PacketManaControlStatSync(playerc.getManaControlStat(), player.getId()));
    }
}

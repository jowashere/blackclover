package com.github.jowashere.blackclover.client.curios.gui.player;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import com.github.jowashere.blackclover.util.helpers.GUIHelper;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class PlayerMenuScreen extends Screen {

    private IPlayerHandler playerHandler;

    float xMouse;
    float yMouse;

    public PlayerMenuScreen()
    {
        super(new StringTextComponent(""));
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float f)
    {
        this.xMouse = (float)x;
        this.yMouse = (float)y;

        this.renderBackground(matrixStack);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int posX = (this.width - 256) / 2;
        int posY = (this.height - 256) / 2;

        String raceGUI = I18n.get("gui.blackclover.race.name");
        String magicLevelGUI = I18n.get("gui.blackclover.magiclevel");
        String magicAttributeGUI = I18n.get("gui.blackclover.magicattribute");
        String magicExpGUI = I18n.get("gui.blackclover.magicexp");

        String race = this.playerHandler.returnRace().getString().toLowerCase();
        String magicAttribute = this.playerHandler.ReturnMagicAttribute().getString().toLowerCase();
        String magicLevel = String.valueOf(this.playerHandler.getMagicLevel());
        String magicExp = ((int) (this.playerHandler.returnMagicExp() - BCMHelper.CalculateExp(this.playerHandler.getMagicLevel()))) + " / " + ((int) (BCMHelper.CalculateExp(this.playerHandler.getMagicLevel()+1) - BCMHelper.CalculateExp(this.playerHandler.getMagicLevel())));

        String raceActual = I18n.get("race.blackclover." + race);
        String attributeActual = I18n.get("attribute.blackclover." + magicAttribute);

        GUIHelper.drawStringWithBorder(matrixStack, this.font, TextFormatting.BOLD + magicLevelGUI + ": " + TextFormatting.RESET + magicLevel, posX - 30, posY + 70, -1);
        GUIHelper.drawStringWithBorder(matrixStack, this.font, TextFormatting.BOLD + magicAttributeGUI + ": " + TextFormatting.RESET + attributeActual, posX - 30, posY + 90, -1);
        GUIHelper.drawStringWithBorder(matrixStack, this.font, TextFormatting.BOLD + raceGUI + ": " + TextFormatting.RESET + raceActual, posX - 30, posY + 110, -1);
        GUIHelper.drawStringWithBorder(matrixStack, this.font, TextFormatting.BOLD + magicExpGUI + ": " + TextFormatting.RESET + magicExp, posX - 30, posY + 130, -1);

        GUIHelper.renderEntityInInventory(posX + 190, posY + 190, 70, (float)(posX + 190) - this.xMouse, (float)(posY + 190 - 120) - this.yMouse, this.minecraft.player);

        super.render(matrixStack, x, y, f);
    }

    @Override
    public void init()
    {
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;

        LazyOptional<IPlayerHandler> player_cap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler playerc = player_cap.orElse(new PlayerCapability());

        this.playerHandler = playerc;

        int posX = ((this.width - 256) / 2) - 110;
        int posY = (this.height - 256) / 2;

        posX += 80;
        this.addButton(new Button(posX, posY + 190, 70, 20, new TranslationTextComponent("gui.blackclover.stats"), b ->
        {
            Minecraft.getInstance().setScreen(new PlayerStatsScreen());
        }));

        posX += 80;
        this.addButton(new Button(posX, posY + 190, 70, 20, new TranslationTextComponent("gui.blackclover.spells"), b ->
        {
            Minecraft.getInstance().setScreen(new PlayerSpellsScreen());
        }));
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }




}

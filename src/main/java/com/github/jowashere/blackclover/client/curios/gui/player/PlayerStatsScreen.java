package com.github.jowashere.blackclover.client.curios.gui.player;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.client.curios.gui.widgets.buttons.GuiButtonPlus;
import com.github.jowashere.blackclover.util.helpers.GUIHelper;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
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
        addButton(new GuiButtonTab(this.getWidthInFromTab(0), this.getHeightInFromTab(0), 0, 240, 0, "Chakra Control", $ -> {
            openedTab = 0;
        }));

        //Page 1
        addButton(healthStatUp = new GuiButtonPlus(posX - 70, this.guiTop + 30, false, $ -> {
            chakraControlUpPressed();
        }));

        addButton(physStatUp = new GuiButtonPlus(posX - 70, this.guiTop + 30, false, $ -> {
            chakraControlUpPressed();
        }));

        addButton(manaStatUp = new GuiButtonPlus(posX - 70, this.guiTop + 30, false, $ -> {
            chakraControlUpPressed();
        }));

        addButton(manaCStatUp = new GuiButtonPlus(posX - 70, this.guiTop + 30, false, $ -> {
            chakraControlUpPressed();
        }));


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


        GUIHelper.drawStringWithBorder(matrixStack, this.font, TextFormatting.BOLD + magicLevelGUI + ": " + TextFormatting.RESET + magicLevel, posX - 30, posY + 70, -1);
        GUIHelper.drawStringWithBorder(matrixStack, this.font, TextFormatting.BOLD + healthGUI + ": " + TextFormatting.RESET + healthStat, posX - 30, posY + 100, -1);
        GUIHelper.drawStringWithBorder(matrixStack, this.font, TextFormatting.BOLD + physicalGUI + ": " + TextFormatting.RESET + physicalStat, posX - 30, posY + 120, -1);
        GUIHelper.drawStringWithBorder(matrixStack, this.font, TextFormatting.BOLD + manaGUI + ": " + TextFormatting.RESET + manaStat, posX - 30, posY + 140, -1);
        GUIHelper.drawStringWithBorder(matrixStack, this.font, TextFormatting.BOLD + manaControlExpGUI + ": " + TextFormatting.RESET + manaControlStat, posX - 30, posY + 160, -1);

        super.render(matrixStack, x, y, f);
    }

    @Override
    public void renderPage(int openedTab, int p_render_1_, int p_render_2_, float p_render_3_) {
        {
            Minecraft mc = Minecraft.getInstance();
            PlayerEntity player = mc.player;

            LazyOptional<IPlayerHandler> player_cap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler playerc = player_cap.orElse(new PlayerCapability());

            this.playerHandler = playerc;

            int posX = ((this.width - 256) / 2) - 110;
            int posY = (this.height - 256) / 2;

            posX += 80;
            this.addButton(new Button(posX, posY + 190, 70, 20, new TranslationTextComponent("gui.blackclover.menu"), b ->
            {
                Minecraft.getInstance().setScreen(new PlayerMenuScreen());
            }));

            posX += 80;
            this.addButton(new Button(posX, posY + 190, 70, 20, new TranslationTextComponent("gui.blackclover.spells"), b ->
            {
                Minecraft.getInstance().setScreen(new PlayerSpellsScreen());
            }));
        }
    }
}

package com.github.jowashere.blackclover.client.gui.player.spells;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.client.gui.widgets.spells.GuiButtonSpell;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.settings.PacketKeybindSet;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import org.lwjgl.opengl.GL11;

public abstract class AbstractSpellScreen extends Screen {

    public String spellToggle = "";
    final ITextComponent guiTitle;
    public int guiLeft;
    public int guiTop;
    Button buttonKey1;
    Button buttonKey2;
    Button buttonKey3;
    Button buttonKey4;
    Button buttonKey5;
    Button buttonKey6;
    Button buttonKey7;
    Button buttonKey8;
    Button buttonKey9;

    public abstract void registerSpells(IPlayerHandler playerCapability);
    public abstract void setSpellsBooleans(IPlayerHandler playerCapability);

    protected AbstractSpellScreen(ITextComponent titleIn) {
        super(titleIn);
        this.guiTitle = titleIn;
    }

    @Override
    protected void init() {
        buttons.clear();
        this.guiLeft = (this.width) / 2;
        this.guiTop = (this.height) / 2;
        Minecraft mc = Minecraft.getInstance();
        AbstractClientPlayerEntity player = mc.player;
        LazyOptional<IPlayerHandler> player_cap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler playerc = player_cap.orElse(new PlayerCapability());

        registerSpells(playerc);

        addButton(buttonKey1 = new net.minecraft.client.gui.widget.button.Button(this.guiLeft - 30, this.guiTop + 50, 20, 20, new StringTextComponent("1"), $ -> {
            {
                if(playerc.returnKeybind2().equals(this.spellToggle)){
                    playerc.setKeybind2("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(2, "", false));
                }
                if(playerc.returnKeybind3().equals(this.spellToggle)){
                    playerc.setKeybind3("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(3, "", false));
                }
                if(playerc.returnKeybind4().equals(this.spellToggle)){
                    playerc.setKeybind4("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(4, "", false));
                }
                if(playerc.returnKeybind5().equals(this.spellToggle)){
                    playerc.setKeybind5("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(5, "", false));
                }
                if(playerc.returnKeybind6().equals(this.spellToggle)){
                    playerc.setKeybind6("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(6, "", false));
                }
                if(playerc.returnKeybind7().equals(this.spellToggle)){
                    playerc.setKeybind7("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(7, "", false));
                }
                if(playerc.returnKeybind8().equals(this.spellToggle)){
                    playerc.setKeybind8("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(8, "", false));
                }
                if(playerc.returnKeybind9().equals(this.spellToggle)){
                    playerc.setKeybind9("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(9, "", false));
                }
            }
            playerc.setKeybind1(this.spellToggle);
            NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(1, this.spellToggle, false));
            player.sendMessage(new StringTextComponent("Keybind 1 Set to: " + new TranslationTextComponent(this.spellToggle).getString()), player.getUUID());
            this.spellToggle = "";
        }));
        addButton(buttonKey2 = new net.minecraft.client.gui.widget.button.Button(this.guiLeft - 10, this.guiTop + 50, 20, 20, new StringTextComponent("2"), $ -> {
            {
                if(playerc.returnKeybind1().equals(this.spellToggle)){
                    playerc.setKeybind1("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(1, "", false));
                }
                if(playerc.returnKeybind3().equals(this.spellToggle)){
                    playerc.setKeybind3("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(3, "", false));
                }
                if(playerc.returnKeybind4().equals(this.spellToggle)){
                    playerc.setKeybind4("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(4, "", false));
                }
                if(playerc.returnKeybind5().equals(this.spellToggle)){
                    playerc.setKeybind5("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(5, "", false));
                }
                if(playerc.returnKeybind6().equals(this.spellToggle)){
                    playerc.setKeybind6("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(6, "", false));
                }
                if(playerc.returnKeybind7().equals(this.spellToggle)){
                    playerc.setKeybind7("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(7, "", false));
                }
                if(playerc.returnKeybind8().equals(this.spellToggle)){
                    playerc.setKeybind8("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(8, "", false));
                }
                if(playerc.returnKeybind9().equals(this.spellToggle)){
                    playerc.setKeybind9("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(9, "", false));
                }
            }
            playerc.setKeybind2(this.spellToggle);
            NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(2, this.spellToggle, false));
            player.sendMessage(new StringTextComponent("Keybind 2 Set to: " + new TranslationTextComponent(this.spellToggle).getString()), player.getUUID());
            this.spellToggle = "";
        }));
        addButton(buttonKey3 = new net.minecraft.client.gui.widget.button.Button(this.guiLeft + 10, this.guiTop + 50, 20, 20, new StringTextComponent("3"), $ -> {
            {
                if(playerc.returnKeybind2().equals(this.spellToggle)){
                    playerc.setKeybind2("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(2, "", false));
                }
                if(playerc.returnKeybind1().equals(this.spellToggle)){
                    playerc.setKeybind1("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(1, "", false));
                }
                if(playerc.returnKeybind4().equals(this.spellToggle)){
                    playerc.setKeybind4("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(4, "", false));
                }
                if(playerc.returnKeybind5().equals(this.spellToggle)){
                    playerc.setKeybind5("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(5, "", false));
                }
                if(playerc.returnKeybind6().equals(this.spellToggle)){
                    playerc.setKeybind6("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(6, "", false));
                }
                if(playerc.returnKeybind7().equals(this.spellToggle)){
                    playerc.setKeybind7("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(7, "", false));
                }
                if(playerc.returnKeybind8().equals(this.spellToggle)){
                    playerc.setKeybind8("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(8, "", false));
                }
                if(playerc.returnKeybind9().equals(this.spellToggle)){
                    playerc.setKeybind9("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(9, "", false));
                }
            }
            playerc.setKeybind3(this.spellToggle);
            NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(3, this.spellToggle, false));
            player.sendMessage(new StringTextComponent("Keybind 3 Set to: " + new TranslationTextComponent(this.spellToggle).getString()), player.getUUID());
            this.spellToggle = "";
        }));
        addButton(buttonKey4 = new net.minecraft.client.gui.widget.button.Button(this.guiLeft - 30, this.guiTop + 70, 20, 20, new StringTextComponent("4"), $ -> {
            {
                if(playerc.returnKeybind2().equals(this.spellToggle)){
                    playerc.setKeybind2("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(2, "", false));
                }
                if(playerc.returnKeybind3().equals(this.spellToggle)){
                    playerc.setKeybind3("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(3, "", false));
                }
                if(playerc.returnKeybind1().equals(this.spellToggle)){
                    playerc.setKeybind1("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(1, "", false));
                }
                if(playerc.returnKeybind5().equals(this.spellToggle)){
                    playerc.setKeybind5("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(5, "", false));
                }
                if(playerc.returnKeybind6().equals(this.spellToggle)){
                    playerc.setKeybind6("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(6, "", false));
                }
                if(playerc.returnKeybind7().equals(this.spellToggle)){
                    playerc.setKeybind7("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(7, "", false));
                }
                if(playerc.returnKeybind8().equals(this.spellToggle)){
                    playerc.setKeybind8("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(8, "", false));
                }
                if(playerc.returnKeybind9().equals(this.spellToggle)){
                    playerc.setKeybind9("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(9, "", false));
                }
            }
            playerc.setKeybind4(this.spellToggle);
            NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(4, this.spellToggle, false));
            player.sendMessage(new StringTextComponent("Keybind 4 Set to: " + new TranslationTextComponent(this.spellToggle).getString()), player.getUUID());
            this.spellToggle = "";
        }));
        addButton(buttonKey5 = new net.minecraft.client.gui.widget.button.Button(this.guiLeft - 10, this.guiTop + 70, 20, 20, new StringTextComponent("5"), $ -> {
            {
                if(playerc.returnKeybind2().equals(this.spellToggle)){
                    playerc.setKeybind2("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(2, "", false));
                }
                if(playerc.returnKeybind3().equals(this.spellToggle)){
                    playerc.setKeybind3("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(3, "", false));
                }
                if(playerc.returnKeybind4().equals(this.spellToggle)){
                    playerc.setKeybind4("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(4, "", false));
                }
                if(playerc.returnKeybind1().equals(this.spellToggle)){
                    playerc.setKeybind1("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(1, "", false));
                }
                if(playerc.returnKeybind6().equals(this.spellToggle)){
                    playerc.setKeybind6("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(6, "", false));
                }
                if(playerc.returnKeybind7().equals(this.spellToggle)){
                    playerc.setKeybind7("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(7, "", false));
                }
                if(playerc.returnKeybind8().equals(this.spellToggle)){
                    playerc.setKeybind8("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(8, "", false));
                }
                if(playerc.returnKeybind9().equals(this.spellToggle)){
                    playerc.setKeybind9("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(9, "", false));
                }
            }
            playerc.setKeybind5(this.spellToggle);
            NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(5, this.spellToggle, false));
            player.sendMessage(new StringTextComponent("Keybind 5 Set to: " + new TranslationTextComponent(this.spellToggle).getString()), player.getUUID());
            this.spellToggle = "";
        }));
        addButton(buttonKey6 = new net.minecraft.client.gui.widget.button.Button(this.guiLeft + 10, this.guiTop + 70, 20, 20, new StringTextComponent("6"), $ -> {
            {
                if(playerc.returnKeybind2().equals(this.spellToggle)){
                    playerc.setKeybind2("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(2, "", false));
                }
                if(playerc.returnKeybind3().equals(this.spellToggle)){
                    playerc.setKeybind3("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(3, "", false));
                }
                if(playerc.returnKeybind4().equals(this.spellToggle)){
                    playerc.setKeybind4("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(4, "", false));
                }
                if(playerc.returnKeybind5().equals(this.spellToggle)){
                    playerc.setKeybind5("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(5, "", false));
                }
                if(playerc.returnKeybind1().equals(this.spellToggle)){
                    playerc.setKeybind1("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(1, "", false));
                }
                if(playerc.returnKeybind7().equals(this.spellToggle)){
                    playerc.setKeybind7("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(7, "", false));
                }
                if(playerc.returnKeybind8().equals(this.spellToggle)){
                    playerc.setKeybind8("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(8, "", false));
                }
                if(playerc.returnKeybind9().equals(this.spellToggle)){
                    playerc.setKeybind9("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(9, "", false));
                }
            }
            playerc.setKeybind6(this.spellToggle);
            NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(6, this.spellToggle, false));
            player.sendMessage(new StringTextComponent("Keybind 6 Set to: " + new TranslationTextComponent(this.spellToggle).getString()), player.getUUID());
            this.spellToggle = "";
        }));
        addButton(buttonKey7 = new net.minecraft.client.gui.widget.button.Button(this.guiLeft - 30, this.guiTop + 90, 20, 20, new StringTextComponent("7"), $ -> {
            {
                if(playerc.returnKeybind2().equals(this.spellToggle)){
                    playerc.setKeybind2("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(2, "", false));
                }
                if(playerc.returnKeybind3().equals(this.spellToggle)){
                    playerc.setKeybind3("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(3, "", false));
                }
                if(playerc.returnKeybind4().equals(this.spellToggle)){
                    playerc.setKeybind4("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(4, "", false));
                }
                if(playerc.returnKeybind5().equals(this.spellToggle)){
                    playerc.setKeybind5("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(5, "", false));
                }
                if(playerc.returnKeybind6().equals(this.spellToggle)){
                    playerc.setKeybind6("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(6, "", false));
                }
                if(playerc.returnKeybind1().equals(this.spellToggle)){
                    playerc.setKeybind1("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(1, "", false));
                }
                if(playerc.returnKeybind8().equals(this.spellToggle)){
                    playerc.setKeybind8("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(8, "", false));
                }
                if(playerc.returnKeybind9().equals(this.spellToggle)){
                    playerc.setKeybind9("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(9, "", false));
                }
            }
            playerc.setKeybind7(this.spellToggle);
            NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(7, this.spellToggle, false));
            player.sendMessage(new StringTextComponent("Keybind 7 Set to: " + new TranslationTextComponent(this.spellToggle).getString()), player.getUUID());
            this.spellToggle = "";
        }));
        addButton(buttonKey8 = new net.minecraft.client.gui.widget.button.Button(this.guiLeft - 10, this.guiTop + 90, 20, 20, new StringTextComponent("8"), $ -> {
            {
                if(playerc.returnKeybind2().equals(this.spellToggle)){
                    playerc.setKeybind2("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(2, "", false));
                }
                if(playerc.returnKeybind3().equals(this.spellToggle)){
                    playerc.setKeybind3("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(3, "", false));
                }
                if(playerc.returnKeybind4().equals(this.spellToggle)){
                    playerc.setKeybind4("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(4, "", false));
                }
                if(playerc.returnKeybind5().equals(this.spellToggle)){
                    playerc.setKeybind5("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(5, "", false));
                }
                if(playerc.returnKeybind6().equals(this.spellToggle)){
                    playerc.setKeybind6("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(6, "", false));
                }
                if(playerc.returnKeybind7().equals(this.spellToggle)){
                    playerc.setKeybind7("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(7, "", false));
                }
                if(playerc.returnKeybind1().equals(this.spellToggle)){
                    playerc.setKeybind1("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(1, "", false));
                }
                if(playerc.returnKeybind9().equals(this.spellToggle)){
                    playerc.setKeybind9("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(9, "", false));
                }
            }
            playerc.setKeybind8(this.spellToggle);
            NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(8, this.spellToggle, false));
            player.sendMessage(new StringTextComponent("Keybind 8 Set to: " + new TranslationTextComponent(this.spellToggle).getString()), player.getUUID());
            this.spellToggle = "";
        }));
        addButton(buttonKey9 = new net.minecraft.client.gui.widget.button.Button(this.guiLeft + 10, this.guiTop + 90, 20, 20, new StringTextComponent("9"), $ -> {
            {
                if(playerc.returnKeybind2().equals(this.spellToggle)){
                    playerc.setKeybind2("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(1, "", false));
                }
                if(playerc.returnKeybind3().equals(this.spellToggle)){
                    playerc.setKeybind3("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(3, "", false));
                }
                if(playerc.returnKeybind4().equals(this.spellToggle)){
                    playerc.setKeybind4("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(4, "", false));
                }
                if(playerc.returnKeybind5().equals(this.spellToggle)){
                    playerc.setKeybind5("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(5, "", false));
                }
                if(playerc.returnKeybind6().equals(this.spellToggle)){
                    playerc.setKeybind6("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(6, "", false));
                }
                if(playerc.returnKeybind7().equals(this.spellToggle)){
                    playerc.setKeybind7("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(7, "", false));
                }
                if(playerc.returnKeybind8().equals(this.spellToggle)){
                    playerc.setKeybind8("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(8, "", false));
                }
                if(playerc.returnKeybind1().equals(this.spellToggle)){
                    playerc.setKeybind1("");
                    NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(1, "", false));
                }
            }
            playerc.setKeybind9(this.spellToggle);
            NetworkLoader.INSTANCE.sendToServer(new PacketKeybindSet(9, this.spellToggle, false));
            player.sendMessage(new StringTextComponent("Keybind 9 Set to: " + new TranslationTextComponent(this.spellToggle).getString()), player.getUUID());
            this.spellToggle = "";
        }));
    }


    @Override
    public void render(MatrixStack matrixStack, int p_render_1_, int p_render_2_, float p_render_3_) {
        super.render(matrixStack, p_render_1_, p_render_2_, p_render_3_);
        Minecraft mc = Minecraft.getInstance();
        LazyOptional<IPlayerHandler> player_cap = mc.player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler playerc = player_cap.orElse(new PlayerCapability());

        setSpellsBooleans(playerc);

        mc.textureManager.bind(new ResourceLocation(Main.MODID + ":textures/gui/magestatsbackground.png"));
        mc.gui.blit(matrixStack,this.guiLeft - 113, this.guiTop - 120, 0, 0, 227, 241);
        font.drawShadow(matrixStack, this.guiTitle.getString(), this.guiLeft - (font.width(this.guiTitle.getString()) / 2), this.guiTop - 105, 0x2B2B2B);


        for (Widget button : this.buttons) {
            button.renderButton(matrixStack, p_render_1_, p_render_2_, p_render_3_);
        }
        this.checkToggled(matrixStack);
        this.checkHovered(matrixStack, p_render_1_, p_render_2_);
        this.checkCovered(matrixStack);
    }

    public void checkHovered(MatrixStack matrixStack, int p_render_1_, int p_render_2_)
    {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.002F);
        GL11.glPushMatrix();

        for (Widget button : this.buttons) {
            if (button.isHovered() && button instanceof GuiButtonSpell) {
                renderTooltip(matrixStack, new StringTextComponent(new TranslationTextComponent("spell." + ((GuiButtonSpell) button).getTranslationName()).getString()), p_render_1_, p_render_2_);
            }
        }
        GL11.glPopMatrix();
    }

    public void checkCovered(MatrixStack matrixStack) {
        Minecraft mc = Minecraft.getInstance();
        mc.textureManager.bind(new ResourceLocation(Main.MODID, "textures/gui/spells.png"));
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.002F);
        GL11.glPushMatrix();
        for (Widget button : this.buttons) {
            if (button instanceof GuiButtonSpell) {
                if (!((GuiButtonSpell) button).hasSpell()) {
                    mc.gui.blit(matrixStack, ((GuiButtonSpell) button).widthIn, ((GuiButtonSpell) button).heightIn, 496, 496, 16, 16, 512, 512);
                }
            }
        }
        GL11.glPopMatrix();
    }

    public void checkToggled(MatrixStack matrixStack)
    {
        Minecraft mc = Minecraft.getInstance();
        mc.textureManager.bind(new ResourceLocation(Main.MODID, "textures/gui/spells.png"));
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.002F);
        GL11.glPushMatrix();
        for (Widget button : this.buttons) {
            if (button instanceof GuiButtonSpell && this.spellToggle.equalsIgnoreCase("spell." + ((GuiButtonSpell) button).getTranslationName())) {
                mc.gui.blit(matrixStack, ((GuiButtonSpell) button).widthIn, ((GuiButtonSpell) button).heightIn, 496, 480, 16,16, 512, 512);
            }
        }
        GL11.glPopMatrix();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}

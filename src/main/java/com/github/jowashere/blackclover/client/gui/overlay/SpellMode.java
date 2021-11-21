package com.github.jowashere.blackclover.client.gui.overlay;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.client.gui.player.spells.AbstractSpellScreen;
import com.github.jowashere.blackclover.util.helpers.SpellHelper;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.gui.GuiUtils;

@OnlyIn(Dist.CLIENT)
public class SpellMode {


    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent event){

        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;

        LazyOptional<IPlayerHandler> playerCapability = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerCapability.orElse(new PlayerCapability());

        if(event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR){

            int posX = mc.getWindow().getGuiScaledWidth()  - 23;
            int posY = mc.getWindow().getGuiScaledHeight() - 1;

            ResourceLocation WIDGETS = new ResourceLocation(Main.MODID + ":textures/gui/widgets.png");

            if(Minecraft.getInstance().screen instanceof AbstractSpellScreen){
                event.setCanceled(true);
            }

            if(player_cap.returnSpellModeToggle()){
                event.setCanceled(true);

                GlStateManager._pushMatrix();
                {
                    GlStateManager._color4f(1, 1, 1, 1);
                    GlStateManager._disableLighting();
                    mc.getTextureManager().bind(WIDGETS);

                    for (int i = 0; i < 9; i++)
                    {
                        BCMSpell spell = null;
                        int spellcd = 0;

                        if(i == 0){
                            spell = SpellHelper.getSpellFromString(player_cap.returnKeybind(1));
                            spellcd = player_cap.returnKeybindCD(1);
                        }else if (i == 1){
                            spell = SpellHelper.getSpellFromString(player_cap.returnKeybind(2));
                            spellcd = player_cap.returnKeybindCD(2);
                        }else if (i == 2){
                            spell = SpellHelper.getSpellFromString(player_cap.returnKeybind(3));
                            spellcd = player_cap.returnKeybindCD(3);
                        }else if (i == 3){
                            spell = SpellHelper.getSpellFromString(player_cap.returnKeybind(4));
                            spellcd = player_cap.returnKeybindCD(4);
                        }else if (i == 4){
                            spell = SpellHelper.getSpellFromString(player_cap.returnKeybind(5));
                            spellcd = player_cap.returnKeybindCD(5);
                        }else if (i == 5){
                            spell = SpellHelper.getSpellFromString(player_cap.returnKeybind(6));
                            spellcd = player_cap.returnKeybindCD(6);
                        }else if (i == 6){
                            spell = SpellHelper.getSpellFromString(player_cap.returnKeybind(7));
                            spellcd = player_cap.returnKeybindCD(7);
                        }else if (i == 7){
                            spell = SpellHelper.getSpellFromString(player_cap.returnKeybind(8));
                            spellcd = player_cap.returnKeybindCD(8);
                        }else if (i == 8){
                            spell = SpellHelper.getSpellFromString(player_cap.returnKeybind(9));
                            spellcd = player_cap.returnKeybindCD(9);
                        }

                        if(spell == null)
                        {
                            GuiUtils.drawTexturedModalRect((posX - 200 + (i * 50)) / 2, posY - 23, 0, 0, 23, 23, 0);
                            continue;
                        }

                        String number = "";

                        float cooldown = 23 - (float) (((spellcd - 10) / spell.getCooldown()) * 23);
                        float threshold = 23;
                        float charge = 23;

                        if(spellcd > 0 && spellcd - 10 > 0)
                            number = (int) spellcd - 10 + " ";

                        String nbtName = spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName();

                        // Setting their color based on their state
                        if (spellcd > 10)
                            GlStateManager._color4f(1, 0, 0, 1);
                        else if (spell.isToggle() && player.getPersistentData().getBoolean(nbtName))
                            GlStateManager._color4f(0, 0, 1, 1);
                        // Drawing the slot
                        GuiUtils.drawTexturedModalRect( event.getMatrixStack(), (posX - 200 + (i * 50)) / 2, posY - 23, 0, 0, 23, 23, 0);
                        // Reverting the color back to avoid future slots being wrongly colored
                        GlStateManager._color4f(1, 1, 1, 1);

                        // Drawing the spells
                        mc.getTextureManager().bind(spell.getResourceLocationForGUI());
                        GuiUtils.drawTexturedModalRect( event.getMatrixStack(), ((posX - 200 + (i * 50)) / 2) + 4, posY - 19, spell.getU(), spell.getV(), 16, 16, 0);

                        /*if(spell.isToggle())
                        {
                            GuiUtils.drawTexturedModalRect( event.getMatrixStack(), ((posX - 200 + (i * 50)) / 2) + 4, posY - 19, spell.getU(), spell.getV(), 16, (int) threshold, 0);
                        }
                        else if(spell.getCooldown() > 0)
                        {

                            mc.getTextureManager().bind(spell.getResourceLocationForGUI());
                            GuiUtils.drawTexturedModalRect( event.getMatrixStack(), ((posX - 200 + (i * 50)) / 2) + 4, posY - 19, spell.getU(), spell.getV(), 16, 16, 0);

                            if(spellcd < 10)
                            {
                                // Ready animation
                                GlStateManager._pushMatrix();
                                {
                                    double scale = 0.8 - (spellcd / 10.0);
                                    GlStateManager._color4f(0.2f, 0.8f, 0.4f, (float)(spellcd / 10));
                                    GlStateManager._translated((posX - 200 + (i * 50)) / 2, posY - 16, 1);
                                    GlStateManager._translated(12, 12, 0);
                                    GlStateManager._scaled(scale, scale, 1);
                                    GlStateManager._translated(-12, -12, 0);
                                    GuiUtils.drawTexturedModalRect( event.getMatrixStack(), ((posX - 200 + (i * 50)) / 2) + 4, posY - 19, spell.getU(), spell.getV(), 16, 16, 0);
                                }
                                GlStateManager._popMatrix();
                            }
                        }*/

                        // Reverting the color back to avoid future slots being wrongly colored
                        GlStateManager._color4f(1, 1, 1, 1);

                        // Drawing the ability icons
                        GlStateManager._translated(0, 0, 2);
                        mc.getTextureManager().bind(WIDGETS);
                    }

                    //GlStateManager.disableBlend();
                }
                GlStateManager._popMatrix();
            }
        }
    }


}

package com.github.jowashere.blackclover.client.gui.overlay;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.client.gui.player.spells.SpellsScreen;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.util.helpers.SpellHelper;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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

            if(Minecraft.getInstance().screen instanceof SpellsScreen){
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
                        ItemStack storedSword = ItemStack.EMPTY;
                        int spellcd = 0;

                        spell = SpellHelper.getSpellFromString(player_cap.returnKeybind(i + 1));
                        storedSword = player_cap.returnSwordSlot(i + 1);


                        if(spell != null){
                            String cdName = spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName() + "_cd";
                            spellcd = player.getPersistentData().getInt(cdName);
                        }else {
                            spellcd = 0;
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

                        if(!(player_cap.ReturnMagicAttribute().equals(AttributeInit.ANTI_MAGIC) && player.isCrouching())){
                            mc.getTextureManager().bind(spell.getResourceLocationForGUI());
                            GuiUtils.drawTexturedModalRect( event.getMatrixStack(), ((posX - 200 + (i * 50)) / 2) + 4, posY - 19, spell.getU(), spell.getV(), 16, 16, 0);
                        }else {

                            IBakedModel model = mc.getItemRenderer().getModel(storedSword, null, null);

                            if(!storedSword.isEmpty()){

                                event.getMatrixStack().translate(((posX - 200 + (i * 50)) / 2) + 4, posY - 19, 100.0F);
                                event.getMatrixStack().translate(8.0F, 8.0F, 0.0F);
                                event.getMatrixStack().scale(1.0F, -1.0F, 1.0F);
                                event.getMatrixStack().scale(16.0F, 16.0F, 16.0F);

                                IRenderTypeBuffer.Impl renderTypeBuffer = Minecraft.getInstance().renderBuffers().bufferSource();

                                mc.getItemRenderer().render(storedSword, ItemCameraTransforms.TransformType.GUI, false, event.getMatrixStack(), renderTypeBuffer, 100, OverlayTexture.NO_OVERLAY, model);
                                renderTypeBuffer.endBatch();

                                event.getMatrixStack().translate(-(((posX - 200 + (i * 50)) / 2) + 4), -(posY - 19), -100.0F);
                                event.getMatrixStack().translate(-8.0F, -8.0F, 0.0F);
                                event.getMatrixStack().scale(-1.0F, 1.0F, -1.0F);
                                event.getMatrixStack().scale(-16.0F, -16.0F, -16.0F);

                            }
                        }

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

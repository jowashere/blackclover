package com.github.jowashere.blackclover.client.curios.gui.overlay;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class ManaBar {

    private final ResourceLocation manaBar = new ResourceLocation(Main.MODID + ":textures/gui/mana_bars.png");
    private final int tex_width = 9, tex_height = 102, bar_width = 7, bar_height = 100;


    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent.Post event) {

        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {

            ClientPlayerEntity player = Minecraft.getInstance().player;
            LazyOptional<IPlayerHandler> mana_cap;
            mana_cap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler mana = mana_cap.orElse(new PlayerCapability());
            if (mana.returnMaxMana() > 0) {
                Minecraft mc = Minecraft.getInstance();
                int colour_x = ((mana.ReturnMagicAttribute().getAttributeColour() * 8) + 9); // CORRECT FORMULA ((chakra.returncolorChakra() * 8) + 9)
                mc.textureManager.bind(manaBar);
                mc.gui.blit(event.getMatrixStack(), 20, 130, 0, 0, tex_width, tex_height);
                if (mana.returnMaxMana() <= 0) {
                    int set_height = tex_height;
                    mc.gui.blit(event.getMatrixStack(), 20, 130, colour_x, 0, tex_width, set_height); // set_height
                } else {
                    float manaRatio = (mana.returnMana() / mana.returnMaxMana());
                    int set_height = (int) (tex_height * manaRatio);//(int) (bar_height * chakraratio)
                    int move_tex = (tex_height - set_height);
                    mc.gui.blit(event.getMatrixStack(), 20, 130 + move_tex, colour_x, move_tex, tex_width, set_height); // set_height
                }
            }
        }
    }

}

package com.github.jowashere.blackclover.client.gui.overlay;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
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
public class Quest {
    private final ResourceLocation questGUI = new ResourceLocation(Main.MODID + ":textures/gui/questGUI.png");
    private final int tex_width = 9, tex_height = 102, bar_width = 7, bar_height = 100;

    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent.Post event)
    {
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT)
        {
            ClientPlayerEntity player = Minecraft.getInstance().player;
            LazyOptional<IPlayerHandler> player_cap;
            player_cap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            Minecraft mc = Minecraft.getInstance();

            mc.textureManager.bind(questGUI);
            mc.gui.blit(event.getMatrixStack(), 20, 130, 0, 0, tex_width, tex_height);
        }
    }
}

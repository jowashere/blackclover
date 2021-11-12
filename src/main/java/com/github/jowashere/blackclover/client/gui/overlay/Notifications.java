package com.github.jowashere.blackclover.client.gui.overlay;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class Notifications {

    private static final ResourceLocation NOTIFS = new ResourceLocation(Main.MODID + ":textures/gui/notifs.png");

    private List<BCMSpell> toggledSpell = new ArrayList<>();

    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent.Post event) {

        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            Minecraft mc = Minecraft.getInstance();
            mc.textureManager.bind(NOTIFS);
            ClientPlayerEntity player = mc.player;
            LazyOptional<IPlayerHandler> playerc = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerc.orElse(new PlayerCapability());
            if (player_cap.returnManaSkinToggled())
            {
                mc.gui.blit(event.getMatrixStack(), 5, 2, 0,0, 16, 16);
            }
            if (player_cap.returnReinforcementToggled())
            {
                mc.gui.blit(event.getMatrixStack(), 25, 2, 0, 16, 16, 24);
            }

            this.setSpellNotifications(player);
            int i = 55;

            for (BCMSpell toggledSpell : this.toggledSpell) {
                mc.textureManager.bind(toggledSpell.getResourceLocationForGUI());
                mc.gui.blit(event.getMatrixStack(), 5, i, toggledSpell.getU(), toggledSpell.getV(), 16, 16, 256, 256);
                i += 20;
            }
            this.toggledSpell.clear();
        }
    }

    private void setSpellNotifications(PlayerEntity playerIn) {
        for (BCMSpell spell : BCMRegistry.SPELLS.getValues()) {
            if (spell.isToggle()) {
                String nbtName = spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName();
                if (playerIn.getPersistentData().getBoolean(nbtName)) {
                    this.toggledSpell.add(spell);
                }
            }
        }
    }

}

package com.github.jowashere.blackclover.util.helpers;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

public class FlyingControlsHelper {

    @OnlyIn(Dist.CLIENT)
    public static boolean isShiftDown() {
        return InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT)
                || InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isControlDown() {
        return InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_CONTROL)
                || InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_RIGHT_CONTROL);
    }
    @OnlyIn(Dist.CLIENT)
    public static boolean isTabDown() {
        return InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_TAB);
    }

    @SubscribeEvent
    public void OnClientTickEvent(final TickEvent.ClientTickEvent event) {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player != null) {
            LazyOptional<IPlayerHandler> playerCapability = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerCapability.orElse(new PlayerCapability());

            /*if (KeybindInit.SHINOBI_STATS.isPressed()) {
                Minecraft.getInstance().displayGuiScreen(new ShinobiStats());
            }*/
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void OnKeyInput(InputEvent.KeyInputEvent event) {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player != null) {
            LazyOptional<IPlayerHandler> player_cap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler playerc = player_cap.orElse(new PlayerCapability());

            if(player.getPersistentData().getBoolean("magic_flying")){
                if(player.isCrouching()){
                    player.setDeltaMovement(player.getDeltaMovement().x, -0.5, player.getDeltaMovement().z);
                }
            }


        }
    }
}

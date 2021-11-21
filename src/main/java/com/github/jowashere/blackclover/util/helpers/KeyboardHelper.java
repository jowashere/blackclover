package com.github.jowashere.blackclover.util.helpers;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.client.gui.player.PlayerStatsScreen;
import com.github.jowashere.blackclover.init.KeybindInit;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.PacketSpellModeToggle;
import com.github.jowashere.blackclover.networking.packets.PacketToggleInfusionBoolean;
import com.github.jowashere.blackclover.networking.packets.spells.PacketSpellCaller;
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

public class KeyboardHelper {

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
    public void onClientTickEvent(final TickEvent.ClientTickEvent event) {
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
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player != null) {
            LazyOptional<IPlayerHandler> player_cap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler playerc = player_cap.orElse(new PlayerCapability());

            if (KeybindInit.SPELL_MODE.isDown()) {
                    if (!playerc.returnSpellModeToggle()) {
                        playerc.setSpellModeToggle(true);
                        NetworkLoader.INSTANCE.sendToServer(new PacketSpellModeToggle(false, true, player.getId()));
                    } else{
                        playerc.setSpellModeToggle(false);
                        NetworkLoader.INSTANCE.sendToServer(new PacketSpellModeToggle(false, false, player.getId()));
                    }
            }

            if (playerc.returnSpellModeToggle()) {
                if (KeybindInit.KEYBIND1.isDown()) {
                    int key = 1;
                    if(playerc.returnKeybindCD(key) <= 0){
                        if(SpellHelper.getSpellFromString(playerc.returnKeybind(key)) != null){
                            NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", key, player.getId()));
                           //playerc.setKeybindCD(key, SpellHelper.getSpellFromName(playerc.returnKeybind(key)).getCooldown());
                        }
                    }
                }
                if (KeybindInit.KEYBIND2.isDown()) {
                    int key = 2;
                    if(playerc.returnKeybindCD(key) <= 0){
                        if(SpellHelper.getSpellFromString(playerc.returnKeybind(key)) != null){
                            NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", key, player.getId()));
                            //playerc.setKeybindCD(key, SpellHelper.getSpellFromName(playerc.returnKeybind(key)).getCooldown());
                        }
                    }
                }
                if (KeybindInit.KEYBIND3.isDown()) {
                    int key = 3;
                    if(playerc.returnKeybindCD(key) <= 0){
                        if(SpellHelper.getSpellFromString(playerc.returnKeybind(key)) != null){
                            NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", key, player.getId()));
                            //playerc.setKeybindCD(key, SpellHelper.getSpellFromName(playerc.returnKeybind(key)).getCooldown());
                        }
                    }
                }
                if (KeybindInit.KEYBIND4.isDown()) {
                    int key = 4;
                    if(playerc.returnKeybindCD(key) <= 0){
                        if(SpellHelper.getSpellFromString(playerc.returnKeybind(key)) != null){
                            NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", key, player.getId()));
                            //playerc.setKeybindCD(key, SpellHelper.getSpellFromName(playerc.returnKeybind(key)).getCooldown());
                        }
                    }
                }
                if (KeybindInit.KEYBIND5.isDown()) {
                    int key = 5;
                    if(playerc.returnKeybindCD(key) <= 0){
                        if(SpellHelper.getSpellFromString(playerc.returnKeybind(key)) != null){
                            NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", key, player.getId()));
                            //playerc.setKeybindCD(key, SpellHelper.getSpellFromName(playerc.returnKeybind(key)).getCooldown());
                        }
                    }
                }
                if (KeybindInit.KEYBIND6.isDown()) {
                    int key = 6;
                    if(playerc.returnKeybindCD(key) <= 0){
                        if(SpellHelper.getSpellFromString(playerc.returnKeybind(key)) != null){
                            NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", key, player.getId()));
                            //playerc.setKeybindCD(key, SpellHelper.getSpellFromName(playerc.returnKeybind(key)).getCooldown());
                        }
                    }
                }
                if (KeybindInit.KEYBIND7.isDown()) {
                    int key = 7;
                    if(playerc.returnKeybindCD(key) <= 0){
                        if(SpellHelper.getSpellFromString(playerc.returnKeybind(key)) != null){
                            NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", key, player.getId()));
                            //playerc.setKeybindCD(key, SpellHelper.getSpellFromName(playerc.returnKeybind(key)).getCooldown());
                        }
                    }
                }
                if (KeybindInit.KEYBIND8.isDown()) {
                    int key = 8;
                    if(playerc.returnKeybindCD(key) <= 0){
                        if(SpellHelper.getSpellFromString(playerc.returnKeybind(key)) != null){
                            NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", key, player.getId()));
                            //playerc.setKeybindCD(key, SpellHelper.getSpellFromName(playerc.returnKeybind(key)).getCooldown());
                        }
                    }
                }
                if (KeybindInit.KEYBIND9.isDown()) {
                    int key = 9;
                    if(playerc.returnKeybindCD(key) <= 0){
                        if(SpellHelper.getSpellFromString(playerc.returnKeybind(key)) != null){
                            NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", key, player.getId()));
                            //playerc.setKeybindCD(key, SpellHelper.getSpellFromName(playerc.returnKeybind(key)).getCooldown());
                        }
                    }
                }
            }

            if (KeybindInit.MANA_SKIN.isDown()) {
                if (!playerc.returnManaSkinToggled()) {
                    playerc.setManaSkinToggled(true);
                    NetworkLoader.INSTANCE.sendToServer(new PacketToggleInfusionBoolean(1, false, true, player.getId()));
                } else {
                    playerc.setManaSkinToggled(false);
                    NetworkLoader.INSTANCE.sendToServer(new PacketToggleInfusionBoolean(1, false, false, player.getId()));
                }
            }
            if (KeybindInit.REINFORCEMENT.isDown()) {
                if (!playerc.returnReinforcementToggled()) {
                    playerc.setReinforcementToggled(true);
                    NetworkLoader.INSTANCE.sendToServer(new PacketToggleInfusionBoolean(2, false, true, player.getId()));
                } else {
                    playerc.setReinforcementToggled(false);
                    NetworkLoader.INSTANCE.sendToServer(new PacketToggleInfusionBoolean(2, false, false, player.getId()));
                }
            }

            if (KeybindInit.MAGIC_MENU.isDown()){
                Minecraft.getInstance().setScreen(new PlayerStatsScreen());
            }
        }
    }
}

package com.github.jowashere.blackclover.util.helpers;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.client.gui.player.PlayerSpellsScreen;
import com.github.jowashere.blackclover.client.gui.player.PlayerStatsScreen;
import com.github.jowashere.blackclover.common.spells.SpellCaller;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.init.KeybindInit;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.PacketSpellModeToggle;
import com.github.jowashere.blackclover.networking.packets.PacketToggleInfusionBoolean;
import com.github.jowashere.blackclover.networking.packets.mana.PacketManaAddition;
import com.github.jowashere.blackclover.networking.packets.spells.PacketKeybindCD;
import com.github.jowashere.blackclover.networking.packets.spells.PacketSpellCaller;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;
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
                    if(playerc.returnKeybind1CD() <= 0){
                        if(SpellHelper.getSpellFromName(playerc.returnKeybind1()) != null){
                            SpellCaller.SpellCaller(player, playerc.returnKeybind1());
                            NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("",1, player.getId()));
                        }
                    }
                }
                if (KeybindInit.KEYBIND2.isDown()) {
                    if(playerc.returnKeybind2CD() <= 0){
                        if(SpellHelper.getSpellFromName(playerc.returnKeybind2()) != null){
                            SpellCaller.SpellCaller(player, playerc.returnKeybind2());
                            NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", 2, player.getId()));
                        }
                    }
                }
                if (KeybindInit.KEYBIND3.isDown()) {
                    if(playerc.returnKeybind3CD() <= 0){
                        if(SpellHelper.getSpellFromName(playerc.returnKeybind3()) != null){
                            SpellCaller.SpellCaller(player, playerc.returnKeybind3());
                            NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", 3, player.getId()));
                        }
                    }
                }
                if (KeybindInit.KEYBIND4.isDown()) {
                    if(playerc.returnKeybind4CD() <= 0){
                        if(SpellHelper.getSpellFromName(playerc.returnKeybind4()) != null){
                            SpellCaller.SpellCaller(player, playerc.returnKeybind4());
                            NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", 4, player.getId()));
                        }
                    }
                }
                if (KeybindInit.KEYBIND5.isDown()) {
                    if(playerc.returnKeybind5CD() <= 0){
                        if(SpellHelper.getSpellFromName(playerc.returnKeybind5()) != null){
                            SpellCaller.SpellCaller(player, playerc.returnKeybind5());
                            NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", 5, player.getId()));
                        }
                    }
                }
                if (KeybindInit.KEYBIND6.isDown()) {
                    if(playerc.returnKeybind6CD() <= 0){
                        if(SpellHelper.getSpellFromName(playerc.returnKeybind6()) != null){
                            SpellCaller.SpellCaller(player, playerc.returnKeybind6());
                            NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", 6, player.getId()));
                       }
                    }
                }
                if (KeybindInit.KEYBIND7.isDown()) {
                    if(playerc.returnKeybind7CD() <= 0){
                        if(SpellHelper.getSpellFromName(playerc.returnKeybind7()) != null){
                            SpellCaller.SpellCaller(player, playerc.returnKeybind7());
                            NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", 7, player.getId()));
                        }
                    }
                }
                if (KeybindInit.KEYBIND8.isDown()) {
                    if(playerc.returnKeybind8CD() <= 0){
                        if(SpellHelper.getSpellFromName(playerc.returnKeybind8()) != null){
                            SpellCaller.SpellCaller(player, playerc.returnKeybind8());
                            NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", 8, player.getId()));
                        }
                    }
                }
                if (KeybindInit.KEYBIND9.isDown()) {
                    if(playerc.returnKeybind9CD() <= 0){
                        if(SpellHelper.getSpellFromName(playerc.returnKeybind9()) != null){
                            SpellCaller.SpellCaller(player, playerc.returnKeybind9());
                            NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", 9, player.getId()));
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

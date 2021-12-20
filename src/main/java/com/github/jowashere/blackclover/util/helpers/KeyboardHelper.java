package com.github.jowashere.blackclover.util.helpers;

import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.client.gui.player.PlayerStatsScreen;
import com.github.jowashere.blackclover.events.PlayerEvents;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.init.KeybindInit;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.PacketSpellModeToggle;
import com.github.jowashere.blackclover.networking.packets.spells.PacketGrimoireSword;
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
               if(!isShiftDown()){
                   if (KeybindInit.KEYBIND1.isDown()) {
                       int key = 1;
                       AbstractSpell spell = SpellHelper.getSpellFromString(playerc.returnKeybind(key));
                       if(spell == null)
                           return;
                       String cdName = spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName() + "_cd";
                       if(player.getPersistentData().getInt(cdName) <= 0){
                           if(SpellHelper.getSpellFromString(playerc.returnKeybind(key)) != null){
                               NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", key, player.getId()));
                           }
                       }
                   }
                   if (KeybindInit.KEYBIND2.isDown()) {
                       int key = 2;
                       AbstractSpell spell = SpellHelper.getSpellFromString(playerc.returnKeybind(key));
                       if(spell == null)
                           return;
                       String cdName = spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName() + "_cd";
                       if(player.getPersistentData().getInt(cdName) <= 0){
                           if(SpellHelper.getSpellFromString(playerc.returnKeybind(key)) != null){
                               NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", key, player.getId()));
                           }
                       }
                   }
                   if (KeybindInit.KEYBIND3.isDown()) {
                       int key = 3;
                       AbstractSpell spell = SpellHelper.getSpellFromString(playerc.returnKeybind(key));
                       if(spell == null)
                           return;
                       String cdName = spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName() + "_cd";
                       if(player.getPersistentData().getInt(cdName) <= 0){
                           if(SpellHelper.getSpellFromString(playerc.returnKeybind(key)) != null){
                               NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", key, player.getId()));
                           }
                       }
                   }
                   if (KeybindInit.KEYBIND4.isDown()) {
                       int key = 4;
                       AbstractSpell spell = SpellHelper.getSpellFromString(playerc.returnKeybind(key));
                       if(spell == null)
                           return;
                       String cdName = spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName() + "_cd";
                       if(player.getPersistentData().getInt(cdName) <= 0){
                           if(SpellHelper.getSpellFromString(playerc.returnKeybind(key)) != null){
                               NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", key, player.getId()));
                           }
                       }
                   }
                   if (KeybindInit.KEYBIND5.isDown()) {
                       int key = 5;
                       AbstractSpell spell = SpellHelper.getSpellFromString(playerc.returnKeybind(key));
                       if(spell == null)
                           return;
                       String cdName = spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName() + "_cd";
                       if(player.getPersistentData().getInt(cdName) <= 0){
                           if(SpellHelper.getSpellFromString(playerc.returnKeybind(key)) != null){
                               NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", key, player.getId()));
                           }
                       }
                   }
                   if (KeybindInit.KEYBIND6.isDown()) {
                       int key = 6;
                       AbstractSpell spell = SpellHelper.getSpellFromString(playerc.returnKeybind(key));
                       if(spell == null)
                           return;
                       String cdName = spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName() + "_cd";
                       if(player.getPersistentData().getInt(cdName) <= 0){
                           if(SpellHelper.getSpellFromString(playerc.returnKeybind(key)) != null){
                               NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", key, player.getId()));
                           }
                       }
                   }
                   if (KeybindInit.KEYBIND7.isDown()) {
                       int key = 7;
                       AbstractSpell spell = SpellHelper.getSpellFromString(playerc.returnKeybind(key));
                       if(spell == null)
                           return;

                       String cdName = spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName() + "_cd";
                       if(player.getPersistentData().getInt(cdName) <= 0){
                           if(SpellHelper.getSpellFromString(playerc.returnKeybind(key)) != null){
                               NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", key, player.getId()));
                           }
                       }
                   }
                   if (KeybindInit.KEYBIND8.isDown()) {
                       int key = 8;
                       AbstractSpell spell = SpellHelper.getSpellFromString(playerc.returnKeybind(key));
                       if(spell == null)
                           return;
                       String cdName = spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName() + "_cd";
                       if(player.getPersistentData().getInt(cdName) <= 0){
                           if(SpellHelper.getSpellFromString(playerc.returnKeybind(key)) != null){
                               NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", key, player.getId()));
                           }
                       }
                   }
                   if (KeybindInit.KEYBIND9.isDown()) {
                       int key = 9;
                       AbstractSpell spell = SpellHelper.getSpellFromString(playerc.returnKeybind(key));
                       if(spell == null)
                           return;
                       String cdName = spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName() + "_cd";
                       if(player.getPersistentData().getInt(cdName) <= 0){
                           if(SpellHelper.getSpellFromString(playerc.returnKeybind(key)) != null){
                               NetworkLoader.INSTANCE.sendToServer(new PacketSpellCaller("", key, player.getId()));
                           }
                       }
                   }
               }else {
                   if (KeybindInit.KEYBIND1.isDown()) {
                       int key = 1;
                       if (playerc.ReturnMagicAttribute().equals(AttributeInit.ANTI_MAGIC)){
                           NetworkLoader.INSTANCE.sendToServer(new PacketGrimoireSword(key, true));
                       }
                   }
                   if (KeybindInit.KEYBIND2.isDown()) {
                       int key = 2;
                       if (playerc.ReturnMagicAttribute().equals(AttributeInit.ANTI_MAGIC)){
                           NetworkLoader.INSTANCE.sendToServer(new PacketGrimoireSword(key, true));
                       }
                   }
                   if (KeybindInit.KEYBIND3.isDown()) {
                       int key = 3;
                       if (playerc.ReturnMagicAttribute().equals(AttributeInit.ANTI_MAGIC)){
                           NetworkLoader.INSTANCE.sendToServer(new PacketGrimoireSword(key, true));
                       }
                   }
                   if (KeybindInit.KEYBIND4.isDown()) {
                       int key = 4;
                       if (playerc.ReturnMagicAttribute().equals(AttributeInit.ANTI_MAGIC)){
                           NetworkLoader.INSTANCE.sendToServer(new PacketGrimoireSword(key, true));
                       }
                   }
                   if (KeybindInit.KEYBIND5.isDown()) {
                       int key = 5;
                       if (playerc.ReturnMagicAttribute().equals(AttributeInit.ANTI_MAGIC)){
                           NetworkLoader.INSTANCE.sendToServer(new PacketGrimoireSword(key, true));
                       }
                   }
                   if (KeybindInit.KEYBIND6.isDown()) {
                       int key = 6;
                       if (playerc.ReturnMagicAttribute().equals(AttributeInit.ANTI_MAGIC)){
                           NetworkLoader.INSTANCE.sendToServer(new PacketGrimoireSword(key, true));
                       }
                   }
                   if (KeybindInit.KEYBIND7.isDown()) {
                       int key = 7;
                       if (playerc.ReturnMagicAttribute().equals(AttributeInit.ANTI_MAGIC)){
                           NetworkLoader.INSTANCE.sendToServer(new PacketGrimoireSword(key, true));
                       }
                   }
                   if (KeybindInit.KEYBIND8.isDown()) {
                       int key = 8;
                       if (playerc.ReturnMagicAttribute().equals(AttributeInit.ANTI_MAGIC)){
                           NetworkLoader.INSTANCE.sendToServer(new PacketGrimoireSword(key, true));
                       }
                   }
                   if (KeybindInit.KEYBIND9.isDown()) {
                       int key = 9;
                       if (playerc.ReturnMagicAttribute().equals(AttributeInit.ANTI_MAGIC)){
                           NetworkLoader.INSTANCE.sendToServer(new PacketGrimoireSword(key, true));
                       }
                   }
               }
            }

            if (KeybindInit.MANA_SKIN.isDown()) {
                PlayerEvents.toggleManaSkin(player);
            }
            if (KeybindInit.REINFORCEMENT.isDown()) {
                PlayerEvents.toggleReinforcement(player);
            }

            if (KeybindInit.MAGIC_MENU.isDown()){
                Minecraft.getInstance().setScreen(new PlayerStatsScreen());
            }
        }
    }
}

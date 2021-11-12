package com.github.jowashere.blackclover.common.spells;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.PacketToggleInfusionBoolean;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

public class EffectSpells {

    public static final int effectDuration = 55;

    public static void ManaSkin(PlayerEntity playerIn, int manaAmount, int primaryModifier, int secondaryModifier) {
        LazyOptional<IPlayerHandler> capabilities = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
        if (playercap.returnMana() > manaAmount) {
            playerIn.getPersistentData().putInt("manaskintick", playerIn.getPersistentData().getInt("manaskintick") + 1);
            if (playerIn.getPersistentData().getInt("manaskintick") >= 20) {
                if (playercap.returnMagicLevel() >= 15) {
                    playerIn.addEffect(new EffectInstance(Effects.SATURATION, effectDuration, secondaryModifier));
                }
                playercap.addMana((float) -manaAmount);
                playerIn.getPersistentData().putInt("manaskintick", 0);
            }
        } else {
            playercap.setManaSkinToggled(false);
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) playerIn), new PacketToggleInfusionBoolean(1, true,false, playerIn.getId()));
            playerIn.displayClientMessage(new TranslationTextComponent("spell." + Main.MODID + ".message.notenoughmana"), true);
        }
    }


    public static void Reinforcement(PlayerEntity playerIn, int manaAmount, int primaryModifier, int secondaryModifier) {
        LazyOptional<IPlayerHandler> capabilities = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
        if (playercap.returnMana() > manaAmount) {
            playerIn.getPersistentData().putInt("reinforcementtick", playerIn.getPersistentData().getInt("reinforcementtick") + 1);
            if (playerIn.getPersistentData().getInt("reinforcementtick") >= 20) {
                if(playerIn.isSprinting()){
                    playerIn.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, effectDuration, Integer.min(primaryModifier/2, 15), false, false, false));
                }
                playerIn.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, effectDuration, Integer.min(primaryModifier/2, 20), false, false, false));
                if (playercap.returnMagicLevel() >= 15) {
                    playerIn.addEffect(new EffectInstance(Effects.DIG_SPEED, effectDuration, Integer.min(secondaryModifier, 16), false, false, false));
                }
                if (!playerIn.isOnGround()){
                    playerIn.addEffect(new EffectInstance(Effects.JUMP, 60, Integer.min(secondaryModifier/2, 5), false, false, false));
                }
                playercap.addMana((float) -manaAmount);
                playerIn.getPersistentData().putInt("reinforcementtick", 0);
            }
        } else {
            playercap.setReinforcementToggled(false);
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) playerIn), new PacketToggleInfusionBoolean(2, true,false, playerIn.getId()));
            playerIn.displayClientMessage(new TranslationTextComponent("spell." + Main.MODID + ".message.notenoughmana"), true);
        }
    }

}

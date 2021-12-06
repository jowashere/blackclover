package com.github.jowashere.blackclover.init.spells;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.entities.spells.antimagic.BlackSlashEntity;
import com.github.jowashere.blackclover.init.ItemInit;
import com.github.jowashere.blackclover.init.ModAttributes;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.spells.PacketIntSpellNBTSync;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;
import java.util.UUID;

public class AntiMagicSpells {

    private static final AttributeModifier REACH_MODIFIER = new AttributeModifier(UUID.fromString("ff5c8feb-6598-4d30-81de-e1ca1084f51b"), "Reach Modifier", 4.5, AttributeModifier.Operation.ADDITION);

    public static void registerSpells(BCMRegistry.SpellRegistry spellRegistry, IBCMPlugin pluginIn) {
        spellRegistry.register(new BCMSpell(pluginIn, "bull_thrust", BCMSpell.Type.ANTI_MAGIC, 15F, 100, false, 0, 0, false, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

            LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

            if (!playerIn.level.isClientSide) {
                Vector3d speed = BCMHelper.Propulsion(playerIn, 2.5, 2.5);
                playerIn.setDeltaMovement(speed.x, 0.3, speed.z);
                playerIn.hurtMarked = true;
                playerIn.hasImpulse = true;
                playerIn.swing(Hand.MAIN_HAND, true);

                String nbtName = "bull_thrust_dmg";
                playerIn.getPersistentData().putInt(nbtName, 6);
                NetworkLoader.INSTANCE.send(PacketDistributor.ALL.noArg(), new PacketIntSpellNBTSync(playerIn.getId(), nbtName, 6));

            }
        }).setExtraSpellChecks((playerIn -> {
            ItemStack mainItem = playerIn.getItemInHand(Hand.MAIN_HAND);

            return (mainItem.getItem().equals(ItemInit.DEMON_SLAYER.get()) || mainItem.getItem().equals(ItemInit.DEMON_DWELLER.get())) && mainItem.getOrCreateTag().getBoolean("antimagic");
        })).setCheckFailMsg("Demon Dweller/Slayer Sword needs to be in hand."));

        spellRegistry.register(new BCMSpell(pluginIn, "black_slash", BCMSpell.Type.ANTI_MAGIC, 25F, 50, false, 0, 16, false, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

            if (!playerIn.level.isClientSide) {
                BlackSlashEntity entity = new BlackSlashEntity(playerIn.level, playerIn, manaIn);
                entity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.8F, 1.0F);
                playerIn.level.addFreshEntity(entity);
                playerIn.swing(Hand.MAIN_HAND, true);

            }
        }).setExtraSpellChecks((playerIn -> {
            ItemStack mainItem = playerIn.getItemInHand(Hand.MAIN_HAND);

            return (mainItem.getItem().equals(ItemInit.DEMON_DWELLER.get()) && mainItem.getOrCreateTag().getBoolean("antimagic"));
        })).setCheckFailMsg("Demon Dweller Sword needs to be in hand."));
        spellRegistry.register(new BCMSpell(pluginIn, "black_divider", BCMSpell.Type.ANTI_MAGIC, 0.25F, 200, false, 0, 80, true, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

            LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

            if (!playerIn.level.isClientSide) {
                ItemStack stack = playerIn.getItemInHand(Hand.MAIN_HAND);

                if(stack.getItem().equals(ItemInit.DEMON_SLAYER.get())){
                    stack.getOrCreateTag().putBoolean("black_divider", true);
                }
            }
        }).setExtraSpellChecks((playerIn -> {
            ItemStack mainItem = playerIn.getItemInHand(Hand.MAIN_HAND);

            return (mainItem.getItem().equals(ItemInit.DEMON_SLAYER.get()) && mainItem.getOrCreateTag().getBoolean("antimagic"));
        })).addStartEventListener(((playerIn, manaIn) -> {

            playerIn.getAttribute(ModAttributes.ATTACK_RANGE.get()).addTransientModifier((REACH_MODIFIER));

        })).addCancelEventListener((playerIn -> {

            playerIn.getAttribute(ModAttributes.ATTACK_RANGE.get()).removeModifier(REACH_MODIFIER);

        })).checkOnlyToToggle(false).setCheckFailMsg("Demon Slayer Sword needs to be in hand."));
        spellRegistry.register(new BCMSpell(pluginIn, "causality_break", BCMSpell.Type.ANTI_MAGIC, 20F, 200, false, 0, 32, false, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

            LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

            List<LivingEntity> entities= BCMHelper.GetEntitiesNear(playerIn.blockPosition(), playerIn.level, (int) (5 + (player_cap.ReturnMagicLevel() / 10)), LivingEntity.class);
            entities.remove(playerIn);

            entities.forEach(entity -> {
                entity.removeAllEffects();
                if (playerIn.level instanceof ServerWorld) {
                    ((ServerWorld) playerIn.level).sendParticles(ParticleTypes.SMOKE, entity.getX(), entity.getY(), entity.getZ(), (int) 25, 0, 0, 0, 0.25);
                }
            });

        }).setExtraSpellChecks((playerIn -> {
            ItemStack mainItem = playerIn.getItemInHand(Hand.MAIN_HAND);

            return (mainItem.getItem().equals(ItemInit.DEMON_DESTROYER.get()) && mainItem.getOrCreateTag().getBoolean("antimagic"));
        })).setCheckFailMsg("Demon Destroyer Sword needs to be in hand."));
        spellRegistry.register(new BCMSpell(pluginIn, "causality_break_self", BCMSpell.Type.ANTI_MAGIC, 20F, 200, false, 0, 48, false, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

            LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

            playerIn.removeAllEffects();
            if (playerIn.level instanceof ServerWorld) {
                ((ServerWorld) playerIn.level).sendParticles(ParticleTypes.SMOKE, playerIn.getX(), playerIn.getY(), playerIn.getZ(), (int) 25, 0, 0, 0, 0.25);
            }

        }).setExtraSpellChecks((playerIn -> {
            ItemStack mainItem = playerIn.getItemInHand(Hand.MAIN_HAND);
            boolean holdingAnti = mainItem.getItem().equals(ItemInit.DEMON_DESTROYER.get());
            boolean antiHasTag = mainItem.getOrCreateTag().getBoolean("antimagic");
            boolean able = (holdingAnti && antiHasTag);
            return able;
        })).setCheckFailMsg("Demon Destroyer Sword needs to be in hand."));

        spellRegistry.register(new BCMSpell(pluginIn, "black_mode", BCMSpell.Type.ANTI_MAGIC, 0.75F, 12400, false, 0, 112, true, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

            LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

            playerIn.fallDistance = 0;

        }).setExtraSpellChecks((playerIn -> {
            ItemStack mainItem = playerIn.getItemInHand(Hand.MAIN_HAND);
            boolean holdingAnti = (mainItem.getItem() instanceof SwordItem);
            boolean isAnti = mainItem.getOrCreateTag().getBoolean("antimagic");
            return isAnti && holdingAnti;

        })).addStartEventListener(((playerIn, manaIn) -> {
            if(!playerIn.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(getBlackModeSpeedModifier(playerIn)))
                playerIn.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(getBlackModeSpeedModifier(playerIn));

            if(!playerIn.getAttribute(Attributes.ARMOR).hasModifier(getBlackModeModifier(playerIn)))
                playerIn.getAttribute(Attributes.ARMOR).addTransientModifier(getBlackModeModifier(playerIn));

            if(!playerIn.getAttribute(Attributes.ARMOR_TOUGHNESS).hasModifier(getBlackModeModifier(playerIn)))
                playerIn.getAttribute(Attributes.ARMOR_TOUGHNESS).addTransientModifier(getBlackModeModifier(playerIn));

            if(!playerIn.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getBlackModeStrengthModifier(playerIn)))
                playerIn.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(getBlackModeStrengthModifier(playerIn));

            if(!playerIn.getAttribute(ModAttributes.DAMAGE_REDUCTION.get()).hasModifier(getBlackModeDamageResModifier(playerIn)))
            playerIn.getAttribute(ModAttributes.DAMAGE_REDUCTION.get()).addTransientModifier(getBlackModeDamageResModifier(playerIn));

        })).addCancelEventListener((playerIn -> {
            playerIn.getPersistentData().putInt("black_mode_fatigue", 900);
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(()-> (ServerPlayerEntity) playerIn), new PacketIntSpellNBTSync(playerIn.getId(), "black_mode_fatigue", 900));

            if(playerIn.getAttribute(Attributes.ARMOR).hasModifier(getBlackModeModifier(playerIn)))
                playerIn.getAttribute(Attributes.ARMOR).removeModifier(getBlackModeModifier(playerIn));

            if(playerIn.getAttribute(Attributes.ARMOR_TOUGHNESS).hasModifier(getBlackModeModifier(playerIn)))
                playerIn.getAttribute(Attributes.ARMOR_TOUGHNESS).removeModifier(getBlackModeModifier(playerIn));

            if(playerIn.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(getBlackModeSpeedModifier(playerIn)))
                playerIn.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(getBlackModeSpeedModifier(playerIn));

            if(playerIn.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getBlackModeStrengthModifier(playerIn)))
                playerIn.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(getBlackModeStrengthModifier(playerIn));

            if(playerIn.getAttribute(ModAttributes.DAMAGE_REDUCTION.get()).hasModifier(getBlackModeDamageResModifier(playerIn)))
                playerIn.getAttribute(ModAttributes.DAMAGE_REDUCTION.get()).removeModifier(getBlackModeDamageResModifier(playerIn));

        })).setToggleTimer(2800).setCheckFailMsg("An Anti-Magic Sword needs to be in hand."));

        spellRegistry.register(new BCMSpell(pluginIn, "black_meteorite", BCMSpell.Type.ANTI_MAGIC, 35F, 800, false, 0, 64, false, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

            if (!playerIn.level.isClientSide) {
                Vector3d speed = BCMHelper.Propulsion(playerIn, 7, 7, 7);
                playerIn.setDeltaMovement(speed.x, speed.y, speed.z);
                playerIn.hurtMarked = true;
                playerIn.hasImpulse = true;
                playerIn.swing(Hand.MAIN_HAND, true);

                String nbtName = "black_meteorite_dmg";
                playerIn.getPersistentData().putInt(nbtName, 10);
                NetworkLoader.INSTANCE.send(PacketDistributor.ALL.noArg(), new PacketIntSpellNBTSync(playerIn.getId(), nbtName, 10));
                playerIn.startFallFlying();
            }
        }).setExtraSpellChecks((playerIn -> {
            ItemStack mainItem = playerIn.getItemInHand(Hand.MAIN_HAND);

            return (mainItem.getItem().equals(ItemInit.DEMON_SLAYER.get()) || mainItem.getItem().equals(ItemInit.DEMON_DWELLER.get())) && mainItem.getOrCreateTag().getBoolean("antimagic") && playerIn.getPersistentData().getBoolean("blackclover_black_mode");
        })).setCheckFailMsg("Need to be in Black Mode and holding Demon Dweller/Slayer Sword."));
    }

    private static AttributeModifier getBlackModeModifier(PlayerEntity playerEntity) {
        LazyOptional<IPlayerHandler> playerInCap = playerEntity.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());
        return new AttributeModifier(UUID.fromString("2096d841-4fe1-4678-ba90-80ff10f115b8"), "Black Mode Modifier"
                , 5 + ((float)player_cap.ReturnMagicLevel()/15), AttributeModifier.Operation.ADDITION);
    }

    private static AttributeModifier getBlackModeSpeedModifier(PlayerEntity playerEntity) {
        LazyOptional<IPlayerHandler> playerInCap = playerEntity.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());
        return new AttributeModifier(UUID.fromString("92ad75d4-778e-4c24-a473-40e7cf3a94c7"), "Black Mode Speed Modifier",
                0.035 * player_cap.ReturnMagicLevel(), AttributeModifier.Operation.MULTIPLY_BASE);
    }

    private static AttributeModifier getBlackModeDamageResModifier(PlayerEntity playerEntity) {
        LazyOptional<IPlayerHandler> playerInCap = playerEntity.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());
        return new AttributeModifier(UUID.fromString("50a53c0c-5dda-4784-8511-a4f8dbee8218"), "Black Mode Attack Damage Modifier",
                ((float)player_cap.ReturnMagicLevel()/100)*0.45, AttributeModifier.Operation.ADDITION);

    }

    private static AttributeModifier getBlackModeStrengthModifier(PlayerEntity playerEntity) {
        LazyOptional<IPlayerHandler> playerInCap = playerEntity.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());
        return new AttributeModifier(UUID.fromString("760496d9-3363-4cf7-bba6-2e13efc11a63"), "Black Mode Damage Resistance Modifier",
                5 + ((float)player_cap.ReturnMagicLevel()/10), AttributeModifier.Operation.ADDITION);

    }

}

package com.github.jowashere.blackclover.init.spells;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.entities.spells.darkness.AvidyaSlashEntity;
import com.github.jowashere.blackclover.entities.spells.darkness.BlackHoleEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.init.ModAttributes;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import com.github.jowashere.blackclover.util.helpers.SpellHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;
import java.util.UUID;

public class DarknessSpells {

    private static final AttributeModifier COCOON_DE = new AttributeModifier(UUID.fromString("ad388521-c053-4a67-a0d9-ff57379a2c68"), "Cocoon Speed", -2000, AttributeModifier.Operation.ADDITION);

    public static void registerSpells(BCMRegistry.SpellRegistry spellRegistry, IBCMPlugin pluginIn) {

        spellRegistry.register(new BCMSpell(pluginIn, "dark_cloaked_blade", BCMSpell.Type.DARKNESS_MAGIC, 0.17F, 40, false, 48, 0, true, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

            LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

            if (!playerIn.level.isClientSide) {
                ItemStack item = playerIn.getItemInHand(Hand.MAIN_HAND);
                if(item.getItem() instanceof SwordItem){
                    int level = player_cap.ReturnMagicLevel();
                    item.getOrCreateTag().putInt("dark_cloak", level);
                }
            }
        }).setExtraSpellChecks((playerIn -> {
            ItemStack item = playerIn.getItemInHand(Hand.MAIN_HAND);
            return item.getItem() instanceof SwordItem;
        })).addStartEventListener((playerIn, manaIn) -> {

            LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

            int level = player_cap.ReturnMagicLevel();

            if(!playerIn.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getDarkCloakModifier(level)))
                playerIn.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(getDarkCloakModifier(level));

        }).addCancelEventListener(playerIn -> {

            LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

            int level = player_cap.ReturnMagicLevel();

            ItemStack stack = playerIn.getItemInHand(Hand.MAIN_HAND);

            if(playerIn.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getDarkCloakModifier(level)))
                playerIn.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(getDarkCloakModifier(level));

            if(stack.getOrCreateTag().getInt("dark_cloak") > 0){
                stack.getOrCreateTag().putInt("dark_cloak", 0);
            }
        }).checkOnlyToToggle(false).setCheckFailMsg("A Sword needs to be in hand.").setUnlockLevel(1));

        spellRegistry.register(new BCMSpell(pluginIn, "avidya_slash", BCMSpell.Type.DARKNESS_MAGIC, 25F, 50, false, 48, 16, false, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

            LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

            if (!playerIn.level.isClientSide) {
                AvidyaSlashEntity entity = new AvidyaSlashEntity(playerIn.level, playerIn, manaIn);
                entity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.3F, 1F);
                playerIn.level.addFreshEntity(entity);
                playerIn.swing(Hand.MAIN_HAND, true);

            }
        }).setExtraSpellChecks((playerIn -> {
            ItemStack mainItem = playerIn.getItemInHand(Hand.MAIN_HAND);

            return mainItem.getOrCreateTag().getBoolean("dark_cloak");
        })).setCheckFailMsg("Dark Cloaked Sword needs to be held.").setUnlockLevel(5));

        spellRegistry.register(new BCMSpell(pluginIn, "black_blade", BCMSpell.Type.DARKNESS_MAGIC, 30F, 60, false, 48, 32, false, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

            LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

            if (!playerIn.level.isClientSide) {

                RayTraceResult mop = BCMHelper.RayTraceBlocksAndEntities(playerIn, 5);

                double i = mop.getLocation().x;
                double j = mop.getLocation().y;
                double k = mop.getLocation().z;

                List<LivingEntity> entities = BCMHelper.GetEntitiesNear(new BlockPos(mop.getLocation()), playerIn.level, 1.5, LivingEntity.class);
                if(entities.contains(playerIn))
                    entities.remove(playerIn);

                playerIn.swing(Hand.MAIN_HAND, true);

                entities.forEach(entity -> {
                    entity.hurt(DamageSource.playerAttack(playerIn), SpellHelper.spellDamageCalcP(playerIn, 2, 6));
                    if(!playerIn.level.isClientSide)
                        ((ServerWorld) playerIn.level).sendParticles(ParticleTypes.SQUID_INK, entity.getX(), entity.getY(), entity.getZ(), 10, 2, 2, 2, 0.4F);
                });

            }
        }).setExtraSpellChecks((playerIn -> {
            ItemStack mainItem = playerIn.getItemInHand(Hand.MAIN_HAND);

            return mainItem.getOrCreateTag().getBoolean("dark_cloak");
        })).setCheckFailMsg("Dark Cloaked Sword needs to be held.").setUnlockLevel(5));

        spellRegistry.register(new BCMSpell(pluginIn, "black_cocoon", BCMSpell.Type.DARKNESS_MAGIC, 0.6F, 300, false, 48, 48, true, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

            LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

            playerIn.addEffect(new EffectInstance(Effects.BLINDNESS, 20, 100, false, false));

        }).addStartEventListener((playerIn, manaIn) -> {
            if (!playerIn.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(COCOON_DE))
                playerIn.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(COCOON_DE);

            if (!playerIn.getAttribute(ModAttributes.JUMP_HEIGHT.get()).hasModifier(COCOON_DE))
                playerIn.getAttribute(ModAttributes.JUMP_HEIGHT.get()).addTransientModifier(COCOON_DE);

            if (!playerIn.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(COCOON_DE))
                playerIn.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(COCOON_DE);

            if (!playerIn.getAttribute(Attributes.ATTACK_SPEED).hasModifier(COCOON_DE))
                playerIn.getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(COCOON_DE);

        }).addCancelEventListener(playerIn -> {
            if(playerIn.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(COCOON_DE))
                playerIn.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(COCOON_DE);

            if(playerIn.getAttribute(ModAttributes.JUMP_HEIGHT.get()).hasModifier(COCOON_DE))
                playerIn.getAttribute(ModAttributes.JUMP_HEIGHT.get()).removeModifier(COCOON_DE);

            if(playerIn.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(COCOON_DE))
                playerIn.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(COCOON_DE);

            if(playerIn.getAttribute(Attributes.ATTACK_SPEED).hasModifier(COCOON_DE))
                playerIn.getAttribute(Attributes.ATTACK_SPEED).removeModifier(COCOON_DE);

        }).addDamageEventListener(((amount, source, defender) -> {

            LazyOptional<IPlayerHandler> playerInCap = defender.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

            if (!player_cap.ReturnMagicAttribute().equals(AttributeInit.DARKNESS))
                return false;

            Entity instantSource = source.getDirectEntity();

            if (defender.getPersistentData().getBoolean("blackclover_black_cocoon"))
            {

                if(source.getEntity() instanceof LivingEntity){
                    LivingEntity livingEntity = (LivingEntity) source.getEntity();

                    if(BCMHelper.GetMagicLevel(livingEntity) < BCMHelper.GetMagicLevel(defender) + 10)
                        return true;
                }
            }
            return false;
        })).setToggleTimer(200).setUnlockLevel(15));

        spellRegistry.register(new BCMSpell(pluginIn, "black_hole", BCMSpell.Type.DARKNESS_MAGIC, 1F, 1000, false, 48, 64, true, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

            LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

        }).addStartEventListener((playerIn, manaIn) -> {

            if (!playerIn.level.isClientSide) {

                BlackHoleEntity entity = new BlackHoleEntity(playerIn.level, playerIn, manaIn);
                entity.setPos(playerIn.getX() + 1, playerIn.getY() + 1, playerIn.getZ() + 1);
                playerIn.level.addFreshEntity(entity);

                playerIn.getPersistentData().putInt("black_hole_id", entity.getId());

            }

        }).addCancelEventListener(playerIn -> {

            if(!playerIn.level.isClientSide) {

                int blackHoleID = playerIn.getPersistentData().getInt("black_hole_id");

                if(playerIn.level.getEntity(blackHoleID) == null)
                    return;

                BlackHoleEntity blackHoleEntity = (BlackHoleEntity) playerIn.level.getEntity(blackHoleID);
                blackHoleEntity.remove();
            }

        }).setToggleTimer(400).setUnlockLevel(30));

    }

    private static AttributeModifier getDarkCloakModifier(int level) {

        return new AttributeModifier(UUID.fromString("b21d956a-0688-4537-8a19-033af08fa05b"), "Dark Cloak Modifier",
                ((float) level/100) * 15.5, AttributeModifier.Operation.ADDITION);
    }

}

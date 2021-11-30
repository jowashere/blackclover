package com.github.jowashere.blackclover.init.spells;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.entities.spells.wind.WindBladeEntity;
import com.github.jowashere.blackclover.entities.spells.wind.WindCrescentEntity;
import com.github.jowashere.blackclover.entities.spells.wind.WindHawkEntity;
import com.github.jowashere.blackclover.init.EntityInit;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import com.github.jowashere.blackclover.util.helpers.SpellHelper;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;

public class WindSpells {

    public static void registerSpells(BCMRegistry.SpellRegistry spellRegistry, IBCMPlugin pluginIn) {
        spellRegistry.register(new BCMSpell(pluginIn, "wind_blade", BCMSpell.Type.WIND_MAGIC, 10, 50, true, 16, 0, false, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {
            if (!playerIn.level.isClientSide) {
                WindBladeEntity entity = new WindBladeEntity(playerIn.level, playerIn, "wind_blade", manaIn);
                entity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.3F, 3.0F);
                playerIn.level.addFreshEntity(entity);

            }
        }));
        spellRegistry.register(new BCMSpell(pluginIn, "wind_crescent", BCMSpell.Type.WIND_MAGIC, 25, 70, false, 16, 48, false, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {
            if (!playerIn.level.isClientSide) {
                WindCrescentEntity entity = new WindCrescentEntity(playerIn.level, playerIn, manaIn);
                entity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.6F, 2.5F);
                playerIn.level.addFreshEntity(entity);
                playerIn.swing(Hand.MAIN_HAND, true);

            }
        }));
        spellRegistry.register(new BCMSpell(pluginIn, "towering_tornado", BCMSpell.Type.WIND_MAGIC, 15, 80, false, 16, 32, false, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {
            if (!playerIn.level.isClientSide) {

                List<LivingEntity> entities = BCMHelper.GetEntitiesNear(playerIn.blockPosition(), playerIn.level, 6F, LivingEntity.class);
                entities.remove(playerIn);

                entities.forEach(entityi ->
                {
                    Vector3d speed = BCMHelper.Propulsion(playerIn, 2.5, 2.5, 2.5);
                    entityi.setDeltaMovement(speed.x, speed.y, speed.z);
                    entityi.hurtMarked = true;
                    entityi.hasImpulse = true;
                    entityi.hurt(DamageSource.playerAttack(playerIn), SpellHelper.spellDamageCalcP(playerIn, 2, 3));
                });
                if (playerIn.level instanceof ServerWorld) {
                    ((ServerWorld) playerIn.level).sendParticles(ParticleTypes.SPIT, playerIn.getX(), playerIn.getY(), playerIn.getZ(), (int) 100, 3, 2, 3, 1);
                }
            }
        }));
        spellRegistry.register(new BCMSpell(pluginIn, "wind_blade_shower", BCMSpell.Type.WIND_MAGIC, 50, 120, false, 16, 16, false, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {
            if (!playerIn.level.isClientSide) {
                for(int i = 0; i < 15; i++) {
                    WindBladeEntity entity = new WindBladeEntity(playerIn.level, playerIn, "wind_blade_shower", manaIn);
                    entity.shoot((float) (playerIn.getLookAngle().x + (Math.random() * 0.45) - 0.275), (float) (playerIn.getLookAngle().y + (Math.random() * 0.4) - 0.25), (float) (playerIn.getLookAngle().z + (Math.random() * 0.45) - 0.275), 1.3F, 0);
                    playerIn.level.addFreshEntity(entity);
                }
            }
        }));
        spellRegistry.register(new BCMSpell(pluginIn, "wind_hawk", BCMSpell.Type.WIND_MAGIC, 50, 120, false, 16, 16, false, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

            LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

            if (!playerIn.level.isClientSide) {
                if (playerIn.level instanceof ServerWorld) {
                    WindHawkEntity entity = new WindHawkEntity(EntityInit.WIND_HAWK.get(), (World) playerIn.level);
                    entity.moveTo(playerIn.getX(), playerIn.getY(), playerIn.getZ(), playerIn.level.getRandom().nextFloat() * 360F, 0);
                    if (entity instanceof MobEntity)
                        ((MobEntity) entity).finalizeSpawn((ServerWorld) playerIn.level, playerIn.level.getCurrentDifficultyAt(entity.blockPosition()),
                                SpawnReason.MOB_SUMMONED, (ILivingEntityData) null, (CompoundNBT) null);
                    playerIn.level.addFreshEntity(entity);
                    entity.setTame(true);
                    entity.tame(playerIn);
                    entity.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, (int) Float.POSITIVE_INFINITY, player_cap.ReturnMagicLevel(), false,false, false));
                }
            }
        }));
        spellRegistry.register(new BCMSpell(pluginIn, "wind_flight", BCMSpell.Type.WIND_MAGIC, 0.7F, 100, false, 16, 16, true, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {
            ((ServerWorld) playerIn.level).sendParticles(ParticleTypes.SPIT, playerIn.getX(), playerIn.getY() + 1, playerIn.getZ(), (int) 2, 0, 0, 0, 0.05);
            playerIn.fallDistance = 0;
        }));
    }

}

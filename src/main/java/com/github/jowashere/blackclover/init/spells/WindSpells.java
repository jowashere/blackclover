package com.github.jowashere.blackclover.init.spells;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.entities.projectiles.spells.wind.WindBladeEntity;
import com.github.jowashere.blackclover.entities.projectiles.spells.wind.WindCrescentEntity;
import com.github.jowashere.blackclover.entities.summons.WindHawkEntity;
import com.github.jowashere.blackclover.init.EffectInit;
import com.github.jowashere.blackclover.init.EntityInit;
import net.minecraft.entity.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WindSpells {

    public static void registerWindSpells(BCMRegistry.SpellRegistry spellRegistry, IBCMPlugin pluginIn) {
        spellRegistry.register(new BCMSpell(pluginIn, "wind_blade", BCMSpell.Type.WIND_MAGIC, 10, 50, true, 16, 0, false, (playerIn, modifier0, modifier1, playerCapability) -> {
            if (!playerIn.level.isClientSide) {
                WindBladeEntity entity = new WindBladeEntity(playerIn.level, playerIn, "wind_blade");
                entity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.3F, 5.0F);
                playerIn.level.addFreshEntity(entity);
                playerIn.swing(Hand.MAIN_HAND, true);
            }
        }));
        spellRegistry.register(new BCMSpell(pluginIn, "wind_crescent", BCMSpell.Type.WIND_MAGIC, 25, 70, false, 16, 48, false, (playerIn, modifier0, modifier1, playerCapability) -> {
            if (!playerIn.level.isClientSide) {
                WindCrescentEntity entity = new WindCrescentEntity(playerIn.level, playerIn);
                entity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.3F, 5.0F);
                playerIn.level.addFreshEntity(entity);
            }
        }));
        spellRegistry.register(new BCMSpell(pluginIn, "towering_tornado", BCMSpell.Type.WIND_MAGIC, 15, 80, false, 16, 32, false, (playerIn, modifier0, modifier1, playerCapability) -> {
            if (!playerIn.level.isClientSide) {
                Entity entity = playerIn;
                double x = playerIn.getX();
                double y = playerIn.getY();
                double z = playerIn.getZ();
                IWorld world = playerIn.level;
                double speed = 0;
                {
                    List<Entity> _entfound = world
                            .getEntitiesOfClass(Entity.class,
                                    new AxisAlignedBB(x - (6 / 2d), y - (6 / 2d), z - (6 / 2d), x + (6 / 2d), y + (6 / 2d), z + (6 / 2d)), null)
                            .stream().sorted(new Object() {
                                Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                                    return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.distanceToSqr(_x, _y, _z)));
                                }
                            }.compareDistOf(x, y, z)).collect(Collectors.toList());
                    for (Entity entityiterator : _entfound) {
                        if ((!(entityiterator == entity))) {
                            speed = (double) 0.4;
                            if (entityiterator instanceof LivingEntity) {
                                ((LivingEntity) entityiterator).hurt(DamageSource.playerAttack(playerIn),
                                        (float) ((new Object() {
                                            int check(Entity _entity) {
                                                if (_entity instanceof LivingEntity) {
                                                    Collection<EffectInstance> effects = ((LivingEntity) _entity).getActiveEffects();
                                                    for (EffectInstance effect : effects) {
                                                        if (effect.getEffect() == EffectInit.MAGIC_LEVEL.get())
                                                            return effect.getAmplifier();
                                                    }
                                                }
                                                return 0;
                                            }
                                        }.check(entity)) + 1));
                            }
                            entityiterator
                                    .setDeltaMovement(
                                            (((entity.level.clip(new RayTraceContext(entity.getEyePosition(1f),
                                                    entity.getEyePosition(1f).add(entity.getViewVector(1f).x * 5, entity.getViewVector(1f).y * 5,
                                                            entity.getViewVector(1f).z * 5),
                                                    RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, entity)).getBlockPos().getX())
                                                    - (entity.getX())) * speed),
                                            (((entity.level
                                                    .clip(new RayTraceContext(entity.getEyePosition(1f),
                                                            entity.getEyePosition(1f)
                                                                    .add(entity.getViewVector(1f).x * 5, entity.getViewVector(1f).y * 5, entity.getViewVector(1f).z * 5),
                                                            RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, entity))
                                                    .getBlockPos().getY()) - (entity.getY())) * speed),
                                            (((entity.level.clip(new RayTraceContext(entity.getEyePosition(1f),
                                                    entity.getEyePosition(1f).add(entity.getViewVector(1f).x * 5, entity.getViewVector(1f).y * 5,
                                                            entity.getViewVector(1f).z * 5),
                                                    RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, entity)).getBlockPos().getZ())
                                                    - (entity.getZ())) * speed));
                        }
                    }
                }
                if (world instanceof ServerWorld) {
                    ((ServerWorld) world).sendParticles(ParticleTypes.SPIT, x, y, z, (int) 100, 3, 2, 3, 1);
                }
            }
        }));
        spellRegistry.register(new BCMSpell(pluginIn, "wind_blade_shower", BCMSpell.Type.WIND_MAGIC, 50, 120, false, 16, 16, false, (playerIn, modifier0, modifier1, playerCapability) -> {
            if (!playerIn.level.isClientSide) {
                for(int i = 0; i < 15; i++) {
                    WindBladeEntity entity = new WindBladeEntity(playerIn.level, playerIn, "wind_blade_shower");
                    entity.shoot((float) (playerIn.getLookAngle().x + (Math.random() * 0.45) - 0.275), (float) (playerIn.getLookAngle().y + (Math.random() * 0.4) - 0.25), (float) (playerIn.getLookAngle().z + (Math.random() * 0.45) - 0.275), 1.3F, 0);
                    playerIn.level.addFreshEntity(entity);
                }
            }
        }));
        spellRegistry.register(new BCMSpell(pluginIn, "wind_hawk", BCMSpell.Type.WIND_MAGIC, 50, 120, false, 16, 16, false, (playerIn, modifier0, modifier1, playerCapability) -> {

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
                    entity.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, (int) Float.POSITIVE_INFINITY, player_cap.returnMagicLevel(), false,false, false));
                }
            }
        }));
    }

}

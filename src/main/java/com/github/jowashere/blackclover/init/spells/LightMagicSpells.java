package com.github.jowashere.blackclover.init.spells;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.mixin.MinecraftMixin;
import com.github.jowashere.blackclover.mixin.TimerMixin;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import com.github.jowashere.blackclover.util.helpers.SpellHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Timer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;
import java.util.UUID;

public class LightMagicSpells {

    private static final AttributeModifier REACH_MODIFIER = new AttributeModifier(UUID.fromString("a2d86e15-37f0-4c5d-8e59-dd1e74ff013c"), "Reach Modifier", 1.5, AttributeModifier.Operation.ADDITION);

    public static void registerSpells(BCMRegistry.SpellRegistry spellRegistry, IBCMPlugin pluginIn) {
        spellRegistry.register(new BCMSpell(pluginIn, "slash_blades", BCMSpell.Type.SLASH_MAGIC, 0.6F, 50, true, 64, 0, true, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

            LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());


        }));

        spellRegistry.register(new BCMSpell(pluginIn, "death_scythe", BCMSpell.Type.SLASH_MAGIC, 30F, 60, false, 64, 16, false, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

            if (!playerIn.level.isClientSide) {

            }
        }));


        Minecraft mc = Minecraft.getInstance();

        Timer timer = ((MinecraftMixin) mc).getTimer();

        ((TimerMixin) timer).getMsPerTick();

        spellRegistry.register(new BCMSpell(pluginIn, "lunatic_slash", BCMSpell.Type.SLASH_MAGIC, 70F, 500, false, 64, 32, false, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

            LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

            RayTraceResult mop = BCMHelper.RayTraceBlocksAndEntities(playerIn, 7);

            List<LivingEntity> entities = BCMHelper.GetEntitiesNear(new BlockPos(mop.getLocation()), playerIn.level, 7, LivingEntity.class);
            if(entities.contains(playerIn))
                entities.remove(playerIn);

            entities.forEach(entity -> {
                entity.hurt(DamageSource.playerAttack(playerIn), SpellHelper.spellDamageCalcP(playerIn, 2, 3));
                ((ServerWorld) playerIn.level).sendParticles(ParticleTypes.SWEEP_ATTACK, entity.getX(), entity.getY(),
                        entity.getZ(), (int) 10, 3, 3, 3, 0.1);
                (playerIn.level).playSound((PlayerEntity) null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, playerIn.getSoundSource(), (float) 1, (float) 1);

                BCMHelper.waitThen(playerIn.level, 11, () -> {
                    entity.hurt(DamageSource.playerAttack(playerIn), SpellHelper.spellDamageCalcP(playerIn, 2, 3));
                    ((ServerWorld) playerIn.level).sendParticles(ParticleTypes.SWEEP_ATTACK, entity.getX(), entity.getY(),
                            entity.getZ(), (int) 10, 3, 3, 3, 0.1);
                    (playerIn.level).playSound((PlayerEntity) null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, playerIn.getSoundSource(), (float) 1, (float) 1);
                });

                BCMHelper.waitThen(playerIn.level, 22, () -> {
                    entity.hurt(DamageSource.playerAttack(playerIn), SpellHelper.spellDamageCalcP(playerIn, 2, 3));
                    ((ServerWorld) playerIn.level).sendParticles(ParticleTypes.SWEEP_ATTACK, entity.getX(), entity.getY(),
                            entity.getZ(), (int) 10, 3, 3, 3, 0.1);
                    (playerIn.level).playSound((PlayerEntity) null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, playerIn.getSoundSource(), (float) 1, (float) 1);
                });

            });

        }).setExtraSpellChecks((playerIn -> {
            boolean slashBlades = playerIn.getPersistentData().getBoolean("blackclover_slash_blades");
            return slashBlades;
        })).setCheckFailMsg("Slash Blades need to be on."));
    }
}

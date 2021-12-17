package com.github.jowashere.blackclover.init.spells.light;

import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Collections;

public class LightMovement extends AbstractSpell {

    public LightMovement(IBCMPlugin plugin) {
        super(plugin, "light_movement", AttributeInit.LIGHT);

        this.setSkillSpell(true);
        this.setManaCost(30F);
        this.setCooldown(90);
        this.setUnlockLevel(5);
        this.setUV(80, 0);

        this.action = this::action;
    }

    public void action(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {
            BlockPos pos = BCMHelper.RayTraceBlockSafe(caster, 8.5F);

            ((ServerWorld) caster.level).sendParticles(ParticleTypes.END_ROD, caster.getX(), caster.getY(), caster.getZ(), 10, 0, 1, 0, 0.1);
            caster.teleportTo(pos.getX(), pos.getY(), pos.getZ());
            if (caster instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity) caster).connection.teleport(pos.getX(), pos.getY(), pos.getZ(), caster.yRot,
                        caster.xRot, Collections.emptySet());
            }
            ((ServerWorld) caster.level).sendParticles(ParticleTypes.END_ROD, caster.getX(), caster.getY(), caster.getZ(), 10, 0, 1, 0, 0.1);
        }
    }
}

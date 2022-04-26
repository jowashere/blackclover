package com.github.jowashere.blackclover.spells.light;

import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.particles.light.LightParticleData;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnParticlePacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

import java.awt.*;
import java.util.Collections;

public class LightMovement extends AbstractSpell {

    public LightMovement() {
        super("light_movement", AttributeInit.LIGHT);

        this.setSkillSpell(true);
        this.setManaCost(30F);
        this.setCooldown(90);
        this.setUnlockLevel(5);
        this.setUV(80, 0);

        this.action = this::action;
    }

    public void action(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {
            int level = BCMHelper.getMagicLevel(caster);
            BlockPos pos = BCMHelper.RayTraceBlockSafe(caster, 8.5F + (level/2));


            caster.teleportTo(pos.getX(), pos.getY(), pos.getZ());
            if (caster instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity) caster).connection.teleport(pos.getX(), pos.getY(), pos.getZ(), caster.yRot,
                        caster.xRot, Collections.emptySet());
            }



        }
    }
}

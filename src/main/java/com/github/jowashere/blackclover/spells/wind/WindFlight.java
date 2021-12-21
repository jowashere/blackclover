package com.github.jowashere.blackclover.spells.wind;

import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.init.AttributeInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.server.ServerWorld;

public class WindFlight extends AbstractSpell {

    public WindFlight() {
        super("wind_flight", AttributeInit.WIND);

        this.setManaCost(0.3F);
        this.setCooldown(100);
        this.setUnlockLevel(7);
        this.setUV(16, 80);

        this.action = this::action;
    }

    private void action(LivingEntity caster, float manaIn) {

        ((ServerWorld) caster.level).sendParticles(ParticleTypes.SPIT, caster.getX(), caster.getY() + 1, caster.getZ(), (int) 2, 0, 0, 0, 0.05);
        caster.fallDistance = 0;

    }
}

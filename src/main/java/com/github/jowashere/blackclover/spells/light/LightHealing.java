package com.github.jowashere.blackclover.spells.light;

import com.github.jowashere.blackclover.api.internal.AbstractToggleSpell;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.server.ServerWorld;

public class LightHealing extends AbstractToggleSpell {

    public LightHealing() {
        super("light_healing", AttributeInit.LIGHT);

        this.setManaCost(0.95F);
        this.setCooldown(120);
        this.setToggleTimer(60);
        this.setUnlockLevel(20);
        this.setUV(80, 64);

        this.action = this::action;

    }

    public void action(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {

            int magicLevel = BCMHelper.getMagicLevel(caster);

            caster.addEffect(new EffectInstance(Effects.REGENERATION, 5, Math.max(1, magicLevel/2), false, false, false));
            ((ServerWorld) caster.level).sendParticles(ParticleTypes.END_ROD, caster.getX(), caster.getY(), caster.getZ(), 2, 0, 1, 0, 0.1);
        }
    }
}

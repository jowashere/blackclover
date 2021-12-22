package com.github.jowashere.blackclover.spells.wind;

import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.entities.spells.wind.WindBladeEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import net.minecraft.entity.LivingEntity;

public class WindBladeShower extends AbstractSpell
{

    public WindBladeShower() {
        super("wind_blade_shower", AttributeInit.WIND);

        this.setManaCost(70F);
        this.setCooldown(120);
        this.setUnlockLevel(15);
        this.setUV(16, 16);

        this.action = this::action;

    }

    private void action(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {
            for(int i = 0; i < 15; i++) {
                WindBladeEntity entity = new WindBladeEntity(caster.level, caster, "wind_blade_shower", manaIn);
                entity.shoot((float) (caster.getLookAngle().x + (Math.random() * 0.45) - 0.275), (float) (caster.getLookAngle().y + (Math.random() * 0.4) - 0.25), (float) (caster.getLookAngle().z + (Math.random() * 0.45) - 0.275), 1.6F, 0);
                caster.level.addFreshEntity(entity);
            }
        }
    }
}

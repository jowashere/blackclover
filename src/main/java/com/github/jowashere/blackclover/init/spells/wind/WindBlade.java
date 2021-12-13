package com.github.jowashere.blackclover.init.spells.wind;

import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.entities.spells.wind.WindBladeEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import net.minecraft.entity.LivingEntity;

public class WindBlade extends AbstractSpell {

    public WindBlade(IBCMPlugin plugin) {
        super(plugin, "wind_blade", AttributeInit.WIND);

        this.setSkillSpell(true);
        this.setManaCost(10F);
        this.setCooldown(50);
        this.setUnlockLevel(1);
        this.setUV(16, 0);

        this.action = this::action;

    }

    private void action(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {
            WindBladeEntity entity = new WindBladeEntity(caster.level, caster, "wind_blade", manaIn);
            entity.shootFromRotation(caster, caster.xRot, caster.yRot, 0.0F, 1.3F, 1.5F);
            caster.level.addFreshEntity(entity);
        }
    }
}

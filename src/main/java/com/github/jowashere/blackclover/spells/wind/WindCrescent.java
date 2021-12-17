package com.github.jowashere.blackclover.spells.wind;

import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.entities.spells.wind.WindCrescentEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;

public class WindCrescent extends AbstractSpell {

    public WindCrescent(IBCMPlugin plugin) {
        super(plugin, "wind_crescent", AttributeInit.WIND);

        this.setManaCost(25F);
        this.setCooldown(70);
        this.setUnlockLevel(15);
        this.setUV(16, 48);

        this.action = this::action;
    }

    private void action(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {
            WindCrescentEntity entity = new WindCrescentEntity(caster.level, caster, manaIn);
            entity.shootFromRotation(caster, caster.xRot, caster.yRot, 0.0F, 1.8F, 1.0F);
            caster.level.addFreshEntity(entity);
            caster.swing(Hand.MAIN_HAND, true);
        }
    }
}

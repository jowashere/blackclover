package com.github.jowashere.blackclover.spells.light;

import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.entities.spells.light.LightSwordOJEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;

public class LightSwordOJ extends AbstractSpell {

    public LightSwordOJ() {
        super("light_sword_oj", AttributeInit.LIGHT);

        this.setManaCost(25F);
        this.setCooldown(50);
        this.setUnlockLevel(5);
        this.setUV(80, 16);

        this.action = this::action;

    }

    private void action(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {
            LightSwordOJEntity entity = new LightSwordOJEntity(caster.level, caster, manaIn);
            entity.shootFromRotation(caster, caster.xRot, caster.yRot, 0.0F, 3.6F, 1F);
            caster.level.addFreshEntity(entity);
            caster.swing(Hand.MAIN_HAND, true);
        }
    }

}

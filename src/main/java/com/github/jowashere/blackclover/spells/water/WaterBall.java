package com.github.jowashere.blackclover.spells.water;

import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.entities.spells.lightning.ThunderOrbEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;

public class WaterBall extends AbstractSpell
{
    public WaterBall()
    {
        super("water_ball", AttributeInit.WATER);

        this.setManaCost(5F);
        this.setCooldown(30);
        this.setUnlockLevel(1);
        this.setUV(32, 32);

        this.action = this::action;
    }

    private void action(LivingEntity caster, float manaIn)
    {
        if (!caster.level.isClientSide)
        {
            ThunderOrbEntity entity = new ThunderOrbEntity(caster.level, caster, manaIn);
            entity.shootFromRotation(caster, caster.xRot, caster.yRot, 0.0F, 2.5F, 1F);
            caster.level.addFreshEntity(entity);
            caster.swing(Hand.MAIN_HAND, true);
        }
    }

}

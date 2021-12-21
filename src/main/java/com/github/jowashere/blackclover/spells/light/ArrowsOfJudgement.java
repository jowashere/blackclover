package com.github.jowashere.blackclover.spells.light;

import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.entities.spells.light.LightSwordOJEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ArrowsOfJudgement extends AbstractSpell {

    public ArrowsOfJudgement() {
        super("arrows_of_judgement", AttributeInit.LIGHT);

        this.setManaCost(100F);
        this.setCooldown(500);
        this.setUnlockLevel(40);
        this.setUV(80, 48);

        this.action = this::action;

    }

    private void action(LivingEntity caster, float manaIn) {

        int maxArrowCount = 5 + ((BCMHelper.getMagicLevel(caster)/100)*30);

        if (!caster.level.isClientSide) {
            List<LivingEntity> entities = BCMHelper.GetEntitiesNear(caster.blockPosition(), caster.level, 15, LivingEntity.class);
            entities.remove(caster);

            AtomicInteger arrowCount = new AtomicInteger();

            entities.forEach(entity -> {
                if(arrowCount.get() < maxArrowCount){
                    LightSwordOJEntity lightSword = new LightSwordOJEntity(caster.level, caster, manaIn);
                    lightSword.setPos(entity.getX(), entity.getY() + 5, entity.getZ());
                    lightSword.shoot(0, -180, 0, 2F, 0);
                    caster.level.addFreshEntity(lightSword);
                    arrowCount.getAndIncrement();
                }
            });
        }
    }

}

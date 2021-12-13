package com.github.jowashere.blackclover.init.spells.light;

import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.entities.spells.light.LightSwordOJEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import net.minecraft.entity.LivingEntity;

public class LightSwordsOJMulti extends AbstractSpell {

    public LightSwordsOJMulti(IBCMPlugin plugin) {
        super(plugin, "light_swords_oj", AttributeInit.LIGHT);

        this.setManaCost(75F);
        this.setCooldown(110);
        this.setUnlockLevel(10);
        this.setUV(80, 80);

        this.action = this::action;

    }

    private void action(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {
            for(int i = 0; i < 15; i++) {
                LightSwordOJEntity entity = new LightSwordOJEntity(caster.level, caster, manaIn);

                entity.shoot((float) (caster.getLookAngle().x + (Math.random() * 0.45) - 0.275), (float) (caster.getLookAngle().y + (Math.random() * 0.4) - 0.25), (float) (caster.getLookAngle().z + (Math.random() * 0.45) - 0.275), 3.0F, 0);
                caster.level.addFreshEntity(entity);
            }
        }
    }

}

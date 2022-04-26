package com.github.jowashere.blackclover.spells.water;

import com.github.jowashere.blackclover.api.internal.AbstractToggleSpell;
import com.github.jowashere.blackclover.entities.spells.water.WaterShieldEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;

public class WaterShield extends AbstractToggleSpell {

    public WaterShield()
    {
        super("water_shield", AttributeInit.WATER);

        this.setManaCost(2F);
        this.setCooldown(1000);
        this.setToggleTimer(300);
        this.setUnlockLevel(35);
        this.setUV(48, 64);

        this.onStartEvent = this::onStart;
        this.onCancelEvent = this::onEnd;
    }

    public void onStart(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {

            WaterShieldEntity entity = new WaterShieldEntity(caster.level, caster, manaIn);
            entity.setPos(caster.getX(), caster.getY(), caster.getY());
            caster.level.addFreshEntity(entity);

            caster.getPersistentData().putInt("water_shield_id", entity.getId());

        }
    }

    public void onEnd(LivingEntity caster) {
        if(!caster.level.isClientSide) {

            int waterShieldId = caster.getPersistentData().getInt("water_shield_id");

            if(caster.level.getEntity(waterShieldId) == null)
                return;

            WaterShieldEntity waterShieldEntity = (WaterShieldEntity) caster.level.getEntity(waterShieldId);
            waterShieldEntity.remove();
        }
    }
}

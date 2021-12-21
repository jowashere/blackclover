package com.github.jowashere.blackclover.spells.darkness;

import com.github.jowashere.blackclover.api.internal.AbstractToggleSpell;
import com.github.jowashere.blackclover.entities.spells.darkness.BlackHoleEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public class BlackHole extends AbstractToggleSpell {

    private static final AttributeModifier COCOON_DE = new AttributeModifier(UUID.fromString("ad388521-c053-4a67-a0d9-ff57379a2c68"), "Cocoon Speed", -2000, AttributeModifier.Operation.ADDITION);

    public BlackHole() {
        super("black_hole", AttributeInit.DARKNESS);

        this.setManaCost(1F);
        this.setCooldown(1000);
        this.setToggleTimer(400);
        this.setUnlockLevel(30);
        this.setUV(48, 64);

        this.onStartEvent = this::onStart;
        this.onCancelEvent = this::onEnd;

    }

    public void onStart(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {

            BlackHoleEntity entity = new BlackHoleEntity(caster.level, caster, manaIn);
            entity.setPos(caster.getX() + 1, caster.getY() + 1, caster.getZ() + 1);
            caster.level.addFreshEntity(entity);

            caster.getPersistentData().putInt("black_hole_id", entity.getId());

        }
    }

    public void onEnd(LivingEntity caster) {
        if(!caster.level.isClientSide) {

            int blackHoleID = caster.getPersistentData().getInt("black_hole_id");

            if(caster.level.getEntity(blackHoleID) == null)
                return;

            BlackHoleEntity blackHoleEntity = (BlackHoleEntity) caster.level.getEntity(blackHoleID);
            blackHoleEntity.remove();
        }
    }
}

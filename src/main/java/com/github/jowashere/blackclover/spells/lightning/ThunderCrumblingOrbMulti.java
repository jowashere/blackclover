package com.github.jowashere.blackclover.spells.lightning;

import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.entities.spells.lightning.ThunderOrbEntity;
import com.github.jowashere.blackclover.entities.spells.slash.DeathScytheEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class ThunderCrumblingOrbMulti extends AbstractSpell {

    public ThunderCrumblingOrbMulti()
    {
        super("tunder_orb_multi", AttributeInit.LIGHTNING);

        this.setManaCost(40F);
        this.setCooldown(120);
        this.setUnlockLevel(15);
        this.setUV(32, 32);

        this.action = this::action;
        this.extraCheck = this::extraCheck;
        this.setCheckFailMsg("Thnder God Gloves need to be on.");

    }

    private void action(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {

            BCMHelper.waitThen(caster.level, 0, () ->
            {
                ThunderOrbEntity entity = new ThunderOrbEntity(caster.level, caster, manaIn);
                entity.shootFromRotation(caster, caster.xRot, caster.yRot, 0.0F, 2.5F, 1F);
                caster.level.addFreshEntity(entity);
                caster.swing(Hand.MAIN_HAND, true);
            });
            BCMHelper.waitThen(caster.level, 10, () ->
            {
                ThunderOrbEntity entity1 = new ThunderOrbEntity(caster.level, caster, manaIn);
                entity1.shootFromRotation(caster, caster.xRot, caster.yRot, 0.0F, 2.5F, 1F);
                caster.level.addFreshEntity(entity1);
                caster.swing(Hand.MAIN_HAND, true);
            });
            BCMHelper.waitThen(caster.level, 20, () ->
            {
                ThunderOrbEntity entity2 = new ThunderOrbEntity(caster.level, caster, manaIn);
                entity2.shootFromRotation(caster, caster.xRot, caster.yRot, 0.0F, 2.5F, 1F);
                caster.level.addFreshEntity(entity2);
                caster.swing(Hand.MAIN_HAND, true);
            });
        }
    }

    private boolean extraCheck(PlayerEntity caster){
        return caster.getPersistentData().getBoolean("blackclover_tg_gloves");
    }
}

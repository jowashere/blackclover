package com.github.jowashere.blackclover.spells.slash;

import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.entities.spells.slash.DeathScytheEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class DeathScythe extends AbstractSpell {

    public static final AbstractSpell INSTANCE = new DeathScythe();

    public DeathScythe() {
        super("death_scythe", AttributeInit.SLASH);

        this.setManaCost(30F);
        this.setCooldown(60);
        this.setUnlockLevel(5);
        this.setUV(64, 16);

        this.action = this::action;
        this.extraCheck = this::extraCheck;
        this.setCheckFailMsg("Slash Blades need to be on.");

    }

    private void action(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {
            DeathScytheEntity entity = new DeathScytheEntity(caster.level, caster, manaIn);
            entity.shootFromRotation(caster, caster.xRot, caster.yRot, 0.0F, 1.8F, 1.0F);
            caster.level.addFreshEntity(entity);
            caster.swing(Hand.MAIN_HAND, true);
        }
    }

    private boolean extraCheck(PlayerEntity caster){
        return caster.getPersistentData().getBoolean("blackclover_slash_blades");
    }
}

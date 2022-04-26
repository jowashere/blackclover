package com.github.jowashere.blackclover.spells.slash;

import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.entities.spells.slash.DeathScytheEntity;
import com.github.jowashere.blackclover.entities.spells.wind.WindBladeEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import com.github.jowashere.blackclover.util.helpers.SpellHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.server.ServerWorld;

public class DeathScytheShower extends AbstractSpell
{
    public static final AbstractSpell INSTANCE = new DeathScytheShower();

    public DeathScytheShower()
    {
        super("death_scythe_shower", AttributeInit.SLASH);

        this.setManaCost(100F);
        this.setCooldown(120);
        this.setUnlockLevel(15);
        this.setUV(64,16);

        this.action = this::action;
        this.extraCheck = this::extraCheck;
        this.setCheckFailMsg("Slash Blades need to be on.");
    }


    //Added this extremely messy will fix later
    //TODO fix this shit
    private void action(LivingEntity caster, float manaIn)
    {
        if (!caster.level.isClientSide)
        {
            BCMHelper.waitThen(caster.level, 0, () ->
            {
                DeathScytheEntity entity = new DeathScytheEntity(caster.level, caster, manaIn);
                entity.shootFromRotation(caster, caster.xRot, caster.yRot, 0.0F, 1.8F, 1.0F);
                entity.shootFromRotation(caster, caster.xRot, caster.yRot, 0.0F, 1.8F, 1.0F);
                caster.level.addFreshEntity(entity);
                caster.swing(Hand.MAIN_HAND, true);
            });
            BCMHelper.waitThen(caster.level, 10, () ->
            {
                DeathScytheEntity entity2 = new DeathScytheEntity(caster.level, caster, manaIn);
                entity2.shootFromRotation(caster, caster.xRot, caster.yRot, 0.0F, 1.8F, 1.0F);
                entity2.shootFromRotation(caster, caster.xRot, caster.yRot, 0.0F, 1.8F, 1.0F);
                caster.level.addFreshEntity(entity2);
                caster.swing(Hand.MAIN_HAND, true);
            });
            BCMHelper.waitThen(caster.level, 20, () ->
            {
                DeathScytheEntity entity3 = new DeathScytheEntity(caster.level, caster, manaIn);
                entity3.shootFromRotation(caster, caster.xRot, caster.yRot, 0.0F, 1.8F, 1.0F);
                entity3.shootFromRotation(caster, caster.xRot, caster.yRot, 0.0F, 1.8F, 1.0F);
                caster.level.addFreshEntity(entity3);
                caster.swing(Hand.MAIN_HAND, true);
            });
        }
    }

    private boolean extraCheck(PlayerEntity caster){
        return caster.getPersistentData().getBoolean("blackclover_slash_blades");
    }
}

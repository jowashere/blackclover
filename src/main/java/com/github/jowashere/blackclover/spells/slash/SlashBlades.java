package com.github.jowashere.blackclover.spells.slash;

import com.github.jowashere.blackclover.api.internal.AbstractToggleSpell;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;

import java.util.UUID;

public class SlashBlades extends AbstractToggleSpell {

    private static final AttributeModifier STEP_HEIGHT = new AttributeModifier(UUID.fromString("ad388521-c053-4a67-a0d9-ff57379a2c68"), "Step Height Multiplier", 1, AttributeModifier.Operation.ADDITION);

    public SlashBlades() {
        super("slash_blades", AttributeInit.SLASH);

        this.setSkillSpell(true);
        this.setManaCost(0.4F);
        this.setCooldown(50);
        this.setUnlockLevel(1);
        this.setUV(64, 0);

        this.action = this::action;
        this.onStartEvent = this::onStart;
        this.onAttackEvent = this::onAttack;
        this.onCancelEvent = this::onEnd;

    }

    public void action (LivingEntity caster, float manaIn) {

        if(caster.getItemInHand(Hand.MAIN_HAND).isEmpty()){
            if(!caster.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getStrengthModifier(caster)))
                caster.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(getStrengthModifier(caster));
        }else {
            if(caster.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getStrengthModifier(caster)))
                caster.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(getStrengthModifier(caster));
        }

    }

    public void onStart(LivingEntity caster, float manaIn) {
        caster.getPersistentData().putBoolean("slash_damage", true);

    }

    public void onAttack(LivingEntity attacker, LivingEntity target) {
        if(attacker.getPersistentData().getBoolean("slash_damage")){
            if (attacker.getItemInHand(Hand.MAIN_HAND).isEmpty()) {

                int magicLevel = BCMHelper.getMagicLevel(attacker);

                float f = (float) attacker.getAttributeValue(Attributes.ATTACK_DAMAGE);
                int level = ((magicLevel / 100) * 15);
                float ratio = 1.0F - 1.0F / (float) (level + 1);
                float f3 = 1.0F + ratio * f;

                attacker.getPersistentData().putBoolean("slash_damage", false);
                for (LivingEntity livingentity : attacker.level.getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(1.0D, 0.25D, 1.0D))) {
                    if (livingentity != attacker && livingentity != target && !attacker.isAlliedTo(livingentity) && (!(livingentity instanceof ArmorStandEntity) || !((ArmorStandEntity) livingentity).isMarker()) && attacker.distanceToSqr(livingentity) < 9.0D) {
                        livingentity.knockback(0.4F, (double) MathHelper.sin(attacker.yRot * ((float) Math.PI / 180F)), (double) (-MathHelper.cos(attacker.yRot * ((float) Math.PI / 180F))));

                        if(attacker instanceof PlayerEntity)
                            livingentity.hurt(DamageSource.playerAttack(((PlayerEntity)attacker)), f3);
                        else
                            livingentity.hurt(DamageSource.mobAttack((attacker)), f3);
                    }
                }
                attacker.level.playSound((PlayerEntity) null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, attacker.getSoundSource(), 1.0F, 1.0F);
                if(attacker instanceof PlayerEntity)
                    ((PlayerEntity)attacker).sweepAttack();
                else
                    this.sweepAttack(attacker);
                attacker.getPersistentData().putBoolean("slash_damage", true);
            }
        }
    }

    public void onEnd(LivingEntity caster) {
        if(caster.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getStrengthModifier(caster)))
            caster.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(getStrengthModifier(caster));

    }

    private static AttributeModifier getStrengthModifier(LivingEntity caster) {

        int magicLevel = BCMHelper.getMagicLevel(caster);

        return new AttributeModifier(UUID.fromString("d8a6b472-db0a-48df-9011-303810669e5b"), "Slash Blades Damage Modifier",
                5 + ((float)magicLevel/9), AttributeModifier.Operation.ADDITION);

    }

    public void sweepAttack(LivingEntity entity) {
        double d0 = (double)(-MathHelper.sin(entity.yRot * ((float)Math.PI / 180F)));
        double d1 = (double)MathHelper.cos(entity.yRot * ((float)Math.PI / 180F));
        if (entity.level instanceof ServerWorld) {
            ((ServerWorld)entity.level).sendParticles(ParticleTypes.SWEEP_ATTACK, entity.getX() + d0, entity.getY(0.5D), entity.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
        }

    }
}

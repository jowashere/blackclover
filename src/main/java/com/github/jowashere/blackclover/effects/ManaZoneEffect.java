package com.github.jowashere.blackclover.effects;

import com.github.jowashere.blackclover.init.ModAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

import java.util.UUID;

public class ManaZoneEffect extends Effect
{


        public ManaZoneEffect()
        {
            super(EffectType.HARMFUL, 5635925);
        }

        @Override
        public void applyEffectTick(LivingEntity entity, int amplifier)
        {
            if (entity.getHealth() > 1.0F) {
                entity.hurt(DamageSource.MAGIC, 1.0F);
            }
        }

        @Override
        public boolean isDurationEffectTick(int duration, int amplifier)
        {
            return true;
        }

    @Override
    public boolean shouldRenderInvText(EffectInstance effect) {
        return false;
    }

    @Override
    public boolean shouldRender(EffectInstance effect) {
        return false;
    }

    @Override
    public boolean shouldRenderHUD(EffectInstance effect) {
        return false;
    }

}

package com.github.jowashere.blackclover.effects;

import com.github.jowashere.blackclover.init.ModAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

import java.util.UUID;

public class ExperienceMultiplierEffect extends Effect
{
    private static final AttributeModifier MULTIPLIER_MODIFIER = new AttributeModifier(UUID.fromString("ff5c8feb-6598-4d30-81de-e1ca1084f51b"), "Multiplier Modifier", 4.5, AttributeModifier.Operation.ADDITION);

    public ExperienceMultiplierEffect()
    {
        super(EffectType.BENEFICIAL, 5635925);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier)
    {

    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier)
    {
        return true;
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeModifierManager attributeModifierManager, int amplifier)
    {
        if (entity.getAttribute(ModAttributes.MULTIPLIER.get()).hasModifier(experienceMultiplierModifier(amplifier)))
            entity.getAttribute(ModAttributes.MULTIPLIER.get()).removeModifier(experienceMultiplierModifier(amplifier));
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeModifierManager attributeModifierManager, int amplifier)
    {
        if (!entity.getAttribute(ModAttributes.MULTIPLIER.get()).hasModifier(experienceMultiplierModifier(amplifier)))
            entity.getAttribute(ModAttributes.MULTIPLIER.get()).addTransientModifier(experienceMultiplierModifier(amplifier));
    }

    private static AttributeModifier experienceMultiplierModifier(int amplifier)
    {
        return new AttributeModifier(UUID.fromString("f9ef9433-fbe9-4f0a-9f5b-4eecd6f57ff8"), "Experience Multiplier",
                0.1 + (amplifier/10), AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}

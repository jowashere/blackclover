package com.github.jowashere.blackclover.spells.lightning;

import com.github.jowashere.blackclover.api.internal.AbstractToggleSpell;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;

import java.util.UUID;

public class ThunderGodGloves extends AbstractToggleSpell {

    private static final AttributeModifier HAND_SPEED = new AttributeModifier(UUID.fromString("8f4c4640-64a4-46dd-bfee-c40195ef23f3"), "Thunder Gloves Hand Modifier", 3, AttributeModifier.Operation.ADDITION);

    public ThunderGodGloves() {
        super("tg_gloves", AttributeInit.LIGHTNING);

        this.setManaCost(0.25F);
        this.setCooldown(40);
        this.setUnlockLevel(5);
        this.setUV(32, 0);

        this.onStartEvent = this::onStart;
        this.onCancelEvent = this::onEnd;
    }

    public void onStart(LivingEntity caster, float manaIn) {
        if(!caster.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getThunderStrengthModifier(caster)))
            caster.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(getThunderStrengthModifier(caster));

        if(!caster.getAttribute(Attributes.ATTACK_SPEED).hasModifier(HAND_SPEED))
            caster.getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(HAND_SPEED);
    }

    public void onEnd(LivingEntity caster) {
        if(caster.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getThunderStrengthModifier(caster)))
            caster.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(getThunderStrengthModifier(caster));

        if(caster.getAttribute(Attributes.ATTACK_SPEED).hasModifier(HAND_SPEED))
            caster.getAttribute(Attributes.ATTACK_SPEED).removeModifier(HAND_SPEED);
    }

    private static AttributeModifier getThunderStrengthModifier(LivingEntity caster) {

        int magicLevel = BCMHelper.getMagicLevel(caster);

        return new AttributeModifier(UUID.fromString("f34729f7-aed7-4ae7-a11e-ce78f454fba5"), "Thunder Gloves Strength Modifier",
                2 + ((float)magicLevel/15), AttributeModifier.Operation.ADDITION);

    }
}

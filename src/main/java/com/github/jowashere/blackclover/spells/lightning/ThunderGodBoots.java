package com.github.jowashere.blackclover.spells.lightning;

import com.github.jowashere.blackclover.api.internal.AbstractToggleSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.init.ModAttributes;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraftforge.common.util.LazyOptional;

import java.util.UUID;

public class ThunderGodBoots extends AbstractToggleSpell {

    private static final AttributeModifier STEP_HEIGHT = new AttributeModifier(UUID.fromString("ad388521-c053-4a67-a0d9-ff57379a2c68"), "Step Height Multiplier", 1, AttributeModifier.Operation.ADDITION);

    public ThunderGodBoots() {
        super("tg_boots", AttributeInit.LIGHTNING);

        this.setSkillSpell(true);
        this.setManaCost(0.25F);
        this.setCooldown(40);
        this.setUnlockLevel(1);
        this.setUV(32, 16);

        this.action = this::action;
        this.onStartEvent = this::onStart;
        this.onCancelEvent = this::onEnd;

    }

    public void action(LivingEntity caster, float main) {
        caster.fallDistance = 0;
    }

    public void onStart(LivingEntity caster, float manaIn) {
        if(!caster.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(getThunderSpeedModifier(caster)))
            caster.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(getThunderSpeedModifier(caster));

        if(!caster.getAttribute(ModAttributes.JUMP_HEIGHT.get()).hasModifier(getThunderJumpModifier(caster)))
            caster.getAttribute(ModAttributes.JUMP_HEIGHT.get()).addTransientModifier(getThunderJumpModifier(caster));

        if(!caster.getAttribute(ModAttributes.STEP_HEIGHT.get()).hasModifier(STEP_HEIGHT))
            caster.getAttribute(ModAttributes.STEP_HEIGHT.get()).addTransientModifier(STEP_HEIGHT);
    }

    public void onEnd(LivingEntity playerIn) {
        if(playerIn.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(getThunderSpeedModifier(playerIn)))
            playerIn.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(getThunderSpeedModifier(playerIn));

        if(playerIn.getAttribute(ModAttributes.JUMP_HEIGHT.get()).hasModifier(getThunderJumpModifier(playerIn)))
            playerIn.getAttribute(ModAttributes.JUMP_HEIGHT.get()).removeModifier(getThunderJumpModifier(playerIn));

        if(playerIn.getAttribute(ModAttributes.STEP_HEIGHT.get()).hasModifier(STEP_HEIGHT))
            playerIn.getAttribute(ModAttributes.STEP_HEIGHT.get()).removeModifier(STEP_HEIGHT);

    }

    private static AttributeModifier getThunderSpeedModifier(LivingEntity caster) {

        int magicLevel = BCMHelper.getMagicLevel(caster);

        return new AttributeModifier(UUID.fromString("c6d81aa4-76af-4040-b7c2-ebe3c6616af1"), "Thunder Boots Speed Modifier",
                0.055 * magicLevel, AttributeModifier.Operation.MULTIPLY_BASE);
    }

    private static AttributeModifier getThunderJumpModifier(LivingEntity caster) {

        int magicLevel = BCMHelper.getMagicLevel(caster);

        return new AttributeModifier(UUID.fromString("f34729f7-aed7-4ae7-a11e-ce78f454fba5"), "Thunder Boots Jump Modifier",
                1.5 + (((float)magicLevel/100) * 3), AttributeModifier.Operation.ADDITION);

    }
}

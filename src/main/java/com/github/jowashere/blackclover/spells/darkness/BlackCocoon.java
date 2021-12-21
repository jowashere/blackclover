package com.github.jowashere.blackclover.spells.darkness;

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
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.LazyOptional;

import java.util.UUID;

public class BlackCocoon extends AbstractToggleSpell {

    private static final AttributeModifier COCOON_DE = new AttributeModifier(UUID.fromString("ad388521-c053-4a67-a0d9-ff57379a2c68"), "Cocoon Speed", -2000, AttributeModifier.Operation.ADDITION);

    public BlackCocoon() {
        super("black_cocoon", AttributeInit.DARKNESS);

        this.setManaCost(0.6F);
        this.setCooldown(300);
        this.setToggleTimer(200);
        this.setUnlockLevel(15);
        this.setUV(48, 48);

        this.action = this::action;
        this.onDamageEvent = this::onDamage;
        this.onStartEvent = this::onStart;
        this.onCancelEvent = this::onEnd;

    }


    public void action(LivingEntity caster, float manaIn) {
        caster.addEffect(new EffectInstance(Effects.BLINDNESS, 20, 100, false, false));
    }

    public boolean onDamage(float amount, DamageSource source, LivingEntity defender) {
        LazyOptional<IPlayerHandler> playerInCap = defender.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

        if (!player_cap.ReturnMagicAttribute().equals(AttributeInit.DARKNESS))
            return false;

        if (defender.getPersistentData().getBoolean("blackclover_black_cocoon"))
        {

            if(source.getEntity() instanceof LivingEntity){
                LivingEntity livingEntity = (LivingEntity) source.getEntity();

                if(BCMHelper.getMagicLevel(livingEntity) < BCMHelper.getMagicLevel(defender) + 10)
                    return true;
            }
        }
        return false;
    }

    public void onStart(LivingEntity caster, float manaIn) {
        if (!caster.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(COCOON_DE))
            caster.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(COCOON_DE);

        if (!caster.getAttribute(ModAttributes.JUMP_HEIGHT.get()).hasModifier(COCOON_DE))
            caster.getAttribute(ModAttributes.JUMP_HEIGHT.get()).addTransientModifier(COCOON_DE);

        if (!caster.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(COCOON_DE))
            caster.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(COCOON_DE);

        if (!caster.getAttribute(Attributes.ATTACK_SPEED).hasModifier(COCOON_DE))
            caster.getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(COCOON_DE);
    }

    public void onEnd(LivingEntity caster) {
        if(caster.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(COCOON_DE))
            caster.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(COCOON_DE);

        if(caster.getAttribute(ModAttributes.JUMP_HEIGHT.get()).hasModifier(COCOON_DE))
            caster.getAttribute(ModAttributes.JUMP_HEIGHT.get()).removeModifier(COCOON_DE);

        if(caster.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(COCOON_DE))
            caster.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(COCOON_DE);

        if(caster.getAttribute(Attributes.ATTACK_SPEED).hasModifier(COCOON_DE))
            caster.getAttribute(Attributes.ATTACK_SPEED).removeModifier(COCOON_DE);

    }
}

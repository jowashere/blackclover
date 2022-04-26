package com.github.jowashere.blackclover.spells.darkness;

import com.github.jowashere.blackclover.api.internal.AbstractToggleSpell;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.particles.dark.DarkParticleData;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnParticlePacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;
import java.util.UUID;

public class DarkCloakedBlade extends AbstractToggleSpell {

    private static final AttributeModifier COCOON_DE = new AttributeModifier(UUID.fromString("ad388521-c053-4a67-a0d9-ff57379a2c68"), "Cocoon Speed", -2000, AttributeModifier.Operation.ADDITION);

    public DarkCloakedBlade() {
        super("dark_cloaked_blade", AttributeInit.DARKNESS);

        this.setSkillSpell(true);
        this.setManaCost(0.4F);
        this.setCooldown(40);
        this.setUnlockLevel(1);
        this.setUV(48, 0);
        this.checkOnlyToToggle(false);

        this.action = this::action;
        this.onStartEvent = this::onStart;
        this.onCancelEvent = this::onEnd;
        this.extraCheck = this::extraCheck;

        this.setCheckFailMsg("A Sword needs to be in hand.");

    }


    public void action(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {
            ItemStack item = caster.getItemInHand(Hand.MAIN_HAND);
            if(item.getItem() instanceof SwordItem){

                double yawRightHandDirection = Math.toRadians(-1 * caster.yBodyRot- 45);
                double x = 0.5 * Math.sin(yawRightHandDirection) + caster.getX() ;
                double y = caster.getY() + 1.2;
                double z = 0.5 * Math.cos(yawRightHandDirection) + caster.getZ();
                int level = BCMHelper.getMagicLevel(caster);
                item.getOrCreateTag().putInt("dark_cloak", level);
            }
        }
    }

    public void onStart(LivingEntity caster, float manaIn) {

        int level = BCMHelper.getMagicLevel(caster);

        if(!caster.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getDarkCloakModifier(level)))
            caster.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(getDarkCloakModifier(level));
    }

    public void onEnd(LivingEntity caster) {

        int level = BCMHelper.getMagicLevel(caster);

        ItemStack stack = caster.getItemInHand(Hand.MAIN_HAND);

        if(caster.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getDarkCloakModifier(level)))
            caster.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(getDarkCloakModifier(level));

        if(stack.hasTag()){
            if (stack.getTag().getInt("dark_cloak") > 0) {
                stack.getTag().putInt("dark_cloak", 0);
            }
        }

    }

    public boolean extraCheck(LivingEntity caster) {
        ItemStack item = caster.getItemInHand(Hand.MAIN_HAND);
        return item.getItem() instanceof SwordItem;
    }

    private static AttributeModifier getDarkCloakModifier(int level) {

        return new AttributeModifier(UUID.fromString("b21d956a-0688-4537-8a19-033af08fa05b"), "Dark Cloak Modifier",
                ((float) level/100) * 15.5, AttributeModifier.Operation.ADDITION);
    }
}

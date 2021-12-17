package com.github.jowashere.blackclover.spells.sword;

import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.entities.spells.sword.OriginFlashEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.init.ItemInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class OriginFlash extends AbstractSpell {

    public OriginFlash(IBCMPlugin plugin) {
        super(plugin, "origin_flash", AttributeInit.SWORD);

        this.setSkillSpell(true);
        this.setManaCost(20F);
        this.setCooldown(35);
        this.setUnlockLevel(1);
        this.setUV(96, 0);

        this.action = this::action;
        this.extraCheck = this::extraCheck;
        this.setCheckFailMsg("You need the Demon Dweller Sword for this!");

    }

    private void action(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide)
        {
            OriginFlashEntity entity = new OriginFlashEntity(caster.level, caster, manaIn);
            entity.shootFromRotation(caster, caster.xRot, caster.yRot, 0.0F, 1.6F, 2.5F);
            caster.level.addFreshEntity(entity);
            caster.swing(Hand.MAIN_HAND, true);
        }
    }

    private boolean extraCheck(PlayerEntity caster){
        ItemStack hand = caster.getItemInHand(Hand.MAIN_HAND);
        return (hand.getItem().equals(ItemInit.DEMON_DWELLER.get()));
    }
}

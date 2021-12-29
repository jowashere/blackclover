package com.github.jowashere.blackclover.spells.darkness;

import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.entities.spells.darkness.AvidyaSlashEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class AvidyaSlash extends AbstractSpell
{

    public AvidyaSlash()
    {
        super("avidya_slash", AttributeInit.DARKNESS);

        this.setManaCost(25F);
        this.setCooldown(50);
        this.setUnlockLevel(5);
        this.setUV(48, 16);

        this.action = this::action;
        this.extraCheck = this::extraCheck;
        this.setCheckFailMsg("Dark Cloaked Sword needs to be held.");

    }

    private void action(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {
            AvidyaSlashEntity entity = new AvidyaSlashEntity(caster.level, caster, manaIn);
            entity.shootFromRotation(caster, caster.xRot, caster.yRot, 0.0F, 1.3F, 1F);
            caster.level.addFreshEntity(entity);
            caster.swing(Hand.MAIN_HAND, true);
        }
    }

    private boolean extraCheck(PlayerEntity caster){
        ItemStack mainItem = caster.getItemInHand(Hand.MAIN_HAND);
        return mainItem.getOrCreateTag().contains("dark_cloak");
    }
}

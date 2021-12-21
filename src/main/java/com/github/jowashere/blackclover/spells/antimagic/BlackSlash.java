package com.github.jowashere.blackclover.spells.antimagic;

import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.entities.spells.sword.OriginFlashEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.init.ItemInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class BlackSlash extends AbstractSpell {

    public BlackSlash() {
        super("black_slash", AttributeInit.ANTI_MAGIC);

        this.setManaCost(25F);
        this.setCooldown(50);
        this.setUnlockLevel(15);
        this.setUV(0, 16);

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
        ItemStack mainItem = caster.getItemInHand(Hand.MAIN_HAND);
        return (mainItem.getItem().equals(ItemInit.DEMON_DWELLER.get()) && mainItem.getOrCreateTag().getBoolean("antimagic"));
    }
}

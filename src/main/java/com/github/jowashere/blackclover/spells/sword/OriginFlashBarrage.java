package com.github.jowashere.blackclover.spells.sword;

import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.entities.spells.sword.OriginFlashEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.init.ItemInit;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class OriginFlashBarrage extends AbstractSpell {

    public OriginFlashBarrage() {
        super("origin_flash_barrage", AttributeInit.SWORD);

        this.setManaCost(120F);
        this.setCooldown(600);
        this.setUnlockLevel(30);
        this.setUV(96, 32);

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

            for(int i = 0; i < 15; i++) {
                BCMHelper.waitThen(caster.level, i * 2, ()-> {
                    OriginFlashEntity entity2 = new OriginFlashEntity(caster.level, caster, manaIn);
                    entity2.shoot((float) (caster.getLookAngle().x + (Math.random() * 0.45) - 0.275), (float) (caster.getLookAngle().y + (Math.random() * 0.4) - 0.25), (float) (caster.getLookAngle().z + (Math.random() * 0.45) - 0.275), 1.6F, 0);
                    caster.level.addFreshEntity(entity2);
                    caster.swing(Hand.MAIN_HAND, true);
                });
            }
        }
    }

    private boolean extraCheck(PlayerEntity caster){
        ItemStack hand = caster.getItemInHand(Hand.MAIN_HAND);
        return (hand.getItem().equals(ItemInit.DEMON_DWELLER.get()));
    }
}

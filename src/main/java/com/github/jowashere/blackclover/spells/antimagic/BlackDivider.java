package com.github.jowashere.blackclover.spells.antimagic;

import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.AbstractToggleSpell;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.init.ItemInit;
import com.github.jowashere.blackclover.init.ModAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.UUID;

public class BlackDivider extends AbstractToggleSpell {

    private static final AttributeModifier REACH_MODIFIER = new AttributeModifier(UUID.fromString("ff5c8feb-6598-4d30-81de-e1ca1084f51b"), "Reach Modifier", 4.5, AttributeModifier.Operation.ADDITION);

    public BlackDivider(IBCMPlugin plugin) {
        super(plugin, "black_divider", AttributeInit.ANTI_MAGIC);

        this.setManaCost(0.25F);
        this.setCooldown(200);
        this.setUnlockLevel(20);
        this.setUV(0, 80);
        this.checkOnlyToToggle(false);

        this.onStartEvent = this::onStart;
        this.onCancelEvent = this::onEnd;
        this.extraCheck = this::extraCheck;

        this.setCheckFailMsg("Demon Slayer Sword needs to be in hand!");
    }

    public void onStart(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {

            if(!caster.getAttribute(ModAttributes.ATTACK_RANGE.get()).hasModifier(REACH_MODIFIER))
                caster.getAttribute(ModAttributes.ATTACK_RANGE.get()).addTransientModifier((REACH_MODIFIER));

            ItemStack stack = caster.getItemInHand(Hand.MAIN_HAND);

            if(stack.getItem().equals(ItemInit.DEMON_SLAYER.get())){
                stack.getOrCreateTag().putBoolean("black_divider", true);
            }
        }
    }

    public void onEnd(LivingEntity caster) {
        if(caster.getAttribute(ModAttributes.ATTACK_RANGE.get()).hasModifier(REACH_MODIFIER))
            caster.getAttribute(ModAttributes.ATTACK_RANGE.get()).removeModifier(REACH_MODIFIER);
    }

    public boolean extraCheck(PlayerEntity caster) {
        ItemStack mainItem = caster.getItemInHand(Hand.MAIN_HAND);
        return (mainItem.getItem().equals(ItemInit.DEMON_SLAYER.get()) && mainItem.getOrCreateTag().getBoolean("antimagic"));
    }
}

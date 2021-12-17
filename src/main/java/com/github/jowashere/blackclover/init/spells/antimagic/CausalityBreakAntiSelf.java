package com.github.jowashere.blackclover.init.spells.antimagic;

import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.init.ItemInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.world.server.ServerWorld;

public class CausalityBreakAntiSelf extends AbstractSpell {

    public CausalityBreakAntiSelf(IBCMPlugin plugin) {
        super(plugin, "causality_break_self", AttributeInit.ANTI_MAGIC);

        this.setManaCost(20F);
        this.setCooldown(200);
        this.setUnlockLevel(16);
        this.setUV(0, 48);

        this.action = this::action;
        this.extraCheck = this::extraCheck;
        this.setCheckFailMsg("You need the Demon Destroyer Sword for this!");

    }

    private void action(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide)
        {
            caster.removeAllEffects();
            if (caster.level instanceof ServerWorld) {
                ((ServerWorld) caster.level).sendParticles(ParticleTypes.SMOKE, caster.getX(), caster.getY(), caster.getZ(), (int) 25, 0, 0, 0, 0.25);
            }
        }
    }

    private boolean extraCheck(PlayerEntity caster){
        ItemStack mainItem = caster.getItemInHand(Hand.MAIN_HAND);
        return (mainItem.getItem().equals(ItemInit.DEMON_DESTROYER.get()) && mainItem.getOrCreateTag().getBoolean("antimagic"));
    }

}

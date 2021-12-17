package com.github.jowashere.blackclover.spells.antimagic;

import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.init.ItemInit;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class CausalityBreakAnti extends AbstractSpell {

    public CausalityBreakAnti(IBCMPlugin plugin) {
        super(plugin, "causality_break", AttributeInit.ANTI_MAGIC);

        this.setManaCost(20F);
        this.setCooldown(200);
        this.setUnlockLevel(15);
        this.setUV(0, 32);

        this.action = this::action;
        this.extraCheck = this::extraCheck;
        this.setCheckFailMsg("You need the Demon Destroyer Sword for this!");

    }

    private void action(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide)
        {
            int magicLevel = BCMHelper.getMagicLevel(caster);

            List<LivingEntity> entities= BCMHelper.GetEntitiesNear(caster.blockPosition(), caster.level, (int) (5 + (magicLevel / 10)), LivingEntity.class);
            entities.remove(caster);

            entities.forEach(entity -> {
                entity.removeAllEffects();
                if (caster.level instanceof ServerWorld) {
                    ((ServerWorld) caster.level).sendParticles(ParticleTypes.SMOKE, entity.getX(), entity.getY(), entity.getZ(), (int) 25, 0, 0, 0, 0.25);
                }
            });
        }
    }

    private boolean extraCheck(PlayerEntity caster){
        ItemStack mainItem = caster.getItemInHand(Hand.MAIN_HAND);
        return (mainItem.getItem().equals(ItemInit.DEMON_DESTROYER.get()) && mainItem.getOrCreateTag().getBoolean("antimagic"));
    }

}

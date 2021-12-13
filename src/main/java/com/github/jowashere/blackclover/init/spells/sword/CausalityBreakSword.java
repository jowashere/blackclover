package com.github.jowashere.blackclover.init.spells.sword;

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
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.server.ServerWorld;

public class CausalityBreakSword extends AbstractSpell {

    public CausalityBreakSword(IBCMPlugin plugin) {
        super(plugin, "causality_break_sword", AttributeInit.SWORD);

        this.setManaCost(40F);
        this.setCooldown(200);
        this.setUnlockLevel(16);
        this.setUV(96, 16);

        this.action = this::action;
        this.extraCheck = this::extraCheck;
        this.setCheckFailMsg("You need the Demon Destroyer Sword for this!");

    }

    private void action(LivingEntity caster, float manaIn) {
        EntityRayTraceResult rayTraceResult = BCMHelper.RayTraceEntities(caster, 6);

        if((rayTraceResult.getEntity() instanceof LivingEntity))
        {
            LivingEntity entity = (LivingEntity) rayTraceResult.getEntity();
            entity.removeAllEffects();
            ((ServerWorld) caster.level).sendParticles(ParticleTypes.SPIT, entity.getX(), entity.getY(), entity.getZ(), (int) 25, 0, 0, 0, 0.25);
        }

        caster.swing(Hand.MAIN_HAND, true);
    }

    private boolean extraCheck(PlayerEntity caster){
        ItemStack mainItem = caster.getItemInHand(Hand.MAIN_HAND);
        return (mainItem.getItem().equals(ItemInit.DEMON_DESTROYER.get()));
    }
}

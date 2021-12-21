package com.github.jowashere.blackclover.spells.darkness;

import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import com.github.jowashere.blackclover.util.helpers.SpellHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class BlackBlade extends AbstractSpell {

    public BlackBlade() {
        super("black_blade", AttributeInit.DARKNESS);

        this.setManaCost(30F);
        this.setCooldown(60);
        this.setUnlockLevel(5);
        this.setUV(48, 32);

        this.action = this::action;
        this.extraCheck = this::extraCheck;
        this.setCheckFailMsg("Dark Cloaked Sword needs to be held.");

    }

    private void action(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {

            RayTraceResult mop = BCMHelper.RayTraceBlocksAndEntities(caster, 5);

            double i = mop.getLocation().x;
            double j = mop.getLocation().y;
            double k = mop.getLocation().z;

            List<LivingEntity> entities = BCMHelper.GetEntitiesNear(new BlockPos(mop.getLocation()), caster.level, 1.5, LivingEntity.class);
            if(entities.contains(caster))
                entities.remove(caster);

            caster.swing(Hand.MAIN_HAND, true);

            entities.forEach(entity -> {

                int magicLevel = BCMHelper.getMagicLevel(caster);

                BCMHelper.doSpellDamage(caster, entity, SpellHelper.spellDamageCalc(magicLevel, 2, 3));

                if(!caster.level.isClientSide)
                    ((ServerWorld) caster.level).sendParticles(ParticleTypes.SQUID_INK, entity.getX(), entity.getY(), entity.getZ(), 10, 2, 2, 2, 0.4F);
            });

        }
    }

    private boolean extraCheck(PlayerEntity caster){
        ItemStack mainItem = caster.getItemInHand(Hand.MAIN_HAND);
        return mainItem.getOrCreateTag().getBoolean("dark_cloak");
    }
}

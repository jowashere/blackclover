package com.github.jowashere.blackclover.spells.slash;

import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import com.github.jowashere.blackclover.util.helpers.SpellHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class LunaticSlash extends AbstractSpell {

    public LunaticSlash(IBCMPlugin plugin) {
        super(plugin, "lunatic_slash", AttributeInit.SLASH);

        this.setManaCost(90F);
        this.setCooldown(100);
        this.setUnlockLevel(20);
        this.setUV(64, 32);

        this.action = this::action;
        this.extraCheck = this::extraCheck;
        this.setCheckFailMsg("Slash Blades need to be on.");

    }

    private void action(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {
            RayTraceResult mop = BCMHelper.RayTraceBlocksAndEntities(caster, 7);

            List<LivingEntity> entities = BCMHelper.GetEntitiesNear(new BlockPos(mop.getLocation()), caster.level, 7, LivingEntity.class);
            if(entities.contains(caster))
                entities.remove(caster);

            entities.forEach(entity -> {

                int magicLevel = BCMHelper.getMagicLevel(caster);

                BCMHelper.doSpellDamage(caster, entity, SpellHelper.spellDamageCalc(magicLevel, 2, 3));

                ((ServerWorld) caster.level).sendParticles(ParticleTypes.SWEEP_ATTACK, entity.getX(), entity.getY(),
                        entity.getZ(), (int) 10, 3, 3, 3, 0.1);
                (caster.level).playSound((PlayerEntity) null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, caster.getSoundSource(), (float) 1, (float) 1);

                BCMHelper.waitThen(caster.level, 11, () -> {

                    BCMHelper.doSpellDamage(caster, entity, SpellHelper.spellDamageCalc(magicLevel, 2, 3));

                    ((ServerWorld) caster.level).sendParticles(ParticleTypes.SWEEP_ATTACK, entity.getX(), entity.getY(),
                            entity.getZ(), (int) 10, 3, 3, 3, 0.1);
                    (caster.level).playSound((PlayerEntity) null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, caster.getSoundSource(), (float) 1, (float) 1);
                });

                BCMHelper.waitThen(caster.level, 22, () -> {

                    BCMHelper.doSpellDamage(caster, entity, SpellHelper.spellDamageCalc(magicLevel, 2, 3));

                    ((ServerWorld) caster.level).sendParticles(ParticleTypes.SWEEP_ATTACK, entity.getX(), entity.getY(),
                            entity.getZ(), (int) 10, 3, 3, 3, 0.1);
                    (caster.level).playSound((PlayerEntity) null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, caster.getSoundSource(), (float) 1, (float) 1);
                });
            });
        }
    }

    private boolean extraCheck(PlayerEntity caster){
        return caster.getPersistentData().getBoolean("blackclover_slash_blades");
    }
}

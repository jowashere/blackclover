package com.github.jowashere.blackclover.spells.wind;

import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import com.github.jowashere.blackclover.util.helpers.SpellHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class ToweringTornado extends AbstractSpell {

    public ToweringTornado(IBCMPlugin plugin) {
        super(plugin, "wind_crescent", AttributeInit.WIND);

        this.setManaCost(15F);
        this.setCooldown(80);
        this.setUnlockLevel(5);
        this.setUV(16, 32);

        this.action = this::action;
    }

    private void action(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {

            List<LivingEntity> entities = BCMHelper.GetEntitiesNear(caster.blockPosition(), caster.level, 6F, LivingEntity.class);
            entities.remove(caster);

            entities.forEach(entityi ->
            {
                Vector3d speed = BCMHelper.Propulsion(caster, 2.5, 2.5, 2.5);
                entityi.setDeltaMovement(speed.x, speed.y, speed.z);
                entityi.hurtMarked = true;
                entityi.hasImpulse = true;

                int magicLevel = BCMHelper.getMagicLevel(caster);

                BCMHelper.doSpellDamage(caster, entityi, SpellHelper.spellDamageCalc(magicLevel, 2, 3));

            });
            if (caster.level instanceof ServerWorld) {
                ((ServerWorld) caster.level).sendParticles(ParticleTypes.SPIT, caster.getX(), caster.getY(), caster.getZ(), (int) 100, 3, 2, 3, 1);
            }
        }
    }
}

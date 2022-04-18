package com.github.jowashere.blackclover.spells.lightning;

import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import com.github.jowashere.blackclover.util.helpers.SpellHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.management.Attribute;
import java.util.List;

public class ThunderDischarge extends AbstractSpell {

    public ThunderDischarge()
    {
        super("thunder_discharge", AttributeInit.LIGHTNING);

        this.setManaCost(100F);
        this.setCooldown(15);
        this.setUnlockLevel(30);
        this.setUV(64, 32);

        this.action = this::action;

    }

    //Checks entities in a radius
    //Damages them + particles if they are
    private void action(LivingEntity caster, float manaIn)
    {
        if (!caster.level.isClientSide)
        {
            int magicLevel = BCMHelper.getMagicLevel(caster);


            //Checks radius and damages enemies
            List<Entity> entities = BCMHelper.GetEntitiesNear(caster.blockPosition(), caster.level, (3 + (int) (BCMHelper.getMagicLevel(caster) / 4)));
            if (entities.contains(caster))
            {
                entities.remove(caster);

                entities.forEach(entity -> {
                    BCMHelper.doSpellDamage(caster, (LivingEntity) entity, SpellHelper.spellDamageCalc(magicLevel, 2, 3));

                    ((ServerWorld) caster.level).sendParticles(ParticleTypes.END_ROD, entity.getX(), entity.getY(),
                            entity.getZ(), (int) 10, 3, 3, 3, 0.1);
                });
            }

        }
    }
}

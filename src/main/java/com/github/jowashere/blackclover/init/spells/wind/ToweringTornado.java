package com.github.jowashere.blackclover.init.spells.wind;

import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.entities.mobs.BCEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import com.github.jowashere.blackclover.util.helpers.SpellHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;

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

                int magicLevel = 1;

                if(caster instanceof PlayerEntity) {
                    LazyOptional<IPlayerHandler> casterCap = caster.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                    IPlayerHandler player_cap = casterCap.orElse(new PlayerCapability());

                    magicLevel = player_cap.ReturnMagicLevel();
                } else if (caster instanceof BCEntity) {
                    magicLevel = ((BCEntity) caster).getMagicLevel();
                }

                if(caster instanceof PlayerEntity)
                    entityi.hurt(DamageSource.playerAttack((PlayerEntity) caster), SpellHelper.spellDamageCalc(magicLevel, 2, 3));
                else
                    entityi.hurt(DamageSource.mobAttack((caster)), SpellHelper.spellDamageCalc(magicLevel, 2, 3));

            });
            if (caster.level instanceof ServerWorld) {
                ((ServerWorld) caster.level).sendParticles(ParticleTypes.SPIT, caster.getX(), caster.getY(), caster.getZ(), (int) 100, 3, 2, 3, 1);
            }
        }
    }
}

package com.github.jowashere.blackclover.spells.wind;

import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.entities.mobs.BCEntity;
import com.github.jowashere.blackclover.entities.spells.wind.WindHawkEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.init.EntityInit;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;

public class WindHawk extends AbstractSpell {

    public WindHawk(IBCMPlugin plugin) {
        super(plugin, "wind_hawk", AttributeInit.WIND);

        this.setManaCost(60F);
        this.setCooldown(120);
        this.setUnlockLevel(20);
        this.setUV(16, 64);

        this.action = this::action;
    }

    private void action(LivingEntity caster, float manaIn) {

        int magicLevel = 1;

        if(caster instanceof PlayerEntity) {
            LazyOptional<IPlayerHandler> casterCap = caster.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = casterCap.orElse(new PlayerCapability());

            magicLevel = player_cap.ReturnMagicLevel();
        } else if (caster instanceof BCEntity) {
            magicLevel = ((BCEntity) caster).getMagicLevel();
        }

        if (!caster.level.isClientSide) {
            if (caster.level instanceof ServerWorld) {
                WindHawkEntity entity = new WindHawkEntity(EntityInit.WIND_HAWK.get(), (World) caster.level);
                entity.moveTo(caster.getX(), caster.getY(), caster.getZ(), caster.level.getRandom().nextFloat() * 360F, 0);
                if (entity instanceof MobEntity)
                    ((MobEntity) entity).finalizeSpawn((ServerWorld) caster.level, caster.level.getCurrentDifficultyAt(entity.blockPosition()),
                            SpawnReason.MOB_SUMMONED, (ILivingEntityData) null, (CompoundNBT) null);
                caster.level.addFreshEntity(entity);
                entity.setTame(true);

                if(caster instanceof PlayerEntity)
                entity.tame((PlayerEntity) caster);
                entity.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, (int) Float.POSITIVE_INFINITY, magicLevel, false,false, false));
            }
        }
    }
}

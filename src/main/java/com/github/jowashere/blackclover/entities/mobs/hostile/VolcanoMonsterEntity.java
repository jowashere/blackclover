package com.github.jowashere.blackclover.entities.mobs.hostile;

import com.github.jowashere.blackclover.entities.mobs.BCEntity;
import com.github.jowashere.blackclover.entities.spells.sword.OriginFlashEntity;
import com.github.jowashere.blackclover.init.EntityInit;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class VolcanoMonsterEntity extends BCEntity
{

    public VolcanoMonsterEntity(EntityType<? extends VolcanoMonsterEntity> type, World world) {
        super(type, world);
        this.maxML = 35;
        this.minML = 15;
        this.magicXPDrop = 500;
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1, true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D, 0.0F));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 4));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));

    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes()
    {
        AttributeModifierMap.MutableAttribute attributes = MobEntity.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, 3.5)
                .add(Attributes.ARMOR, 10)
                .add(Attributes.MAX_HEALTH, 100)
                .add(Attributes.FOLLOW_RANGE, 25)
                .add(Attributes.MOVEMENT_SPEED, 0.24);

        return addModAttributes(attributes);
    }

    @Override
    public boolean removeWhenFarAway(double d)
    {
        return false;
    }

    @Override
    public float getBrightness() {
        return 2.0F;
    }

    @Override
    public boolean isSensitiveToWater() {
        return true;
    }



    @Override
    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag)
    {
        spawnData = super.finalizeSpawn(world, difficulty, reason, spawnData, dataTag);
        return spawnData;

    }

}

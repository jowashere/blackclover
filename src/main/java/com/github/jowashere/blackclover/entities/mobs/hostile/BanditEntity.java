package com.github.jowashere.blackclover.entities.mobs.hostile;

import com.github.jowashere.blackclover.entities.mobs.BCEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class BanditEntity extends BCEntity
{
    private static final String[] DEFAULT_TEXTURES = new String[]
            {
                "hostile/bandit"
            };


    public BanditEntity(EntityType<? extends CreatureEntity> type, World world) {
        super(type, world, DEFAULT_TEXTURES);
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1, true));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 4));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));

        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes()
    {
        return MobEntity.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.ARMOR, 5)
                .add(Attributes.MAX_HEALTH, 15)
                .add(Attributes.FOLLOW_RANGE, 20)
                .add(Attributes.MOVEMENT_SPEED, 0.24);

    }

    @Override
    public boolean removeWhenFarAway(double d)
    {
        if (d > 32)
            return true;
        else
            return false;
    }
}

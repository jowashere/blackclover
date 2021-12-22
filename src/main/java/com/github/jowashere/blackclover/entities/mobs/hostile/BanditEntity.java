package com.github.jowashere.blackclover.entities.mobs.hostile;

import com.github.jowashere.blackclover.entities.AiSpellEntry;
import com.github.jowashere.blackclover.entities.mobs.BCEntity;
import com.github.jowashere.blackclover.entities.mobs.ISpellUser;
import com.github.jowashere.blackclover.util.helpers.AttributeHelper;
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
import java.util.ArrayList;

public class BanditEntity extends BCEntity implements ISpellUser
{
    private static final String[] DEFAULT_TEXTURES = new String[]
            {
                "hostile/bandit"
            };


    public BanditEntity(EntityType<? extends CreatureEntity> type, World world) {
        super(type, world, DEFAULT_TEXTURES);
        this.maxML = 25;
        this.minML = 5;
        this.magicXPDrop = 50;
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
            .add(Attributes.ATTACK_DAMAGE, 5)
            .add(Attributes.ARMOR, 5)
            .add(Attributes.MAX_HEALTH, 15)
            .add(Attributes.FOLLOW_RANGE, 35)
            .add(Attributes.MOVEMENT_SPEED, 0.24);

        return addModAttributes(attributes);
    }

    @Override
    public boolean removeWhenFarAway(double d)
    {
        if (d > 48)
            return true;
        else
            return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.SPELL_POOL.isEmpty()) {
            this.addSpells(this, 6);
        }
    }

    @Override
    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag)
    {
        spawnData = super.finalizeSpawn(world, difficulty, reason, spawnData, dataTag);

        this.setAttribute(AttributeHelper.getRandomAttribute(true));
        this.generateGrimoireTextures();
        this.SPELL_POOL = new ArrayList<AiSpellEntry>();
        this.addSpells(this, 6);

        return spawnData;

    }

}

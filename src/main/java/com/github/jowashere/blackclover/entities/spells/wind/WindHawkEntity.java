package com.github.jowashere.blackclover.entities.spells.wind;

import com.sun.istack.internal.NotNull;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;

public class WindHawkEntity extends TameableEntity implements IFlyingAnimal {

    int existTick;

    public WindHawkEntity(EntityType<WindHawkEntity> type, World world) {
        super(type, world);
        xpReward = 0;
        setNoAi(false);
        this.moveControl = new FlyingMovementController(this, 20, true);
        this.navigation = new FlyingPathNavigator(this, this.level);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MoveTowardsTargetGoal(this, 1, 32));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 20, true));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1.0D, 5.0F, 1.0F, true));
        /*this.goalSelector.addGoal(4, new Goal() {
            {
                this.setFlags(EnumSet.of(Goal.Flag.MOVE));
            }
            public boolean canUse() {
                if (WindHawkEntity.this.getTarget() != null && !WindHawkEntity.this.getMoveControl().hasWanted()) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public boolean canContinueToUse() {
                return WindHawkEntity.this.getMoveControl().hasWanted() && WindHawkEntity.this.getTarget() != null
                        && WindHawkEntity.this.getTarget().isAlive();
            }

            @Override
            public void start() {
                LivingEntity livingentity = WindHawkEntity.this.getTarget();
                Vector3d vec3d = livingentity.getEyePosition(1);
                WindHawkEntity.this.moveControl.setWantedPosition(vec3d.x, vec3d.y, vec3d.z, 60);
            }

            @Override
            public void tick() {
                LivingEntity livingentity = WindHawkEntity.this.getTarget();
                if (WindHawkEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                    WindHawkEntity.this.doHurtTarget(livingentity);
                } else {
                    double d0 = WindHawkEntity.this.distanceToSqr(livingentity);
                    if (d0 < 20) {
                        Vector3d vec3d = livingentity.getEyePosition(1);
                        WindHawkEntity.this.moveControl.setWantedPosition(vec3d.x, vec3d.y, vec3d.z, 60);
                    }
                }
            }
        });*/
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(6, new LookAtGoal(this, LivingEntity.class, (float) 6));
        this.targetSelector.addGoal(7, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(8, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(9, new HurtByTargetGoal(this));
    }

    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEFINED;
    }

    @Override
    public boolean causeFallDamage(float l, float d) {
        return false;
    }

    @Override
    public void setNoGravity(boolean ignored) {
        super.setNoGravity(true);
    }

    @NotNull
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.FOLLOW_RANGE, 16.0D).add(Attributes.ATTACK_KNOCKBACK, 2).add(Attributes.MOVEMENT_SPEED, 2).add(Attributes.MAX_HEALTH, 30).add(Attributes.FLYING_SPEED, 2).add(Attributes.ATTACK_DAMAGE, 5);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.setNoGravity(true);
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        Random random = this.random;
        Entity entity = this;
        if (true)
            for (int l = 0; l < 1; ++l) {
                double d0 = (x + random.nextFloat());
                double d1 = (y + random.nextFloat());
                double d2 = (z + random.nextFloat());
                int i1 = random.nextInt(2) * 2 - 1;
                double d3 = (random.nextFloat() - 0.5D) * 0.5000000014901161D;
                double d4 = (random.nextFloat() - 0.5D) * 0.5000000014901161D;
                double d5 = (random.nextFloat() - 0.5D) * 0.5000000014901161D;
                level.addParticle(ParticleTypes.SPIT, d0, d1, d2, d3, d4, d5);
            }
        existTick++;
        if(existTick >= 200){
            this.remove();
        }
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }
}

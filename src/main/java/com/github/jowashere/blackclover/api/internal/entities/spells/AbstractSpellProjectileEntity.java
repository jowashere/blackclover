package com.github.jowashere.blackclover.api.internal.entities.spells;

import com.github.jowashere.blackclover.init.EffectInit;
import com.github.jowashere.blackclover.util.helpers.SpellHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Random;

public abstract class AbstractSpellProjectileEntity extends ProjectileItemEntity{

    private Random random = new Random();
    private float manaIn;

    private int damageTier;
    private float baseDamage;

    public abstract String getAffiliatedSpellName();

    public AbstractSpellProjectileEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public AbstractSpellProjectileEntity(EntityType<? extends ProjectileItemEntity> type, double x, double y, double z, World worldIn, float manaIn) {
        super(type, x, y, z, worldIn);
        this.manaIn = manaIn;
    }

    public AbstractSpellProjectileEntity(EntityType<? extends ProjectileItemEntity> type, LivingEntity livingEntityIn, World worldIn, float manaIn) {
        super(type, livingEntityIn, worldIn);
        this.manaIn = manaIn;
    }

    @Override
    protected void onHit(RayTraceResult result) {

        if (result.getType() == RayTraceResult.Type.ENTITY) {
            if (result.getType() == RayTraceResult.Type.ENTITY) {
                Entity entity = ((EntityRayTraceResult)result).getEntity();
                if (((EntityRayTraceResult)result).getEntity() instanceof LivingEntity)
                {
                    LivingEntity livingEntity = (LivingEntity) ((EntityRayTraceResult)result).getEntity();
                }
                if (this.getOwner() instanceof LivingEntity) {
                    LivingEntity livingShooter = (LivingEntity) this.getOwner();
                    if(livingShooter instanceof PlayerEntity){
                        entity.hurt(DamageSource.thrown(this, this.getOwner()), SpellHelper.spellDamageCalcP((PlayerEntity) livingShooter, damageTier, baseDamage));
                    } else if(livingShooter.hasEffect(EffectInit.MAGIC_LEVEL.get())){
                        entity.hurt(DamageSource.thrown(this, this.getOwner()), SpellHelper.spellDamageCalcE(livingShooter, damageTier, baseDamage));
                    }else {
                        entity.hurt(DamageSource.thrown(this, this.getOwner()), 3F);
                    }
                }else {
                    entity.hurt(DamageSource.thrown(this, this.getOwner()), 3F);
                }
            }
        }

        onHitEffect();

        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)3);
            this.remove();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount >= 600) {
            this.remove();
        }
    }

    public Random getRNG() {
        return random;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public float getManaIn(){
        return manaIn;
    }

    public void setDamageTier(int tier){
        this.damageTier = tier;
    }

    public void setBaseDamage(float baseDamage){
        this.baseDamage = baseDamage;
    }

    protected void onHitEffect(){

    }


}

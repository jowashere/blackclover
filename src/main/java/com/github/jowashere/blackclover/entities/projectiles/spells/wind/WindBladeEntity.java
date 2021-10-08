package com.github.jowashere.blackclover.entities.projectiles.spells.wind;

import com.github.jowashere.blackclover.api.internal.entities.spells.AbstractWindSpellEntity;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.init.EffectInit;
import com.github.jowashere.blackclover.init.EntityInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class WindBladeEntity extends AbstractWindSpellEntity {

    String affiliatedSpell;

    public WindBladeEntity(EntityType<? extends WindBladeEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public WindBladeEntity(World worldIn, LivingEntity throwerIn, String affiliatedSpell) {
        super(EntityInit.WIND_BLADE.get(), throwerIn, worldIn);
        this.affiliatedSpell = affiliatedSpell;
    }

    @Override
    protected void onHit(RayTraceResult result) {

        if (result.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult)result).getEntity();
            if (((EntityRayTraceResult)result).getEntity() instanceof LivingEntity)
            {
                LivingEntity livingEntity = (LivingEntity) ((EntityRayTraceResult)result).getEntity();
            }
            if (this.getOwner() instanceof LivingEntity) {
                LivingEntity livingShooter = (LivingEntity) this.getOwner();

                if(livingShooter instanceof PlayerEntity){
                    PlayerEntity playerIn = (PlayerEntity) livingShooter;
                    IPlayerHandler playercap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));
                    entity.hurt(DamageSource.thrown(this, this.getOwner()), (float) playercap.returnMagicLevel() * 0.55F);
                } else if(livingShooter.hasEffect(EffectInit.MAGIC_LEVEL.get())){
                    entity.hurt(DamageSource.thrown(this, this.getOwner()), (float) livingShooter.getEffect(EffectInit.MAGIC_LEVEL.get()).getAmplifier() * 0.55F);
                }else {
                    entity.hurt(DamageSource.thrown(this, this.getOwner()), 4F);
                }
            }else {
                entity.hurt(DamageSource.thrown(this, this.getOwner()), 4F);
            }
        }

        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)3);
            this.remove();
        }
    }

    @Override
    protected float getGravity() {
        return 0F;
    }

    @Override
    public String getAffiliatedSpellName() {
        return affiliatedSpell;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }
}

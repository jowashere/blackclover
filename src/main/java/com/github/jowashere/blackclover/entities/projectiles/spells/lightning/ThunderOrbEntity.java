package com.github.jowashere.blackclover.entities.projectiles.spells.lightning;

import com.github.jowashere.blackclover.api.internal.entities.spells.AbstractLightningSpellEntity;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.init.EffectInit;
import com.github.jowashere.blackclover.init.EntityInit;
import com.github.jowashere.blackclover.util.helpers.SpellHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ThunderOrbEntity extends AbstractLightningSpellEntity {

    public ThunderOrbEntity(EntityType<? extends ThunderOrbEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public ThunderOrbEntity(World worldIn, LivingEntity throwerIn) {
        super(EntityInit.THUNDER_ORB.get(), throwerIn, worldIn);
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
                    entity.hurt(DamageSource.thrown(this, this.getOwner()), SpellHelper.spellDamageCalcP((PlayerEntity) livingShooter, 2, 3));
                } else if(livingShooter.hasEffect(EffectInit.MAGIC_LEVEL.get())){
                    entity.hurt(DamageSource.thrown(this, this.getOwner()), SpellHelper.spellDamageCalcE(livingShooter, 2, 3));
                }else {
                    entity.hurt(DamageSource.thrown(this, this.getOwner()), 3F);
                }
            }else {
                entity.hurt(DamageSource.thrown(this, this.getOwner()), 3F);
            }
        }

        if (!this.level.isClientSide) {
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 2, Explosion.Mode.NONE);
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
        return "thunder_orb";
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

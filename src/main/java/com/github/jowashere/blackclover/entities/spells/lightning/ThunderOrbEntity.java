package com.github.jowashere.blackclover.entities.spells.lightning;

import com.github.jowashere.blackclover.api.internal.entities.spells.AbstractLightningProjectileEntity;
import com.github.jowashere.blackclover.entities.mobs.BCEntity;
import com.github.jowashere.blackclover.init.EntityInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ThunderOrbEntity extends AbstractLightningProjectileEntity {

    public ThunderOrbEntity(EntityType<? extends ThunderOrbEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public ThunderOrbEntity(World worldIn, LivingEntity throwerIn, float manaIn) {
        super(EntityInit.THUNDER_ORB.get(), throwerIn, worldIn, manaIn);
        this.setDamageTier(2);
        this.setBaseDamage(3);
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

    @Override
    protected void onHitEffect(){
        if(!this.level.isClientSide){
            this.level.explode(this.getOwner(), this.getX(), this.getY(), this.getZ(), 1, Explosion.Mode.NONE);
        }
    }
}

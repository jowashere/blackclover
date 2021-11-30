package com.github.jowashere.blackclover.entities.spells.wind;

import com.github.jowashere.blackclover.api.internal.entities.spells.AbstractWindProjectileEntity;
import com.github.jowashere.blackclover.init.EntityInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class WindCrescentEntity extends AbstractWindProjectileEntity {

    public WindCrescentEntity(EntityType<? extends WindCrescentEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public WindCrescentEntity(World worldIn, LivingEntity throwerIn, float manaIn) {
        super(EntityInit.WIND_CRESCENT.get(), throwerIn, worldIn, manaIn);
        this.setDamageTier(2);
        this.setBaseDamage(3.5F);
    }

    @Override
    protected float getGravity() {
        return 0F;
    }

    @Override
    public String getAffiliatedSpellName() {
        return "wind_crescent";
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

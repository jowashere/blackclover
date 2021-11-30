package com.github.jowashere.blackclover.entities.spells.slash;

import com.github.jowashere.blackclover.api.internal.entities.spells.AbstractWindProjectileEntity;
import com.github.jowashere.blackclover.init.EntityInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class DeathScytheEntity extends AbstractWindProjectileEntity {

    public DeathScytheEntity(EntityType<? extends DeathScytheEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public DeathScytheEntity(World worldIn, LivingEntity throwerIn, float manaIn) {
        super(EntityInit.DEATH_SCYTHE.get(), throwerIn, worldIn, manaIn);
        this.setDamageTier(2);
        this.setBaseDamage(3.5F);
    }

    @Override
    protected float getGravity() {
        return 0F;
    }

    @Override
    public String getAffiliatedSpellName() {
        return "death_scythe";
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

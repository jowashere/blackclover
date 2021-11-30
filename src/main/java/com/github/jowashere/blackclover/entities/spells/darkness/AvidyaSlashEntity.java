package com.github.jowashere.blackclover.entities.spells.darkness;

import com.github.jowashere.blackclover.api.internal.entities.spells.AbstractDarknessProjectileEntity;
import com.github.jowashere.blackclover.init.EntityInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class AvidyaSlashEntity extends AbstractDarknessProjectileEntity {

    public AvidyaSlashEntity(EntityType<? extends AvidyaSlashEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public AvidyaSlashEntity(World worldIn, LivingEntity throwerIn, float manaIn) {
        super(EntityInit.AVIDYA_SLASH.get(), throwerIn, worldIn, manaIn);
        this.setDamageTier(2);
        this.setBaseDamage(4);
    }

    @Override
    protected float getGravity() {
        return 0F;
    }

    @Override
    public String getAffiliatedSpellName() {
        return "avidya_slash";
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

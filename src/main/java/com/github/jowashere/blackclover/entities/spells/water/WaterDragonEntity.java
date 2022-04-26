package com.github.jowashere.blackclover.entities.spells.water;

import com.github.jowashere.blackclover.api.internal.entities.spells.AbstractWaterProjectileEntity;
import com.github.jowashere.blackclover.init.EntityInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class WaterDragonEntity extends AbstractWaterProjectileEntity {

    String affiliatedSpell;

    public WaterDragonEntity(EntityType<? extends WaterDragonEntity> type, World worlIn)
    {
        super(type, worlIn);
    }

    public WaterDragonEntity(World worldIn, LivingEntity throwerIn, String affiliatedSpell, float manaIn) {
        super(EntityInit.WATER_DRAGON.get(), throwerIn, worldIn, manaIn);
        this.affiliatedSpell = affiliatedSpell;
        this.setDamageTier(4);
        this.setBaseDamage(20F);
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
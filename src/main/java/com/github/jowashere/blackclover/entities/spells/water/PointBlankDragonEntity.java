package com.github.jowashere.blackclover.entities.spells.water;

import com.github.jowashere.blackclover.api.internal.entities.spells.AbstractWaterProjectileEntity;
import com.github.jowashere.blackclover.init.EntityInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class PointBlankDragonEntity extends AbstractWaterProjectileEntity
{

    String affiliatedSpell;

    public PointBlankDragonEntity(EntityType<? extends PointBlankDragonEntity> type, World worlIn)
    {
        super(type, worlIn);
    }

    public PointBlankDragonEntity(World worldIn, LivingEntity throwerIn, String affiliatedSpell, float manaIn) {
        super(EntityInit.POINT_BLANK_DRAGON.get(), throwerIn, worldIn, manaIn);
        this.affiliatedSpell = affiliatedSpell;
        this.setDamageTier(1);
        this.setBaseDamage(3.5F);
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

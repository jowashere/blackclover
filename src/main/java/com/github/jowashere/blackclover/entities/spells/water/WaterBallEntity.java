package com.github.jowashere.blackclover.entities.spells.water;

import com.github.jowashere.blackclover.api.internal.entities.spells.AbstractWaterProjectileEntity;
import com.github.jowashere.blackclover.entities.spells.wind.WindBladeEntity;
import com.github.jowashere.blackclover.init.EntityInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class WaterBallEntity extends AbstractWaterProjectileEntity
{
    String affiliatedSpell;

    public WaterBallEntity(EntityType<? extends WaterBallEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public WaterBallEntity(World worldIn, LivingEntity throwerIn, String affiliatedSpell, float manaIn) {
        super(EntityInit.WATER_BALL.get(), throwerIn, worldIn, manaIn);
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

package com.github.jowashere.blackclover.entities.spells.wind;

import com.github.jowashere.blackclover.api.internal.entities.spells.AbstractWindProjectileEntity;
import com.github.jowashere.blackclover.init.EntityInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class WindBladeEntity extends AbstractWindProjectileEntity {

    String affiliatedSpell;

    public WindBladeEntity(EntityType<? extends WindBladeEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public WindBladeEntity(World worldIn, LivingEntity throwerIn, String affiliatedSpell, float manaIn) {
        super(EntityInit.WIND_BLADE.get(), throwerIn, worldIn, manaIn);
        this.affiliatedSpell = affiliatedSpell;
        this.setDamageTier(1);
        this.setBaseDamage(2.5F);
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

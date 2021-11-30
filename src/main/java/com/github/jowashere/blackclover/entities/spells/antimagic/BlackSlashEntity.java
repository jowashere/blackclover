package com.github.jowashere.blackclover.entities.spells.antimagic;

import com.github.jowashere.blackclover.api.internal.entities.spells.AbstractAntiMagicProjectileEntity;
import com.github.jowashere.blackclover.init.EntityInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BlackSlashEntity extends AbstractAntiMagicProjectileEntity {

    public BlackSlashEntity(EntityType<? extends BlackSlashEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public BlackSlashEntity(World worldIn, LivingEntity throwerIn, float manaIn) {
        super(EntityInit.BLACK_SLASH.get(), throwerIn, worldIn, manaIn);
        this.setDamageTier(2);
        this.setBaseDamage(4);
    }

    @Override
    protected float getGravity() {
        return 0F;
    }

    @Override
    public String getAffiliatedSpellName() {
        return "black_slash";
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

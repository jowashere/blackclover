package com.github.jowashere.blackclover.api.internal.entities.spells;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.world.World;

public abstract class AbstractLightningProjectileEntity extends AbstractSpellProjectileEntity {

    public AbstractLightningProjectileEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public AbstractLightningProjectileEntity(EntityType<? extends ProjectileItemEntity> type, double x, double y, double z, World worldIn, float manaIn) {
        super(type, x, y, z, worldIn, manaIn);
    }

    public AbstractLightningProjectileEntity(EntityType<? extends ProjectileItemEntity> type, LivingEntity livingEntityIn, World worldIn, float manaIn) {
        super(type, livingEntityIn, worldIn, manaIn);
    }
}

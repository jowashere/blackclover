package com.github.jowashere.blackclover.api.internal.entities.spells;

import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.world.World;

import java.util.List;

public abstract class AbstractDarknessProjectileEntity extends AbstractSpellProjectileEntity {

    public AbstractDarknessProjectileEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public AbstractDarknessProjectileEntity(EntityType<? extends ProjectileItemEntity> type, double x, double y, double z, World worldIn, float manaIn) {
        super(type, x, y, z, worldIn, manaIn);
    }

    public AbstractDarknessProjectileEntity(EntityType<? extends ProjectileItemEntity> type, LivingEntity livingEntityIn, World worldIn, float manaIn) {
        super(type, livingEntityIn, worldIn, manaIn);
    }

    @Override
    public void tick() {
        super.tick();
        List<AbstractLightProjectileEntity> entities = BCMHelper.GetEntitiesNear(this.blockPosition(), this.level, 1.5, AbstractLightProjectileEntity.class);

        entities.forEach(entity -> {
            if(entity.getManaIn() < (this.getManaIn() * 0.65)){
                entity.remove();
            }
        });
    }

}

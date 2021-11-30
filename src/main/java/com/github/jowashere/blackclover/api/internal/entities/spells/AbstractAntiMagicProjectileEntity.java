package com.github.jowashere.blackclover.api.internal.entities.spells;

import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.world.World;

import java.util.List;

public abstract class AbstractAntiMagicProjectileEntity extends AbstractSpellProjectileEntity {

    public AbstractAntiMagicProjectileEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public AbstractAntiMagicProjectileEntity(EntityType<? extends ProjectileItemEntity> type, double x, double y, double z, World worldIn, float manaIn) {
        super(type, x, y, z, worldIn, manaIn);
    }

    public AbstractAntiMagicProjectileEntity(EntityType<? extends ProjectileItemEntity> type, LivingEntity livingEntityIn, World worldIn, float manaIn) {
        super(type, livingEntityIn, worldIn, manaIn);
    }

    @Override
    public void tick() {
        super.tick();
        List<AbstractSpellProjectileEntity> entities = BCMHelper.GetEntitiesNear(this.blockPosition(), this.level, 1.5, AbstractSpellProjectileEntity.class);

        entities.forEach(entity -> {
            if(!(entity instanceof AbstractAntiMagicProjectileEntity)){
                if(entity.getManaIn() < (this.getManaIn() * 0.55)){
                    entity.remove();
                }
            }
        });
    }
}

package com.github.jowashere.blackclover.api.internal.entities.spells;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public abstract class AbstractSlashProjectileEntity extends AbstractSpellProjectileEntity {

    public AbstractSlashProjectileEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public AbstractSlashProjectileEntity(EntityType<? extends ProjectileItemEntity> type, double x, double y, double z, World worldIn, float manaIn) {
        super(type, x, y, z, worldIn, manaIn);
    }

    public AbstractSlashProjectileEntity(EntityType<? extends ProjectileItemEntity> type, LivingEntity livingEntityIn, World worldIn, float manaIn) {
        super(type, livingEntityIn, worldIn, manaIn);
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult result)
    {
        if (!this.level.isClientSide)
        {
            BlockPos pos = result.getBlockPos();
            World world = this.level;
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
        }
    }


}

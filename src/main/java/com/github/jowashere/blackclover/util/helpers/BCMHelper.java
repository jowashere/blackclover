package com.github.jowashere.blackclover.util.helpers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class BCMHelper {

    public static Vector3d propulsion(LivingEntity entity, double extraVelX, double extraVelZ)
    {
        return propulsion(entity, extraVelX, 0, extraVelZ);
    }

    public static Vector3d propulsion(LivingEntity entity, double extraVelX, double extraVelY, double extraVelZ)
    {
        return entity.getLookAngle().multiply(extraVelX, extraVelY, extraVelZ);
    }

    public static <T extends Entity> List<T> getEntitiesNear(BlockPos pos, World world, double radius)
    {
        return (List<T>) getEntitiesNear(pos, world, radius, LivingEntity.class);
    }

    public static <T extends Entity> List<T> getEntitiesNear(BlockPos pos, World world, double radius, Class<? extends T>... classEntities)
    {
        return getEntitiesNear(pos, world, radius, null, classEntities);
    }

    public static <T extends Entity> List<T> getEntitiesNear(BlockPos pos, World world, double radius, Predicate<Entity> predicate, Class<? extends T>... classEntities)
    {
        if(predicate != null)
            predicate = predicate.and(EntityPredicates.NO_SPECTATORS);
        else
            predicate = EntityPredicates.NO_SPECTATORS;

        AxisAlignedBB aabb = new AxisAlignedBB(pos).expandTowards(radius, radius, radius);
        List<T> list = new ArrayList<T>();
        for (Class<? extends T> clzz : classEntities)
        {
            list.addAll(world.getEntitiesOfClass(clzz, aabb, predicate));
        }
        return list;
    }
}

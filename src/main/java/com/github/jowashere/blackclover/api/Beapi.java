package com.github.jowashere.blackclover.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class Beapi
{
    public static RayTraceResult rayTraceBlocksAndEntities(Entity entity) {
        return Beapi.rayTraceBlocksAndEntities(entity, 1024, 0.4f);
    }

    public static RayTraceResult rayTraceBlocksAndEntities(Entity entity, double distance) {
        return Beapi.rayTraceBlocksAndEntities(entity, distance, 0.2f);
    }

    public static RayTraceResult rayTraceBlocksAndEntities(Entity entity, double distance, float entityBoxRange) {
        Vector3d lookVec = entity.getLookAngle();
        Vector3d startVec = entity.position().add(0, entity.getEyeHeight(), 0);
        Vector3d endVec = startVec.add(entity.getLookAngle().scale(distance));
        RayTraceResult blockResult = entity.level.clip(new RayTraceContext(startVec, endVec,  RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity));
        RayTraceResult entityResult = null;

        for (int i = 0; i < distance * 2; i++)
        {
            if (entityResult != null)
                break;

            float scale = i / 2F;
            Vector3d pos = startVec.add(lookVec.scale(scale));

            Vector3d min = pos.add(entityBoxRange, entityBoxRange, entityBoxRange);
            Vector3d max = pos.add(-entityBoxRange, -entityBoxRange, -entityBoxRange);
            List<Entity> list = entity.level.getEntities(entity, new AxisAlignedBB(min.x, min.y, min.z, max.x, max.y, max.z));
            list.remove(entity);
            for (Entity e : list) {


                entityResult = new EntityRayTraceResult(e, pos);
                break;
            }
        }

        if (entityResult != null && entityResult.getLocation().distanceTo(startVec) <= blockResult.getLocation().distanceTo(startVec)) {
            return entityResult;
        } else {
            return blockResult;
        }

    }

}

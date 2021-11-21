package com.github.jowashere.blackclover.util.helpers;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.events.PlayerEvents;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.PacketMagicExpSync;
import com.github.jowashere.blackclover.networking.packets.PacketMagicLevel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

        AxisAlignedBB aabb = new AxisAlignedBB(pos.getX() - (radius / 2d), pos.getY() - (radius / 2d), pos.getZ() - (radius / 2d), pos.getX() + (radius / 2d), pos.getY() + (radius / 2d), pos.getZ() + (radius / 2d));
        List<T> list = new ArrayList<T>();
        for (Class<? extends T> clzz : classEntities)
        {
            //list.addAll(world.getEntitiesOfClass(clzz, aabb, predicate));
            list.addAll(world
                    .getEntitiesOfClass(clzz, aabb, predicate) .stream().sorted(new Object() {
                        Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                            return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.distanceToSqr(_x, _y, _z)));
                        }
                    }.compareDistOf(pos.getX(), pos.getY(), pos.getZ())).collect(Collectors.toList()));
        }

        return list;
    }

    public static EntityRayTraceResult rayTraceEntities(Entity source, double distance)
    {
        Vector3d startVec = source.position().add(0, source.getEyeHeight(), 0);
        Vector3d endVec = startVec.add(source.getLookAngle().scale(distance));
        AxisAlignedBB boundingBox = source.getBoundingBox().inflate(distance);

        for (Entity entity : source.level.getEntities(source, boundingBox, (entity) ->
        {
            return entity != source;
        }))
        {
            AxisAlignedBB entityBB = entity.getBoundingBox().inflate(1);
            Optional<Vector3d> optional = entityBB.clip(startVec, endVec);

            if (optional.isPresent())
            {
                Vector3d targetVec = optional.get();
                double distFromSource = MathHelper.sqrt(startVec.distanceToSqr(targetVec));

                if (distFromSource < distance)
                {
                    List<Entity> targets = BCMHelper.getEntitiesNear(new BlockPos(targetVec), source.level, 1.25);
                    targets.remove(source);
                    Optional<Entity> target = targets.stream().findFirst();

                    if (target.isPresent())
                    {
                        return new EntityRayTraceResult(target.get(), endVec);
                    }
                }
            }
        }

        return new EntityRayTraceResult(null, endVec);
    }

    public static float calculateExp (int level) {
        return (float) ((level / 0.07) * (level / 0.07));
    }

    public static int calculateLevel (float exp){
        return 1 + (int) (0.07 * Math.sqrt(exp));
    }

    public static void recaculateMagicLevel(PlayerEntity player){

        LazyOptional<IPlayerHandler> playerCapability = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerCapability.orElse(new PlayerCapability());

        int magicLevel = BCMHelper.calculateLevel(player_cap.returnMagicExp());

        if(magicLevel != player_cap.returnMagicLevel()){

            player.displayClientMessage(new TranslationTextComponent("message." + Main.MODID + ".levelup", magicLevel), false);
            player_cap.setMagicLevel(magicLevel);
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketMagicLevel(magicLevel, player.getId()));
        }
    }

}

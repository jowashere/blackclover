package com.github.jowashere.blackclover.util.helpers;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.entities.mobs.BCEntity;
import com.github.jowashere.blackclover.events.bcevents.MagicLevelChangeEvent;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.PacketMagicLevel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BCMHelper {

    public static boolean isNullOrEmpty(String str)
    {
        if (str != null && !str.isEmpty() && !str.equalsIgnoreCase("n/a"))
            return false;
        return true;
    }

    public static Vector3d Propulsion(LivingEntity entity, double extraVelX, double extraVelZ)
    {
        return Propulsion(entity, extraVelX, 0, extraVelZ);
    }

    public static Vector3d Propulsion(LivingEntity entity, double extraVelX, double extraVelY, double extraVelZ)
    {
        return entity.getLookAngle().multiply(extraVelX, extraVelY, extraVelZ);
    }

    public static <T extends Entity> List<T> GetEntitiesNear(BlockPos pos, World world, double diameter)
    {
        return (List<T>) GetEntitiesNear(pos, world, diameter, LivingEntity.class);
    }

    public static <T extends Entity> List<T> GetEntitiesNear(BlockPos pos, World world, double diameter, Class<? extends T>... classEntities)
    {
        return GetEntitiesNear(pos, world, diameter, null, classEntities);
    }

    public static <T extends Entity> List<T> GetEntitiesNear(BlockPos pos, World world, double diameter, Predicate<Entity> predicate, Class<? extends T>... classEntities)
    {
        if(predicate != null)
            predicate = predicate.and(EntityPredicates.NO_SPECTATORS);
        else
            predicate = EntityPredicates.NO_SPECTATORS;

        AxisAlignedBB aabb = new AxisAlignedBB(pos.getX() - (diameter / 2d), pos.getY() - (diameter / 2d), pos.getZ() - (diameter / 2d), pos.getX() + (diameter / 2d), pos.getY() + (diameter / 2d), pos.getZ() + (diameter / 2d));
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

    public static RayTraceResult RayTraceBlocksAndEntities(Entity entity) {
        return BCMHelper.RayTraceBlocksAndEntities(entity, 1024, 0.4f);
    }

    public static RayTraceResult RayTraceBlocksAndEntities(Entity entity, double distance) {
        return BCMHelper.RayTraceBlocksAndEntities(entity, distance, 0.2f);
    }

    public static RayTraceResult RayTraceBlocksAndEntities(Entity entity, double distance, float entityBoxRange) {
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
                /*if(e instanceof LightningEntity)
                    continue;*/

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

    public static BlockPos RayTraceBlockSafe(LivingEntity entity, float range)
    {
        World world = entity.level;
        Vector3d startVec = entity.position().add(0.0, entity.getEyeHeight(), 0.0);
        Vector3d endVec = startVec.add(entity.getLookAngle().scale(range));
        BlockRayTraceResult result = world.clip(new RayTraceContext(startVec, endVec,  RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, entity));
        BlockPos dashPos = result.getDirection().equals(Direction.DOWN) ? result.getBlockPos().below(2) : result.getBlockPos().offset(result.getDirection().getNormal());

        boolean posIsFree = BCMHelper.IsPosClearForPlayer(world, dashPos);
        boolean tryUp = true;

        while (!posIsFree)
        {
            if(tryUp)
            {
                dashPos = dashPos.above();
                BlockPos bpb = dashPos.below();
                Vector3d v3d = new Vector3d(bpb.getX(), bpb.getY(), bpb.getZ());
                posIsFree = BCMHelper.IsPosClearForPlayer(world, dashPos) && world.clip(new RayTraceContext(startVec, v3d,
                        RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, entity)).getType().equals(RayTraceResult.Type.MISS);
                if(world.getMaxBuildHeight() >= dashPos.getY())
                    tryUp = false;
            } else
            {
                dashPos = dashPos.below();
                BlockPos bpa = dashPos.above();
                Vector3d v3d = new Vector3d(bpa.getX(), bpa.getY(), bpa.getZ());
                posIsFree = BCMHelper.IsPosClearForPlayer(world, dashPos) && world.clip(new RayTraceContext(startVec, v3d,
                        RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, entity)).getType().equals(RayTraceResult.Type.MISS);
                if(dashPos.getY() <= 0)
                    break;
            }
        }

        return (posIsFree) ? dashPos : null;
    }

    public static boolean IsPosClearForPlayer(World world, BlockPos pos)
    {
        return (world.isEmptyBlock(pos) || world.getBlockState(pos).getCollisionShape(world, pos).isEmpty())
                && (world.isEmptyBlock(pos.above()) || world.getBlockState(pos.above()).getCollisionShape(world, pos.above()).isEmpty());
    }

    public static BlockRayTraceResult RayTraceBlocks(Entity source, double distance)
    {
        Vector3d startVec = source.position().add(0, source.getEyeHeight(), 0);
        Vector3d endVec = startVec.add(source.getLookAngle().scale(distance));
        return source.level.clip(new RayTraceContext(startVec, endVec,  RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, source));
    }

    public static EntityRayTraceResult RayTraceEntities(Entity source, double distance)
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
                    List<Entity> targets = BCMHelper.GetEntitiesNear(new BlockPos(targetVec), source.level, 1.25, Entity.class);
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

    public static float CalculateExp(int level) {
        return (float) (((level - 1) / 0.07) * ((level - 1) / 0.07));
    }

    public static int CalculateLevel(float exp){
        return 1 + (int) (0.07 * Math.sqrt(exp));
    }

    public static void recaculateMagicLevel(PlayerEntity player){

        LazyOptional<IPlayerHandler> playerCapability = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerCapability.orElse(new PlayerCapability());

        int magicLevel = BCMHelper.CalculateLevel(player_cap.returnMagicExp());

        if(magicLevel != player_cap.ReturnMagicLevel()){

            MinecraftForge.EVENT_BUS.post(new MagicLevelChangeEvent(player, player_cap.ReturnMagicLevel(), magicLevel));

            player.displayClientMessage(new TranslationTextComponent("message." + Main.MODID + ".levelup", magicLevel), false);
            player_cap.setMagicLevel(magicLevel);
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketMagicLevel(magicLevel, player.getId()));
        }
    }

    public static int getMagicLevel(LivingEntity entity){

        if(entity instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) entity;

            LazyOptional<IPlayerHandler> playerCapability = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerCapability.orElse(new PlayerCapability());

            return player_cap.ReturnMagicLevel();

        }else if (entity instanceof BCEntity){

            return ((BCEntity)entity).getMagicLevel();
        }

        return 1;
    }

    public static void doSpellDamage(LivingEntity attacker, LivingEntity target, float amount){
        if(attacker instanceof PlayerEntity)
            target.hurt(DamageSource.playerAttack((PlayerEntity) attacker), amount);
        else
            target.hurt(DamageSource.mobAttack((attacker)), amount);

    }

    public static void GiveItem(LivingEntity entity, ItemStack stack){
        if (entity instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) entity;
            if (player.getItemInHand(player.getUsedItemHand()).isEmpty() && !stack.isEmpty()) {
                player.inventory.setItem(player.inventory.selected, stack);
            } else {
                if (!stack.isEmpty()) {
                    ItemHandlerHelper.giveItemToPlayer(player, stack);
                }
            }
        } else {
            entity.setItemInHand(Hand.MAIN_HAND, stack);
        }
    }

    public static void waitThen(World world, int ticks, Wait wait) {

        new Object() {
            private int ticks = 0;
            private float waitTicks;
            private IWorld world;
            public void start (IWorld world,int waitTicks){
                this.waitTicks = waitTicks;
                MinecraftForge.EVENT_BUS.register(this);
                this.world = world;
            }

            @SubscribeEvent
            public void tick (TickEvent.ServerTickEvent event){
                if (event.phase == TickEvent.Phase.END) {
                    this.ticks += 1;
                    if (this.ticks >= this.waitTicks){
                        wait.waitThen();
                        MinecraftForge.EVENT_BUS.unregister(this);
                    }

                }
            }

        }.start(world, (int) ticks);

    }

    public interface Wait {
        void waitThen();
    }

    public static double getDifferenceToFloor(PlayerEntity player) {
        Vector3d startVec = player.position();
        Vector3d endVec = startVec.add(0, -256, 0);
        RayTraceResult blockResult = player.level.clip(new RayTraceContext(startVec, endVec,  RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));
        return startVec.subtract(blockResult.getLocation()).y;
    }

}

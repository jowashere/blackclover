package com.github.jowashere.blackclover.events.spells;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class WindMagicEvents {

    @Mod.EventBusSubscriber(modid = Main.MODID)
    public static class CommonEvents
    {
        //private static final AttributeModifier COCOON_JUMP = new AttributeModifier(UUID.fromString("ad388521-c053-4a67-a0d9-ff57379a2c68"), "Cocoon Speed", -256, AttributeModifier.Operation.ADDITION);

        private static float flightSpeed = 0;

        @SubscribeEvent
        public static void onEntityUpdate(LivingEvent.LivingUpdateEvent event)
        {
            if (!(event.getEntityLiving() instanceof PlayerEntity))
                return;

            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            World world = player.level;

            //if(world.isClientSide)
                //return;

            LazyOptional<IPlayerHandler> playerInCap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

            if(!player_cap.ReturnMagicAttribute().equals(AttributeInit.WIND))
                return;

            if(player.getPersistentData().getBoolean("blackclover_wind_flight")) {
                float maxSpeed = 0.5f + (player_cap.ReturnMagicLevel() / 100);
                float acceleration = 0.005f;

                acceleration *= (flightSpeed > 0 ? (1 - flightSpeed / maxSpeed) : 1);
                acceleration = (player.zza > 0 && !player.verticalCollision) ? acceleration : -maxSpeed / 10;
                flightSpeed = MathHelper.clamp(flightSpeed + acceleration, acceleration > 0 ? maxSpeed / 4 : 0, maxSpeed);

                int d1 = player.isOnGround() ? 1 : 0;
                int d2 = Float.compare(player.zza, 0F);

                Vector3d vec = player.getLookAngle();
                double x = (vec.x * flightSpeed) * (1 - d1) * d2;
                double y = d1 * 0.6f + (vec.y * flightSpeed) * (1 - d1) * d2 + (Math.cos(player.tickCount / 0.9f) / 5f);
                double z = (vec.z * flightSpeed) * (1 - d1) * d2;
                player.setDeltaMovement(x, y, z);

                double difference = BCMHelper.getDifferenceToFloor(player);

                if (difference > 40) {
                    player.setDeltaMovement(player.getDeltaMovement()
                            .add(0, 0.25 * (difference / 5f), 0)
                            .multiply(1, 1 * -0.15, 1));
                }
            }
        }

        /*@SubscribeEvent
        public static void onEntityAttackEvent(LivingAttackEvent event)
        {
            if (!(event.getEntityLiving() instanceof PlayerEntity))
                return;

            PlayerEntity attacked = (PlayerEntity) event.getEntityLiving();

            LazyOptional<IPlayerHandler> playerInCap = attacked.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());


            if (!player_cap.ReturnMagicAttribute().equals(AttributeInit.DARKNESS))
                return;

            DamageSource source = event.getSource();
            Entity instantSource = source.getDirectEntity();

            if (attacked.getPersistentData().getBoolean("blackclover_black_cocoon"))
            {

                if(source.getEntity() instanceof LivingEntity){
                    LivingEntity livingEntity = (LivingEntity) source.getEntity();

                    if(BCMHelper.GetMagicLevel(livingEntity) < BCMHelper.GetMagicLevel(attacked) + 10)
                        event.setCanceled(true);
                }
            }
        }*/

    }




}

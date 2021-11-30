package com.github.jowashere.blackclover.events.spells;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class DarknessMagicEvents {

    @Mod.EventBusSubscriber(modid = Main.MODID)
    public static class CommonEvents
    {
        //private static final AttributeModifier COCOON_JUMP = new AttributeModifier(UUID.fromString("ad388521-c053-4a67-a0d9-ff57379a2c68"), "Cocoon Speed", -256, AttributeModifier.Operation.ADDITION);

        @SubscribeEvent
        public static void onEntityUpdate(LivingEvent.LivingUpdateEvent event)
        {
            if (!(event.getEntityLiving() instanceof PlayerEntity))
                return;

            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            World world = player.level;

            if(world.isClientSide)
                return;

            LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
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

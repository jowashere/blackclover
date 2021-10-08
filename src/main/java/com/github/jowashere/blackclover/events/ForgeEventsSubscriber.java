package com.github.jowashere.blackclover.events;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.entities.summons.WindHawkEntity;
import com.github.jowashere.blackclover.init.EntityInit;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.modes.PacketModeSync;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventsSubscriber {

    private static boolean curiosLoaded = false;

    @SubscribeEvent
    public static void entityJoinWorld(EntityJoinWorldEvent event) {
        if (!event.getWorld().isClientSide) {
            if (event.getEntity() instanceof PlayerEntity) {
                PlayerEvents.PlayerJoinedWorld(event);

                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) event.getEntity();
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;

        if (player.isAlive()) {
            IPlayerHandler playercap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));

            if (!player.level.isClientSide) {
                PlayerEvents.regenerateMana(event);
                PlayerEvents.setPlayerSpells(event);
                PlayerEvents.cooldowns(event);
                if(playercap.hasManaBoolean()){
                    PlayerEvents.manaRuns(event);
                    PlayerEvents.magicBuffs(event);
                }
            }
        }



        if(curiosLoaded)
            return;

        if(event.phase != TickEvent.Phase.START)
            return;
    }

    @SubscribeEvent
    public static void onPlayerTracking(PlayerEvent.StartTracking event)
    {
        if (event.getTarget() instanceof PlayerEntity)
        {
            PlayerEntity target = (PlayerEntity) event.getTarget();
            if (!event.getEntity().level.isClientSide) {
                int targetID = target.getId();
                event.getTarget().getId();
                LazyOptional<IPlayerHandler> capabilities = target.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                IPlayerHandler targetcap = capabilities.orElse(new PlayerCapability());
                NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> (ServerPlayerEntity) event.getPlayer()), new PacketModeSync(targetcap.returnPlayerMode().getName(), targetID, true));

            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event){
        if(event.getEntity() instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) event.getEntity();
            LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = capabilities.orElse(new PlayerCapability());

            if(player.isAlive()){
                if(player_cap.returnManaSkinToggled()){
                    event.setAmount(PlayerEvents.manaSkinReduction(event));
                }
            }
        }
    }

}

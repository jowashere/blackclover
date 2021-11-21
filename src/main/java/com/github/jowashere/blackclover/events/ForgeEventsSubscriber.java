package com.github.jowashere.blackclover.events;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.entities.summons.WindHawkEntity;
import com.github.jowashere.blackclover.init.EntityInit;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.PacketSetGrimoire;
import com.github.jowashere.blackclover.networking.packets.PacketSpellModeToggle;
import com.github.jowashere.blackclover.networking.packets.PacketToggleInfusionBoolean;
import com.github.jowashere.blackclover.networking.packets.modes.PacketModeSync;
import com.github.jowashere.blackclover.networking.packets.settings.PacketSetGrimoireTexture;
import com.github.jowashere.blackclover.networking.packets.spells.PacketKeybindCD;
import com.github.jowashere.blackclover.networking.packets.spells.PacketSpellNBTSync;
import com.github.jowashere.blackclover.util.helpers.SpellHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.NoteBlockEvent;
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
            for (BCMSpell spell : BCMRegistry.SPELLS.getValues()) {
                if (spell.isToggle()) {
                    String nbtName = spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName();
                    if (player.getPersistentData().getBoolean(nbtName)) {

                        int modifier0 = Math.max(0, playercap.returnMagicLevel() / 5);
                        int modifier1 = Math.max(0, playercap.returnMagicLevel() / 5) - 1;
                        spell.act(player, modifier0, modifier1, SpellHelper.findSpellKey(player, spell));

                    }
                }
            }

            if (!player.level.isClientSide) {
                PlayerEvents.regenerateMana(event);
                PlayerEvents.setPlayerSpells(event);
                //PlayerEvents.magicLevel(event);
                PlayerEvents.specialSpellNbt(event);
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
    @OnlyIn(Dist.CLIENT)
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        PlayerEntity player = Minecraft.getInstance().player;

        if(player != null && player.isAlive()){
            PlayerEvents.cooldowns(event);
        }
    }

    @SubscribeEvent
    public static void onPlayerTracking(PlayerEvent.StartTracking event)
    {
        if (event.getTarget() instanceof PlayerEntity)
        {
            PlayerEntity target = (PlayerEntity) event.getTarget();
            if (!event.getEntity().level.isClientSide) {
                int playerID = event.getPlayer().getId();
                LazyOptional<IPlayerHandler> playerCapability = event.getPlayer().getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                IPlayerHandler playercap = playerCapability.orElse(new PlayerCapability());

                int targetID = target.getId();
                LazyOptional<IPlayerHandler> targetCapability = target.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                IPlayerHandler targetcap = targetCapability.orElse(new PlayerCapability());
                NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> (ServerPlayerEntity) event.getPlayer()), new PacketModeSync(targetcap.returnPlayerMode().getName(), targetID, true));
                NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> (ServerPlayerEntity) event.getPlayer()), new PacketSpellModeToggle(true, targetcap.returnSpellModeToggle(), targetID));
                NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> (ServerPlayerEntity) event.getPlayer()), new PacketToggleInfusionBoolean(1, true, targetcap.returnManaSkinToggled(), targetID));
                NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> (ServerPlayerEntity) event.getPlayer()), new PacketToggleInfusionBoolean(2, true, targetcap.returnReinforcementToggled(), targetID));
                NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> (ServerPlayerEntity) event.getPlayer()), new PacketSetGrimoire(targetcap.returnHasGrimoire(), true, targetID));
                NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> (ServerPlayerEntity) event.getPlayer()), new PacketSetGrimoireTexture(targetcap.getGrimoireTexture(), true, targetID));
                for (BCMSpell spell : BCMRegistry.SPELLS.getValues()) {
                    NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> (ServerPlayerEntity) event.getPlayer()), new PacketSpellNBTSync(targetID, spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName(), target.getPersistentData().getBoolean(spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName())));
                }
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

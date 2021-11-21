package com.github.jowashere.blackclover.capabilities;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.*;
import com.github.jowashere.blackclover.networking.packets.modes.PacketHasModeSync;
import com.github.jowashere.blackclover.networking.packets.modes.PacketModeSync;
import com.github.jowashere.blackclover.networking.packets.settings.PacketKeybindSet;
import com.github.jowashere.blackclover.networking.packets.spells.PacketKeybindCD;
import com.github.jowashere.blackclover.networking.packets.spells.PacketSetSpellBoolean;
import com.github.jowashere.blackclover.networking.packets.spells.PacketSpellNBTSync;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber
public class CapabilityHandler {

    public static final ResourceLocation CAPABILITY_PLAYER = new ResourceLocation(Main.MODID, "player");

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof PlayerEntity)) return;

        event.addCapability(CAPABILITY_PLAYER, new PlayerProvider());
    }

    @SubscribeEvent
    public void entityJoinWorld(EntityJoinWorldEvent event) {

        if (!event.getWorld().isClientSide && event.getEntity() instanceof PlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getEntity();
            LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());

            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketToggleInfusionBoolean(1, true, playercap.returnManaSkinToggled(), player.getId()));
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketToggleInfusionBoolean(2, true, playercap.returnReinforcementToggled(), player.getId()));

            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketSpellModeToggle(true, playercap.returnSpellModeToggle(), player.getId()));

            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketMagicExpSync(playercap.returnMagicExp(), player.getId()));
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketMagicLevel(playercap.returnMagicLevel(), player.getId()));
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketModeSync(playercap.returnPlayerMode().getName(), player.getId(), true));
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketRaceSync(playercap.returnRace().getString(), true));
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketAttributeSync(playercap.returnMagicAttribute().getString(), true));

            //NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketHasModeSync(1, playercap.returnPlayerCurseMark(), true));

            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketSetGrimoire(playercap.returnHasGrimoire(), true, player.getId()));

            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketKeybindSet(1, playercap.returnKeybind(1), true));
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketKeybindSet(2, playercap.returnKeybind(2), true));
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketKeybindSet(3, playercap.returnKeybind(3), true));
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketKeybindSet(4, playercap.returnKeybind(4), true));
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketKeybindSet(5, playercap.returnKeybind(5), true));
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketKeybindSet(6, playercap.returnKeybind(6), true));
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketKeybindSet(7, playercap.returnKeybind(7), true));
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketKeybindSet(8, playercap.returnKeybind(8), true));
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketKeybindSet(9, playercap.returnKeybind(9), true));

            for (BCMSpell spell : BCMRegistry.SPELLS.getValues()) {
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketSetSpellBoolean(spell.getName(), spell.hasSpell(spell, playercap), true));
                String nbtName = spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName();
                player.getPersistentData().putBoolean(nbtName, false);
                NetworkLoader.INSTANCE.send(PacketDistributor.ALL.noArg(), new PacketSpellNBTSync(player.getId(), nbtName, false));
            }

            //NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new PacketSusanooItemsSync(player.getEntityId(), playercap.getSusanooMainHand(), playercap.getSusanooOffHand(), true)); //playercap.getSusanooMainHand(), playercap.getSusanooOffHand()
            //NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new PacketTruthSeekingOrbsSync(player.getEntityId(), playercap.getTruthSeekingOrbAmount())); //playercap.getSusanooMainHand(), playercap.getSusanooOffHand()
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        PlayerEntity player = event.getPlayer();
        LazyOptional<IPlayerHandler> manaCap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler mana = manaCap.orElse(new PlayerCapability());
        LazyOptional<IPlayerHandler> oldManaCap = event.getOriginal().getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler oldMana = oldManaCap.orElse(new PlayerCapability());

        mana.setSpellModeToggle(oldMana.returnSpellModeToggle());

        mana.setKeybind(1, oldMana.returnKeybind(1));
        mana.setKeybind(2, oldMana.returnKeybind(2));
        mana.setKeybind(3, oldMana.returnKeybind(3));
        mana.setKeybind(4, oldMana.returnKeybind(4));
        mana.setKeybind(5, oldMana.returnKeybind(5));
        mana.setKeybind(6, oldMana.returnKeybind(6));
        mana.setKeybind(7, oldMana.returnKeybind(7));
        mana.setKeybind(8, oldMana.returnKeybind(8));
        mana.setKeybind(9, oldMana.returnKeybind(9));

        mana.setGrimoireTexture(oldMana.getGrimoireTexture());

        mana.setKeybindCD(1, oldMana.returnKeybindCD(1));
        mana.setKeybindCD(2, oldMana.returnKeybindCD(2));
        mana.setKeybindCD(3, oldMana.returnKeybindCD(3));
        mana.setKeybindCD(4, oldMana.returnKeybindCD(4));
        mana.setKeybindCD(5, oldMana.returnKeybindCD(5));
        mana.setKeybindCD(6, oldMana.returnKeybindCD(6));
        mana.setKeybindCD(7, oldMana.returnKeybindCD(7));
        mana.setKeybindCD(8, oldMana.returnKeybindCD(8));
        mana.setKeybindCD(9, oldMana.returnKeybindCD(9));

        mana.setToggleSpellMessage(oldMana.returnToggleSpellMessage());

        for (BCMSpell spell : BCMRegistry.SPELLS.getValues()) {
            mana.setSpellBoolean(spell, oldMana.hasSpellBoolean(spell));
        }

        mana.setMana(oldMana.returnMana());
        mana.setMaxMana(oldMana.returnMaxMana());
        mana.setColourMana(oldMana.returnColourMana());
        mana.setJoinWorld(oldMana.joinWorld());
        mana.setRegenMana(oldMana.returnRegenMana());
        mana.setRace(oldMana.returnRace());
        mana.setPlayerBodyMode(oldMana.returnPlayerMode());
        mana.setMagicAttribute(oldMana.returnMagicAttribute());
        mana.setMagicLevel(oldMana.returnMagicLevel());
        mana.setMagicExp(oldMana.returnMagicExp());
        mana.setHasGrimoire(oldMana.returnHasGrimoire());

        mana.setManaBoolean(oldMana.hasManaBoolean());
    }
}
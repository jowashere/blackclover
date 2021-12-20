package com.github.jowashere.blackclover.events;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.api.internal.BCMAttribute;
import com.github.jowashere.blackclover.api.internal.BCMRace;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.common.spells.EffectSpells;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.init.EffectInit;
import com.github.jowashere.blackclover.init.RaceInit;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.*;
import com.github.jowashere.blackclover.networking.packets.mana.PacketColourManaSync;
import com.github.jowashere.blackclover.networking.packets.mana.PacketManaSync;
import com.github.jowashere.blackclover.networking.packets.mana.PacketMaxManaSync;
import com.github.jowashere.blackclover.networking.packets.mana.PacketRegenManaSync;
import com.github.jowashere.blackclover.networking.packets.server.SSyncManaPacket;
import com.github.jowashere.blackclover.networking.packets.settings.PacketKeybindSet;
import com.github.jowashere.blackclover.networking.packets.settings.PacketSetGrimoireTexture;
import com.github.jowashere.blackclover.networking.packets.spells.PacketIntSpellNBTSync;
import com.github.jowashere.blackclover.networking.packets.spells.PacketSetSpellBoolean;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import com.github.jowashere.blackclover.util.helpers.SpellHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerEvents {

    public static void regenerateMana(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        player.getPersistentData().putInt("regentick", player.getPersistentData().getInt("regentick") + 1);
        LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
        if (player.isAlive() && playercap.HasManaBoolean()) {

            if (player.getPersistentData().getInt("regentick") >= 10 && playercap.returnMaxMana() != 0) {
                float mana = playercap.returnMana();
                float maxMana = playercap.returnMaxMana();
                float regenMana = playercap.returnRegenMana() / 6;
                playercap.addMana(regenMana);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketManaSync(mana));
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketRegenManaSync(playercap.returnRegenMana(), true));
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketMaxManaSync(maxMana, true));
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new SSyncManaPacket(player.getId(), mana, maxMana, regenMana));

                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketColourManaSync(playercap.ReturnMagicLevel()));
                player.getPersistentData().putInt("regentick", 0);
            }
        }
    }

    public static void cooldowns(TickEvent.PlayerTickEvent event) {

        if(!event.player.level.isClientSide){
            PlayerEntity player = event.player;
            if (player != null) {
                LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());

                for (AbstractSpell spells : BCMRegistry.SPELLS.getValues()) {
                    if (spells.getAttribute() == playercap.ReturnMagicAttribute()) {
                        if(playercap.hasSpellBoolean(spells)){
                            String cdName = spells.getCorrelatedPlugin().getPluginId() + "_" + spells.getName() + "_cd";
                            int cd = player.getPersistentData().getInt(cdName);
                            int newCD = cd - 1;
                            if(cd > 0){
                                player.getPersistentData().putInt(cdName, newCD);
                                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(()-> (ServerPlayerEntity) player), new PacketIntSpellNBTSync(player.getId(), cdName, newCD));
                            }
                        }
                    }
                }
            }
        }
    }

    public static void resetCooldowns(PlayerEntity player) {

        if (player != null) {
            LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());

            for (AbstractSpell spells : BCMRegistry.SPELLS.getValues()) {
                if (spells.getAttribute() == playercap.ReturnMagicAttribute()) {
                    if(playercap.hasSpellBoolean(spells)){
                        String cdName = spells.getCorrelatedPlugin().getPluginId() + "_" + spells.getName() + "_cd";
                        player.getPersistentData().putInt(cdName, 0);
                        NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(()-> (ServerPlayerEntity) player), new PacketIntSpellNBTSync(player.getId(), cdName, 0));
                    }
                }
            }
        }

    }

    public static void setPlayerSpells(TickEvent.PlayerTickEvent event) {

        PlayerEntity player = event.player;
        if (player.isAlive()) {

            LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());

            /*if (playercap.ReturnMagicAttribute().getSpellAdder() != null) {
                playercap.ReturnMagicAttribute().getSpellAdder().add(event.player);
            }*/

            for (AbstractSpell spell : BCMRegistry.SPELLS.getValues()) {
                if(spell.getAttribute().equals(playercap.ReturnMagicAttribute())){
                    if(!(spell.getUnlockLevel() <= 0)){
                        if(spell.getUnlockLevel() <= playercap.getMagicLevel() && (spell.isSkillSpell() || playercap.returnHasGrimoire())){
                            if(!playercap.hasSpellBoolean(spell)){
                                playercap.setSpellBoolean(spell, true);
                                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketSetSpellBoolean(spell.getName(), true,true));
                            }
                        }

                        if(spell.getUnlockLevel() > playercap.getMagicLevel() || (!spell.isSkillSpell() && !playercap.returnHasGrimoire())){
                            if(playercap.hasSpellBoolean(spell)){
                                playercap.setSpellBoolean(spell, false);
                                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketSetSpellBoolean(spell.getName(), false,true));
                            }
                        }
                    }
                }
            }

                for (int i = 0; i < 9; i++) {
                AbstractSpell spell = null;
                spell = SpellHelper.getSpellFromString(playercap.returnKeybind(i + 1));

                if (spell != null && spell.getAttribute() != playercap.ReturnMagicAttribute()) {
                    playercap.setKeybind(i + 1, "");
                    NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketKeybindSet(i + 1, "", true));
                }
            }
        }
    }


    public static void playerJoinedWorld(EntityJoinWorldEvent event) {
        PlayerEntity player = (PlayerEntity) event.getEntity();
        LazyOptional<IPlayerHandler> playerc = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerc.orElse(new PlayerCapability());

        player_cap.setSpellModeToggle(false);
        NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketSpellModeToggle(true, false, player.getId()));

        if (!player_cap.JoinWorld()) {

            player_cap.setHasGrimoire(false);
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketSetGrimoire(false, true, player.getId()));

            player_cap.setJoinWorld(true);
            {
                ArrayList<BCMRace> races = new ArrayList<>();
                for (BCMRace race : BCMRegistry.RACES.getValues()) {
                    if (race != RaceInit.NULL) {
                        races.add(race);
                    }
                }

                double totalWeight = 0.0D;
                for (BCMRace race : races) {
                    totalWeight += race.getWeight();
                }

                int randomIndex = -1;
                double random = Math.random() * totalWeight;
                for (int i = 0; i < races.size(); ++i) {
                    random -= races.get(i).getWeight();
                    if (random <= 0.0D) {
                        randomIndex = i;
                        break;
                    }
                }

                BCMRace randomRace = races.get(randomIndex);

                if (player_cap.returnRace() == RaceInit.NULL) {
                    player_cap.setRace(randomRace);
                    NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketRaceSync(randomRace.getString(), true));
                    player.displayClientMessage(new StringTextComponent(player_cap.returnRace().getRaceMessage()), false);
                }
            }
            {
                ArrayList<BCMAttribute> attributes = new ArrayList<>();
                for (BCMAttribute attribute : BCMRegistry.ATTRIBUTES.getValues()) {
                    if (attribute != AttributeInit.NULL) {
                        attributes.add(attribute);
                    }
                }

                double totalWeight = 0.0D;
                for (BCMAttribute attribute : attributes) {
                    totalWeight += attribute.getWeight();
                }

                int randomIndex = -1;
                double random = Math.random() * totalWeight;
                for (int i = 0; i < attributes.size(); ++i) {
                    random -= attributes.get(i).getWeight();
                    if (random <= 0.0D) {
                        randomIndex = i;
                        break;
                    }
                }

                BCMAttribute randomAttribute = attributes.get(randomIndex);

                if (player_cap.ReturnMagicAttribute() == AttributeInit.NULL) {
                    player_cap.setMagicAttribute(randomAttribute);
                    NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketAttributeSync(randomAttribute.getString(), true));
                    player.displayClientMessage(new StringTextComponent(player_cap.ReturnMagicAttribute().getAttributeMessage()), false);
                }
            }

            float xpNeeded = BCMHelper.CalculateExp(1);

            player_cap.setMagicExp(xpNeeded);
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketMagicExpSync(xpNeeded, player.getId()));
            BCMHelper.recaculateMagicLevel(player);

            if(player_cap.ReturnMagicAttribute() != AttributeInit.ANTI_MAGIC){
                player_cap.setManaBoolean(true);
                player_cap.setMana(player_cap.returnRace().getStartingMana());
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketManaSync(player_cap.returnMana()));
            }else {
                player_cap.setManaBoolean(false);
                player_cap.setMana(0);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketManaSync(0));
            }

        }
    }

    public static void manaRuns(TickEvent.PlayerTickEvent event) {
        LazyOptional<IPlayerHandler> playerCapability = event.player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerCapability.orElse(new PlayerCapability());

        if (!event.player.hasEffect(EffectInit.MAGIC_LEVEL.get()))
            event.player.addEffect(new EffectInstance(EffectInit.MAGIC_LEVEL.get(), (int) Float.POSITIVE_INFINITY, player_cap.ReturnMagicLevel(), false, false, false));

        int magicLevel = player_cap.ReturnMagicLevel();
        float manaMultiplier = player_cap.returnRace().getManaMultiplier();

        if(player_cap.HasManaBoolean()){
            int maxMana = (int) (player_cap.returnRace().getStartingMana() + ((((player_cap.returnRace().getStartingMana() / 10) * magicLevel) - 15)) * manaMultiplier);

            player_cap.setMaxMana(maxMana);
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.player), new PacketMaxManaSync(maxMana, true));

            int regenMana = maxMana / 20;
            player_cap.setRegenMana(regenMana);
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.player), new PacketRegenManaSync(regenMana, true));
        } else {
            player_cap.setMaxMana(0);
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.player), new PacketMaxManaSync(0, true));

            player_cap.setMana(0);
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.player), new PacketManaSync(0));

            player_cap.setRegenMana(0);
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.player), new PacketRegenManaSync(0, true));

        }

        if (player_cap.ReturnMagicAttribute().getGrimoireTextures() != null && player_cap.ReturnMagicAttribute().getGrimoireTextures().size() > 0) {

            List<String> textures = player_cap.ReturnMagicAttribute().getGrimoireTextures();

            if (Arrays.stream(textures.toArray(new String[0])).noneMatch(
                    (element) -> element.equals(player_cap.getGrimoireTexture()))) {
                String randomGrimoire = player_cap.ReturnMagicAttribute().getGrimoireTextures().get((int) Math.random() * player_cap.ReturnMagicAttribute().getGrimoireTextures().size());
                player_cap.SetGrimoireTexture(randomGrimoire);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.player), new PacketSetGrimoireTexture(randomGrimoire, true, event.player.getId()));
            }
        }

        if(player_cap.HasManaBoolean()) {

            ItemStack stackMain = event.player.getItemInHand(Hand.MAIN_HAND);
            ItemStack stackOff = event.player.getItemInHand(Hand.OFF_HAND);

            if(stackMain.getOrCreateTag().getBoolean("antimagic") || stackOff.getOrCreateTag().getBoolean("antimagic")){
                player_cap.addMana(-3);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.player), new PacketManaSync(player_cap.returnMana()));
            }

            if(player_cap.returnMana() <= 0 && !event.player.isCreative()){
                event.player.hurt(DamageSource.OUT_OF_WORLD, 3);
            }
        }
    }


    public static void magicBuffs(TickEvent.PlayerTickEvent event) {
        LazyOptional<IPlayerHandler> playerCapability = event.player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerCapability.orElse(new PlayerCapability());

        NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.player), new PacketRaceSync(player_cap.returnRace().getString(), true));

        if(!player_cap.HasManaBoolean()){
            if (player_cap.ReturnManaSkinToggled()) {
                player_cap.setManaSkinToggled(false);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.player), new PacketToggleInfusionBoolean(1, true, false, event.player.getId()));
            }
            if (player_cap.returnReinforcementToggled()) {
                player_cap.setReinforcementToggled(false);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.player), new PacketToggleInfusionBoolean(2, true, false, event.player.getId()));
            }
        }
    }


    public static void specialSpellNbt(TickEvent.PlayerTickEvent event){

        LazyOptional<IPlayerHandler> playerCapability = event.player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerCapability.orElse(new PlayerCapability());

        if(player_cap.ReturnMagicAttribute().equals(AttributeInit.LIGHTNING)){
            if(event.player.getPersistentData().getInt("thunder_fiend_dmg") > 0){
                EffectSpells.ThunderFiendDamage(event.player);
                int newAmount = event.player.getPersistentData().getInt("thunder_fiend_dmg") - 1;
                event.player.getPersistentData().putInt("thunder_fiend_dmg", newAmount);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(()-> (ServerPlayerEntity) event.player), new PacketIntSpellNBTSync(event.player.getId(), "thunder_fiend_dmg", newAmount));
            }
        }

        if(player_cap.ReturnMagicAttribute().equals(AttributeInit.ANTI_MAGIC)){
            if(event.player.getPersistentData().getInt("bull_thrust_dmg") > 0){
                EffectSpells.BullThrustDamage(event.player);
                int newAmount = event.player.getPersistentData().getInt("bull_thrust_dmg") - 1;
                event.player.getPersistentData().putInt("bull_thrust_dmg", newAmount);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(()-> (ServerPlayerEntity) event.player), new PacketIntSpellNBTSync(event.player.getId(), "bull_thrust_dmg", newAmount));
            }

            if(event.player.getPersistentData().getInt("black_meteorite_dmg") > 0){
                EffectSpells.BlackMeteoriteDamage(event.player);
                int newAmount = event.player.getPersistentData().getInt("black_meteorite_dmg") - 1;
                event.player.getPersistentData().putInt("black_meteorite_dmg", newAmount);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(()-> (ServerPlayerEntity) event.player), new PacketIntSpellNBTSync(event.player.getId(), "black_meteorite_dmg", newAmount));
                if(event.player.getPersistentData().getInt("black_meteorite_dmg") == 1){
                    event.player.stopFallFlying();
                }
            }

            if(event.player.getPersistentData().getInt("black_mode_fatigue") > 0){
                event.player.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, 2, 3));
                event.player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 2, 3));
                event.player.addEffect(new EffectInstance(Effects.WEAKNESS, 2, 3));
                event.player.addEffect(new EffectInstance(Effects.HUNGER, 2, 3));
                int newAmount = event.player.getPersistentData().getInt("black_mode_fatigue") - 1;
                event.player.getPersistentData().putInt("black_mode_fatigue", newAmount);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(()-> (ServerPlayerEntity) event.player), new PacketIntSpellNBTSync(event.player.getId(), "bull_thrust_dmg", newAmount));
            }

        }

    }

    public static void toggleManaSkin(PlayerEntity player){

        LazyOptional<IPlayerHandler> player_cap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler playerc = player_cap.orElse(new PlayerCapability());

        if(player.level.isClientSide){
            if(playerc.HasManaBoolean()){
                if (!playerc.ReturnManaSkinToggled()) {
                    playerc.setManaSkinToggled(true);
                    NetworkLoader.INSTANCE.sendToServer(new PacketToggleInfusionBoolean(1, false, true, player.getId()));
                } else {
                    playerc.setManaSkinToggled(false);
                    NetworkLoader.INSTANCE.sendToServer(new PacketToggleInfusionBoolean(1, false, false, player.getId()));
                }
            }
        }
    }

    public static void toggleReinforcement(PlayerEntity player){

        LazyOptional<IPlayerHandler> player_cap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler playerc = player_cap.orElse(new PlayerCapability());

        if(player.level.isClientSide){
            if(playerc.HasManaBoolean()){
                if (!playerc.returnReinforcementToggled()) {
                    playerc.setReinforcementToggled(true);
                    NetworkLoader.INSTANCE.sendToServer(new PacketToggleInfusionBoolean(2, false, true, player.getId()));
                } else {
                    playerc.setReinforcementToggled(false);
                    NetworkLoader.INSTANCE.sendToServer(new PacketToggleInfusionBoolean(2, false, false, player.getId()));
                }
            }
        }
    }
}
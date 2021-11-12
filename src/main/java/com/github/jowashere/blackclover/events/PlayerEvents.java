package com.github.jowashere.blackclover.events;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.BCMAttribute;
import com.github.jowashere.blackclover.api.internal.BCMRace;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.common.spells.EffectSpells;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.init.EffectInit;
import com.github.jowashere.blackclover.init.RaceInit;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.*;
import com.github.jowashere.blackclover.networking.packets.mana.*;
import com.github.jowashere.blackclover.networking.packets.server.SSyncManaPacket;
import com.github.jowashere.blackclover.networking.packets.settings.PacketKeybindSet;
import com.github.jowashere.blackclover.networking.packets.settings.PacketSetGrimoireTexture;
import com.github.jowashere.blackclover.networking.packets.spells.PacketKeybindCD;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.stats.Stats;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PlayerEvents {

    public static void regenerateMana(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        player.getPersistentData().putInt("regentick", player.getPersistentData().getInt("regentick") + 1);
        LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());
        if (player.isAlive() && playercap.hasManaBoolean()) {

            if (player.getPersistentData().getInt("regentick") >= 10 && playercap.returnMaxMana() != 0) {
                float mana = playercap.returnMana();
                float maxMana = playercap.returnMaxMana();
                float regenMana = playercap.returnRegenMana() / 6;
                playercap.addMana(regenMana);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketManaSync(mana));
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketRegenManaSync(playercap.returnRegenMana(), true));
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketMaxManaSync(maxMana, true));
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new SSyncManaPacket(player.getId(), mana, maxMana, regenMana));

                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketColourManaSync(playercap.returnMagicLevel()));
                player.getPersistentData().putInt("regentick", 0);
            }
        }
    }

    public static void cooldowns(TickEvent.PlayerTickEvent event){
        PlayerEntity player = event.player;

        LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());

        if (player.isAlive()) {
            if (playercap.returnKeybind1CD() > 0) {
                playercap.setKeybind1CD(playercap.returnKeybind1CD() - 1);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketKeybindCD(1, playercap.returnKeybind1CD(), player.getId()));
            }
            if (playercap.returnKeybind2CD() > 0) {
                playercap.setKeybind2CD(playercap.returnKeybind2CD() - 1);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketKeybindCD(2, playercap.returnKeybind2CD(), player.getId()));
            }
            if (playercap.returnKeybind3CD() > 0) {
                playercap.setKeybind3CD(playercap.returnKeybind3CD() - 1);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketKeybindCD(3, playercap.returnKeybind3CD(), player.getId()));
            }
            if (playercap.returnKeybind4CD() > 0) {
                playercap.setKeybind4CD(playercap.returnKeybind4CD() - 1);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketKeybindCD(4, playercap.returnKeybind4CD(), player.getId()));
            }
            if (playercap.returnKeybind5CD() > 0) {
                playercap.setKeybind5CD(playercap.returnKeybind5CD() - 1);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketKeybindCD(5, playercap.returnKeybind5CD(), player.getId()));
            }
            if (playercap.returnKeybind6CD() > 0) {
                playercap.setKeybind6CD(playercap.returnKeybind6CD() - 1);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketKeybindCD(6, playercap.returnKeybind6CD(), player.getId()));
            }
            if (playercap.returnKeybind7CD() > 0) {
                playercap.setKeybind7CD(playercap.returnKeybind7CD() - 1);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketKeybindCD(7, playercap.returnKeybind7CD(), player.getId()));
            }
            if (playercap.returnKeybind8CD() > 0) {
                playercap.setKeybind8CD(playercap.returnKeybind8CD() - 1);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketKeybindCD(8, playercap.returnKeybind8CD(), player.getId()));
            }
            if (playercap.returnKeybind9CD() > 0) {
                playercap.setKeybind9CD(playercap.returnKeybind9CD() - 1);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketKeybindCD(9, playercap.returnKeybind9CD(), player.getId()));
            }
        }
    }

    public static void setPlayerSpells(TickEvent.PlayerTickEvent event) {

        PlayerEntity player = event.player;
        if (player.isAlive()) {

            LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());

            if(playercap.returnMagicAttribute().getSpellAdder() != null)
                playercap.returnMagicAttribute().getSpellAdder().add(event.player);

        }
    }


    public static void PlayerJoinedWorld(EntityJoinWorldEvent event) {
        PlayerEntity player = (PlayerEntity) event.getEntity();
        LazyOptional<IPlayerHandler> playerc = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerc.orElse(new PlayerCapability());

        player.getPersistentData().putInt("randomakatsukitimerint", new Random().nextInt(24000));
        //player.sendMessage(new StringTextComponent("You are interested in pests, an Aburame?"));
        //player.sendMessage(new StringTextComponent("You gloat over food, an Akimichi?"));

        player_cap.setSpellModeToggle(false);
        NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketSpellModeToggle(true, false, player.getId()));

        player_cap.setHasGrimoire(true);
        NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketSetGrimoire(true, true, player.getId()));


        if (!player_cap.joinWorld()) {

            player_cap.setKeybind1CD(0);
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketKeybindCD(1, 0, player.getId()));
            player_cap.setKeybind2CD(0);
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketKeybindCD(2, 0, player.getId()));
            player_cap.setKeybind3CD(0);
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketKeybindCD(3, 0, player.getId()));
            player_cap.setKeybind4CD(0);
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketKeybindCD(4, 0, player.getId()));
            player_cap.setKeybind5CD(0);
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketKeybindCD(5, 0, player.getId()));
            player_cap.setKeybind6CD(0);
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketKeybindCD(6, 0, player.getId()));
            player_cap.setKeybind7CD(0);
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketKeybindCD(7, 0, player.getId()));
            player_cap.setKeybind8CD(0);
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketKeybindCD(8, 0, player.getId()));
            player_cap.setKeybind9CD(0);
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketKeybindCD(9, 0, player.getId()));

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
                for (BCMAttribute attribute : attributes)
                {
                    totalWeight += attribute.getWeight();
                }

                int randomIndex = -1;
                double random = Math.random() * totalWeight;
                for (int i = 0; i < attributes.size(); ++i)
                {
                    random -= attributes.get(i).getWeight();
                    if (random <= 0.0D)
                    {
                        randomIndex = i;
                        break;
                    }
                }

                BCMAttribute randomAttribute = attributes.get(randomIndex);

                if(player_cap.returnMagicAttribute() == AttributeInit.NULL){
                    player_cap.setMagicAttribute(randomAttribute);
                    NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketAttributeSync(randomAttribute.getString(), true));
                    player.displayClientMessage(new StringTextComponent(player_cap.returnMagicAttribute().getAttributeMessage()), false);
                }
            }

            player_cap.setMana(player_cap.returnRace().getStartingMana());
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketManaSync(player_cap.returnMana()));
            player_cap.setMagicLevel(1);
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketMagicLevel(1, true));
            player_cap.setManaBoolean(true);

        }
    }

    public static void manaRuns(TickEvent.PlayerTickEvent event){
        LazyOptional<IPlayerHandler> playerCapability = event.player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerCapability.orElse(new PlayerCapability());

        if(!event.player.hasEffect(EffectInit.MAGIC_LEVEL.get()))
            event.player.addEffect(new EffectInstance(EffectInit.MAGIC_LEVEL.get(), (int) Float.POSITIVE_INFINITY, player_cap.returnMagicLevel(), false,false, false));

        int magicLevel = player_cap.returnMagicLevel();

        int maxMana = (int) (player_cap.returnRace().getStartingMana() + (((player_cap.returnRace().getStartingMana()/10) * magicLevel) - 15));

        player_cap.setMaxMana(maxMana);
        NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.player), new PacketMaxManaSync(maxMana, true));

        int regenMana = maxMana / 20;
        player_cap.setRegenMana(regenMana);
        NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.player), new PacketRegenManaSync(regenMana, true));

        if(player_cap.returnMagicAttribute().getGrimoireTextures() != null && player_cap.returnMagicAttribute().getGrimoireTextures().size() > 0){
            if(Arrays.stream(player_cap.returnMagicAttribute().getGrimoireTextures().toArray(new String[0])).noneMatch(
                    (element) -> element == player_cap.getGrimoireTexture())) {
                String randomGrimoire = player_cap.returnMagicAttribute().getGrimoireTextures().get((int) Math.random() * player_cap.returnMagicAttribute().getGrimoireTextures().size());
                player_cap.setGrimoireTexture(randomGrimoire);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.player), new PacketSetGrimoireTexture(randomGrimoire, true, event.player.getId()));
            }
        }

    }

    public static void magicBuffs(TickEvent.PlayerTickEvent event) {
        LazyOptional<IPlayerHandler> playerCapability = event.player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerCapability.orElse(new PlayerCapability());

        NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.player), new PacketRaceSync(player_cap.returnRace().getString(), true));

        int magicLevel = player_cap.returnMagicLevel();

        if (player_cap.returnManaSkinToggled()) {
            EffectSpells.ManaSkin(event.player, 3, magicLevel, (int) (magicLevel * 0.85));
        }
        if (player_cap.returnReinforcementToggled()) {
            EffectSpells.Reinforcement(event.player, 4, magicLevel, (int) (magicLevel * 0.85));
        }
    }

    public static float manaSkinReduction(LivingHurtEvent event) {

        LivingEntity entity = event.getEntityLiving();
        DamageSource damageSrc = event.getSource();
        float damageAmount = event.getAmount();

        if(entity instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) entity;

            LazyOptional<IPlayerHandler> playerCapability = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerCapability.orElse(new PlayerCapability());

            if (damageSrc.isBypassMagic()) {
                return damageAmount;
            } else {
                if (player_cap.returnManaSkinToggled() && damageSrc != DamageSource.OUT_OF_WORLD) {
                    float i = (float) (player_cap.returnMagicLevel() * 1.05);
                    float f = damageAmount / (float)i;
                    float f1 = damageAmount;
                    damageAmount = Math.max(f / 2.0F, 0.0F);
                    float f2 = f1 - damageAmount;
                    if (f2 > 0.0F && f2 < 3.4028235E37F) {
                        if (entity instanceof ServerPlayerEntity) {
                            ((ServerPlayerEntity)entity).awardStat(Stats.DAMAGE_RESISTED, Math.round(f2 * 10.0F));
                        } else if (damageSrc.getEntity() instanceof ServerPlayerEntity) {
                            ((ServerPlayerEntity)damageSrc.getEntity()).awardStat(Stats.DAMAGE_DEALT_RESISTED, Math.round(f2 * 10.0F));
                        }
                    }
                }

                if (damageAmount <= 0.0F) {
                    return 0.0F;
                } else {
                    int k = EnchantmentHelper.getDamageProtection(entity.getArmorSlots(), damageSrc);
                    if (k > 0) {
                        damageAmount = CombatRules.getDamageAfterMagicAbsorb(damageAmount, (float)k);
                    }
                    return damageAmount;
                }
            }
        }
        return damageAmount;
    }
}

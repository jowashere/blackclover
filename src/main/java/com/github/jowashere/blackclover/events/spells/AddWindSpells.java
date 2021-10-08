package com.github.jowashere.blackclover.events.spells;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.spells.PacketSetSpellBoolean;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

public class AddWindSpells extends AbstractAddSpells{

    @Override
    public void add(PlayerEntity player) {
        if (player.isAlive()) {

            LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());

            for (BCMSpell spell : BCMRegistry.SPELLS.getValues()) {

                if(spell.getName().equals("wind_blade")){
                    if(!playercap.hasSpellBoolean(spell)){
                        playercap.setSpellBoolean(spell, true);
                        NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketSetSpellBoolean(spell.getName(), true,true));
                    }
                }

                if(spell.getName().equals("wind_crescent")){
                    if(!playercap.hasSpellBoolean(spell)){
                        playercap.setSpellBoolean(spell, true);
                        NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketSetSpellBoolean(spell.getName(), true,true));
                    }
                }

                if(spell.getName().equals("wind_blade_shower")){
                    if(!playercap.hasSpellBoolean(spell)){
                        playercap.setSpellBoolean(spell, true);
                        NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketSetSpellBoolean(spell.getName(), true,true));
                    }
                }

                if(spell.getName().equals("towering_tornado")){
                    if(!playercap.hasSpellBoolean(spell)){
                        playercap.setSpellBoolean(spell, true);
                        NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketSetSpellBoolean(spell.getName(), true,true));
                    }
                }

                if(spell.getName().equals("wind_hawk")){
                    if(!playercap.hasSpellBoolean(spell)){
                        playercap.setSpellBoolean(spell, true);
                        NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketSetSpellBoolean(spell.getName(), true,true));
                    }
                }
            }
        }
    }

}

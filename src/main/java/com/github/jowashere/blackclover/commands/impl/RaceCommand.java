package com.github.jowashere.blackclover.commands.impl;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.BCMRace;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.PacketRaceSync;
import com.github.jowashere.blackclover.util.helpers.RaceHelper;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public class RaceCommand {

    private static final SuggestionProvider<CommandSource> SUGGEST_RACE = (source, builder) -> {
        List<String> raceNames = new ArrayList<>();
        for (BCMRace race : BCMRegistry.RACES.getValues()) {
            raceNames.add(race.getString());
        }
        return ISuggestionProvider.suggest(raceNames.stream(), builder);
    };

    private static final SuggestionProvider<CommandSource> SUGGEST_SET = (source, builder) -> {
        List<String> suggestions = new ArrayList<>();
        suggestions.add("set");
        return ISuggestionProvider.suggest(suggestions.stream(), builder);
    };

    /*private static final SuggestionProvider<CommandSource> SUGGEST_PRIM_SEC = (source, builder) -> {
        List<String> suggestions = new ArrayList<>();
        suggestions.add("primary");
        suggestions.add("secondary");
        return ISuggestionProvider.suggest(suggestions.stream(), builder);
    };*/

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("race").requires((commandSource) -> commandSource.hasPermission(3)).then(Commands.argument("target", EntityArgument.player()).then(Commands.argument("set", StringArgumentType.string()).suggests(SUGGEST_SET)/*.then(Commands.argument("primary/secondary", StringArgumentType.string()).suggests(SUGGEST_PRIM_SEC)*/.then(Commands.argument("race", StringArgumentType.string()).suggests(SUGGEST_RACE).executes((context) -> {
            if (StringArgumentType.getString(context, "set").equalsIgnoreCase("set")) {
                /*boolean secondary = false;
                if (StringArgumentType.getString(context, "primary/secondary").equalsIgnoreCase("secondary")) {
                    secondary = true;
                }*/
                return setRace(context.getSource(), EntityArgument.getPlayer(context, "target"), StringArgumentType.getString(context, "race"));
            }
            return 0;
        })))));
    }

    private static int setRace(CommandSource source, PlayerEntity player, /*boolean secondary,*/ String race) {
        IPlayerHandler playercap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));

        if(RaceHelper.getRaceFromString(race) == (null)){
            source.sendFailure(new TranslationTextComponent("commands." + Main.MODID + ".race.set.norace"));
            return 0;
        }

        playercap.setRace(RaceHelper.getRaceFromString(race));
        NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketRaceSync(race, true));

        source.sendSuccess(new TranslationTextComponent("commands." + Main.MODID + ".race.set", player.getDisplayName(), race), true);
        return 1;
    }

}

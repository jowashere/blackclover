package com.github.jowashere.blackclover.commands.impl;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.PacketSetGrimoire;
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

public class GrimoireCommand {

    private static final SuggestionProvider<CommandSource> SUGGEST_GIVE_OR_TAKE = (source, builder) -> {
        List<String> suggestions = new ArrayList<>();
        suggestions.add("take");
        suggestions.add("give");
        return ISuggestionProvider.suggest(suggestions.stream(), builder);
    };

    /*private static final SuggestionProvider<CommandSource> SUGGEST_PRIM_SEC = (source, builder) -> {
        List<String> suggestions = new ArrayList<>();
        suggestions.add("primary");
        suggestions.add("secondary");
        return ISuggestionProvider.suggest(suggestions.stream(), builder);
    };*/

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("grimoire").requires((commandSource) -> commandSource.hasPermission(3)).then(Commands.argument("target", EntityArgument.player()).then(Commands.argument("give|take", StringArgumentType.string()).suggests(SUGGEST_GIVE_OR_TAKE).executes((context) -> {
            if (StringArgumentType.getString(context, "give|take").equalsIgnoreCase("give")) {
                return setGrimoire(context.getSource(), EntityArgument.getPlayer(context, "target"), true);
            } else if (StringArgumentType.getString(context, "give|take").equalsIgnoreCase("take")) {
                return setGrimoire(context.getSource(), EntityArgument.getPlayer(context, "target"), false);
            }
            return 0;
        }))));
    }

    private static int setGrimoire(CommandSource source, PlayerEntity player, Boolean shouldHave) {
        IPlayerHandler playercap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));

        playercap.setHasGrimoire(shouldHave);
        NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketSetGrimoire(shouldHave, true, player.getId()));

        if(shouldHave){
            source.sendSuccess(new TranslationTextComponent("commands." + Main.MODID + ".grimoire.give", player.getDisplayName()), true);
        } else {
            source.sendSuccess(new TranslationTextComponent("commands." + Main.MODID + ".grimoire.take", player.getDisplayName()), false);
        }
        return 1;
    }

}

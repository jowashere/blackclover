package com.github.jowashere.blackclover.commands.impl;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.BCMAttribute;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.PacketAttributeSync;
import com.github.jowashere.blackclover.util.helpers.AttributeHelper;
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

public class MagicAttributeCommand {

    private static final SuggestionProvider<CommandSource> SUGGEST_ATTRIBUTE = (source, builder) -> {
        List<String> attributeNames = new ArrayList<>();
        for (BCMAttribute attribute : BCMRegistry.ATTRIBUTES.getValues()) {
            if(!attribute.equals(AttributeInit.NULL)){
                attributeNames.add(attribute.getString());
            }
        }
        return ISuggestionProvider.suggest(attributeNames.stream(), builder);
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
        dispatcher.register(Commands.literal("magicattribute").requires((commandSource) -> commandSource.hasPermission(3)).then(Commands.argument("target", EntityArgument.player()).then(Commands.argument("set", StringArgumentType.string()).suggests(SUGGEST_SET)/*.then(Commands.argument("primary/secondary", StringArgumentType.string()).suggests(SUGGEST_PRIM_SEC)*/.then(Commands.argument("attribute", StringArgumentType.string()).suggests(SUGGEST_ATTRIBUTE).executes((context) -> {
            if (StringArgumentType.getString(context, "set").equalsIgnoreCase("set")) {
                /*boolean secondary = false;
                if (StringArgumentType.getString(context, "primary/secondary").equalsIgnoreCase("secondary")) {
                    secondary = true;
                }*/
                return setAttribute(context.getSource(), EntityArgument.getPlayer(context, "target"), StringArgumentType.getString(context, "attribute"));
            }
            return 0;
        })))));
    }

    private static int setAttribute(CommandSource source, PlayerEntity player, /*boolean secondary,*/ String attribute) {
        IPlayerHandler playercap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));

        if(AttributeHelper.getAttributeFromString(attribute) == (null)){
            source.sendFailure(new TranslationTextComponent("commands." + Main.MODID + ".attribute.set.noattribute"));
            return 0;
        }

        playercap.setMagicAttribute(AttributeHelper.getAttributeFromString(attribute));
        NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketAttributeSync(attribute, true));

        source.sendSuccess(new TranslationTextComponent("commands." + Main.MODID + ".attribute.set", player.getDisplayName(), attribute), true);
        return 1;
    }

}

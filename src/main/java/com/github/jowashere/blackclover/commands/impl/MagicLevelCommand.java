package com.github.jowashere.blackclover.commands.impl;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.PacketMagicExpSync;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
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

public class MagicLevelCommand {

    private static final SuggestionProvider<CommandSource> SUGGEST_LEVEL = (source, builder) -> {
        List<String> suggestions = new ArrayList<>();
        suggestions.add("points");
        suggestions.add("levels");
        return ISuggestionProvider.suggest(suggestions.stream(), builder);
    };

    private static final SuggestionProvider<CommandSource> SUGGEST_SET = (source, builder) -> {
        List<String> suggestions = new ArrayList<>();
        suggestions.add("set");
        suggestions.add("add");
        return ISuggestionProvider.suggest(suggestions.stream(), builder);
    };

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("magiclevel").requires((commandSource) -> commandSource.hasPermission(3)).then(Commands.argument("target", EntityArgument.player()).then(Commands.argument("add|set", StringArgumentType.string()).suggests(SUGGEST_SET).then(Commands.argument("amount", IntegerArgumentType.integer()).then(Commands.argument("levels|points", StringArgumentType.string()).suggests(SUGGEST_LEVEL).executes((context) -> {

            String set = StringArgumentType.getString(context, "add|set");
            String level = StringArgumentType.getString(context, "levels|points");
            int amount = IntegerArgumentType.getInteger(context, "amount");

            LevelOrPoints levelOrPoints;
            AddOrSet addOrSet;

            if(set.equalsIgnoreCase("set")){
                addOrSet = AddOrSet.SET;
            }else if(set.equalsIgnoreCase("add")){
                addOrSet = AddOrSet.ADD;
            }else {
                addOrSet = AddOrSet.NULL;
            }

            if(level.equalsIgnoreCase("levels")){
                levelOrPoints = LevelOrPoints.LEVEL;
            }else if(level.equalsIgnoreCase("points")){
                levelOrPoints = LevelOrPoints.POINTS;
            }else {
                levelOrPoints = LevelOrPoints.NULL;
            }

            return setMagicLevel(context.getSource(), EntityArgument.getPlayer(context, "target"), amount , levelOrPoints, addOrSet);

        }))))));
    }

    private static int setMagicLevel(CommandSource source, PlayerEntity player, int amount, LevelOrPoints levelOrPoints, AddOrSet addOrSet) {
        IPlayerHandler playercap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));

        if(levelOrPoints.equals(LevelOrPoints.NULL) || addOrSet.equals(AddOrSet.NULL)){
            source.sendFailure(new TranslationTextComponent("commands." + Main.MODID + ".magiclevel.invalid"));
            return 0;
        }

        int maxLevel = 100;
        float maxExp = BCMHelper.CalculateExp(maxLevel);

        if(levelOrPoints.equals(LevelOrPoints.LEVEL) && ((addOrSet.equals(AddOrSet.SET) && amount > maxLevel) ||
                addOrSet.equals(AddOrSet.ADD) && amount + playercap.ReturnMagicLevel() > maxLevel)){
            source.sendFailure(new TranslationTextComponent("commands." + Main.MODID + ".magiclevel.toohigh", amount));
            return 0;
        } else if(levelOrPoints.equals(LevelOrPoints.LEVEL) && amount <= 0){
            source.sendFailure(new TranslationTextComponent("commands." + Main.MODID + ".magiclevel.toolow", amount));
            return 0;
        }

        if(levelOrPoints.equals(LevelOrPoints.POINTS) && ((addOrSet.equals(AddOrSet.SET) && amount > maxExp) ||
                addOrSet.equals(AddOrSet.ADD) && amount + playercap.returnMagicExp() > maxExp)){
            source.sendFailure(new TranslationTextComponent("commands." + Main.MODID + ".magiclevel.toohigh", amount));
            return 0;
        } else if(levelOrPoints.equals(LevelOrPoints.POINTS) && (addOrSet.equals(AddOrSet.SET) && amount < 0) || addOrSet.equals(AddOrSet.ADD) && amount <= 0){
            source.sendFailure(new TranslationTextComponent("commands." + Main.MODID + ".magiclevel.toolow", amount));
            return 0;
        }

        if(levelOrPoints.equals(LevelOrPoints.LEVEL)){
            if(addOrSet.equals(AddOrSet.SET)){

                float magicExp = BCMHelper.CalculateExp(amount);

                playercap.setMagicExp(magicExp);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketMagicExpSync(magicExp, player.getId()));
                //NetworkLoader.INSTANCE.sendToServer(new PacketMagicExpSync(magicExp, player.getId()));

                source.sendSuccess(new TranslationTextComponent("commands." + Main.MODID + ".magiclevel.set", player.getDisplayName(), amount), true);
            }else if(addOrSet.equals(AddOrSet.ADD)){

                float expNeeded = BCMHelper.CalculateExp(playercap.ReturnMagicLevel() + amount) - playercap.returnMagicExp();
                float newXP = expNeeded + playercap.returnMagicExp();
                playercap.setMagicExp(newXP);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketMagicExpSync(newXP, player.getId()));
                //NetworkLoader.INSTANCE.sendToServer(new PacketMagicExpSync(newXP, player.getId()));

                source.sendSuccess(new TranslationTextComponent("commands." + Main.MODID + ".magiclevel.add", player.getDisplayName(), amount), true);
            }
        }else if(levelOrPoints.equals(LevelOrPoints.POINTS)){
            if(addOrSet.equals(AddOrSet.SET)){
                playercap.setMagicExp(amount);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketMagicExpSync(amount, player.getId()));
                //NetworkLoader.INSTANCE.sendToServer(new PacketMagicExpSync(amount, player.getId()));

                source.sendSuccess(new TranslationTextComponent("commands." + Main.MODID + ".magicexp.set", player.getDisplayName(), amount), true);
            }else if(addOrSet.equals(AddOrSet.ADD)){

                float newXP = playercap.returnMagicExp() + amount;

                playercap.addMagicExp(amount);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketMagicExpSync(newXP, player.getId()));
                //NetworkLoader.INSTANCE.sendToServer(new PacketMagicExpSync(newXP, player.getId()));

                source.sendSuccess(new TranslationTextComponent("commands." + Main.MODID + ".magicexp.add", player.getDisplayName(), amount), true);
            }
        }

        BCMHelper.recaculateMagicLevel(player);

        return 1;
    }

    private enum LevelOrPoints {
        LEVEL, POINTS, NULL
    }

    private enum AddOrSet {
        ADD, SET, NULL
    }

}

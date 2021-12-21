/*
 * Copyright (c) 2018-2020 C4
 *
 * This file is part of Curios, a mod made for Minecraft.
 *
 * Curios is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Curios is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Curios.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.jowashere.blackclover.common.curios.server.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class CurioArgumentType implements ArgumentType<String> {

  public static Set<String> slotIds = new HashSet<>();

  private static final Collection<String> EXAMPLES = Arrays.asList("ring", "head");
  private static final DynamicCommandExceptionType UNKNOWN_TYPE = new DynamicCommandExceptionType(
      type -> new TranslationTextComponent("argument.curios.type.unknown", type));

  public static CurioArgumentType slot() {
    return new CurioArgumentType();
  }

  public static String getSlot(CommandContext<CommandSource> context, String name) {
    return context.getArgument(name, String.class);
  }

  @Override
  public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context,
                                                            SuggestionsBuilder builder) {
    return ISuggestionProvider.suggest(slotIds, builder);
  }

  @Override
  public Collection<String> getExamples() {
    return EXAMPLES;
  }

  @Override
  public String parse(StringReader reader) throws CommandSyntaxException {
    String s = reader.readUnquotedString();

    if (!slotIds.contains(s)) {
      throw UNKNOWN_TYPE.create(s);
    } else {
      return s;
    }
  }
}
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

package com.github.jowashere.blackclover.common.curios.slottype;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.api.curios.CuriosApi;
import com.github.jowashere.blackclover.api.curios.SlotTypeMessage;
import com.github.jowashere.blackclover.api.curios.SlotTypePreset;
import com.github.jowashere.blackclover.common.curios.CuriosConfig;
import com.github.jowashere.blackclover.common.curios.server.command.CurioArgumentType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.InterModComms.IMCMessage;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SlotTypeManager {

  private static Map<String, SlotType.Builder> imcBuilders = new HashMap<>();
  private static Map<String, SlotType.Builder> configBuilders = new HashMap<>();

  public static void buildImcSlotTypes(Stream<IMCMessage> register,
                                       Stream<IMCMessage> modify) {
    imcBuilders.clear();
    processImc(register, true);
    processImc(modify, false);
  }

  public static void buildConfigSlotTypes() {
    configBuilders.clear();
    List<CuriosConfig.CuriosSettings.CuriosSetting> settings = CuriosConfig.curios;

    if (settings == null) {
      return;
    }

    settings.forEach(setting -> {
      String id = setting.identifier;

      if (id == null || id.isEmpty()) {
        Main.LOGGER.error("Missing identifier in curios config, skipping...");
        return;
      }
      SlotType.Builder builder = imcBuilders.get(id);
      boolean force = setting.override != null ? setting.override : false;

      if (builder == null) {
        builder = new SlotType.Builder(id);
        SlotTypeMessage.Builder preset = SlotTypePreset.findPreset(id)
            .map(SlotTypePreset::getMessageBuilder).orElse(null);

        if (preset != null) {
          SlotTypeMessage msg = preset.build();
          builder.icon(msg.getIcon()).priority(msg.getPriority()).size(msg.getSize())
              .locked(msg.isLocked()).visible(msg.isVisible()).hasCosmetic(msg.hasCosmetic());
        }
      } else {
        builder = new SlotType.Builder(id).copyFrom(builder);
      }
      configBuilders.putIfAbsent(id, builder);

      if (setting.priority != null) {
        builder.priority(setting.priority, force);
      }

      if (setting.icon != null && !setting.icon.isEmpty()) {
        builder.icon(new ResourceLocation(setting.icon));
      }

      if (setting.size != null) {
        builder.size(setting.size, force);
      }

      if (setting.locked != null) {
        builder.locked(setting.locked, force);
      }

      if (setting.visible != null) {
        builder.visible(setting.visible, force);
      }

      if (setting.hasCosmetic != null) {
        builder.hasCosmetic(setting.hasCosmetic, force);
      }
    });
    imcBuilders.forEach((key, builder) -> configBuilders.putIfAbsent(key, builder));
  }

  public static void buildSlotTypes() {
    Map<String, SlotType.Builder> builders = !configBuilders.isEmpty() ? configBuilders : imcBuilders;
    builders.values().forEach(builder -> CuriosApi.getSlotHelper().addSlotType(builder.build()));
    CurioArgumentType.slotIds = CuriosApi.getSlotHelper().getSlotTypeIds();
  }

  private static void processImc(Stream<IMCMessage> messages, boolean create) {
    TreeMap<String, List<SlotTypeMessage>> messageMap = new TreeMap<>();
    List<IMCMessage> messageList = messages.collect(Collectors.toList());

    messageList.forEach(msg -> {
      Object messageObject = msg.getMessageSupplier().get();

      if (messageObject instanceof SlotTypeMessage) {
        messageMap.computeIfAbsent(msg.getSenderModId(), k -> new ArrayList<>())
            .add((SlotTypeMessage) messageObject);
      } else if (messageObject instanceof Iterable) {
        Iterable<?> iterable = (Iterable<?>) messageObject;
        Iterator<?> iter = iterable.iterator();

        if (iter.hasNext()) {
          Object firstChild = iter.next();

          if (firstChild instanceof SlotTypeMessage) {
            messageMap.computeIfAbsent(msg.getSenderModId(), k -> new ArrayList<>())
                .add((SlotTypeMessage) firstChild);

            iter.forEachRemaining(
                (child) -> messageMap.computeIfAbsent(msg.getSenderModId(), k -> new ArrayList<>())
                    .add((SlotTypeMessage) child));
          }
        }
      }
    });

    messageMap.values().forEach(msgList -> msgList.forEach(msg -> {
      String id = msg.getIdentifier();
      SlotType.Builder builder = imcBuilders.get(id);

      if (builder == null && create) {
        builder = new SlotType.Builder(id);
        imcBuilders.put(id, builder);
      }

      if (builder != null) {
        builder.size(msg.getSize()).locked(msg.isLocked()).visible(msg.isVisible())
            .hasCosmetic(msg.hasCosmetic());
        SlotTypeMessage.Builder preset = SlotTypePreset.findPreset(id)
            .map(SlotTypePreset::getMessageBuilder).orElse(null);
        SlotTypeMessage presetMsg = preset != null ? preset.build() : null;

        if (msg.getIcon() == null && presetMsg != null) {
          builder.icon(presetMsg.getIcon());
        } else {
          builder.icon(msg.getIcon());
        }

        if (msg.getPriority() == null && presetMsg != null) {
          builder.priority(presetMsg.getPriority());
        } else {
          builder.priority(msg.getPriority());
        }
      }
    }));
  }
}

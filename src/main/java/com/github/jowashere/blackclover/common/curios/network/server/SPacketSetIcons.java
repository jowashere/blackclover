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

package com.github.jowashere.blackclover.common.curios.network.server;

import com.github.jowashere.blackclover.api.curios.CuriosApi;
import com.github.jowashere.blackclover.common.curios.server.command.CurioArgumentType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class SPacketSetIcons {

  private int entrySize;
  private Map<String, ResourceLocation> map;

  public SPacketSetIcons(Map<String, ResourceLocation> map) {
    this.entrySize = map.size();
    this.map = map;
  }

  public static void encode(SPacketSetIcons msg, PacketBuffer buf) {
    buf.writeInt(msg.entrySize);

    for (Map.Entry<String, ResourceLocation> entry : msg.map.entrySet()) {
      buf.writeUtf(entry.getKey());
      buf.writeUtf(entry.getValue().toString());
    }
  }

  public static SPacketSetIcons decode(PacketBuffer buf) {
    int entrySize = buf.readInt();
    Map<String, ResourceLocation> map = new HashMap<>();

    for (int i = 0; i < entrySize; i++) {
      map.put(buf.readUtf(25), new ResourceLocation(buf.readUtf(100)));
    }
    return new SPacketSetIcons(map);
  }

  public static void handle(SPacketSetIcons msg, Supplier<NetworkEvent.Context> ctx) {
    ctx.get().enqueueWork(() -> {
      ClientWorld world = Minecraft.getInstance().level;
      Set<String> slotIds = new HashSet<>();

      if (world != null) {
        CuriosApi.getIconHelper().clearIcons();

        for (Map.Entry<String, ResourceLocation> entry : msg.map.entrySet()) {
          CuriosApi.getIconHelper().addIcon(entry.getKey(), entry.getValue());
          slotIds.add(entry.getKey());
        }
      }
      CurioArgumentType.slotIds = slotIds;
    });
    ctx.get().setPacketHandled(true);
  }
}

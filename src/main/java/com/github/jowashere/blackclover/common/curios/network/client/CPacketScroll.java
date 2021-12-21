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

package com.github.jowashere.blackclover.common.curios.network.client;

import com.github.jowashere.blackclover.common.curios.inventory.container.CuriosContainer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CPacketScroll {

  private int windowId;
  private int index;

  public CPacketScroll(int windowId, int index) {
    this.windowId = windowId;
    this.index = index;
  }

  public static void encode(CPacketScroll msg, PacketBuffer buf) {
    buf.writeInt(msg.windowId);
    buf.writeInt(msg.index);
  }

  public static CPacketScroll decode(PacketBuffer buf) {
    return new CPacketScroll(buf.readInt(), buf.readInt());
  }

  public static void handle(CPacketScroll msg, Supplier<NetworkEvent.Context> ctx) {
    ctx.get().enqueueWork(() -> {
      ServerPlayerEntity sender = ctx.get().getSender();

      if (sender != null) {
        Container container = sender.containerMenu;

        if (container instanceof CuriosContainer && container.containerId == msg.windowId) {
          ((CuriosContainer) container).scrollToIndex(msg.index);
        }
      }
    });
    ctx.get().setPacketHandled(true);
  }
}

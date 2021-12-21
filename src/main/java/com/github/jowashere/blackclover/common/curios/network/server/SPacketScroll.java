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

import com.github.jowashere.blackclover.client.curios.gui.curios.CuriosScreen;
import com.github.jowashere.blackclover.common.curios.inventory.container.CuriosContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;


import java.util.function.Supplier;

public class SPacketScroll {

  private int windowId;
  private int index;

  public SPacketScroll(int windowId, int index) {
    this.windowId = windowId;
    this.index = index;
  }

  public static void encode(SPacketScroll msg, PacketBuffer buf) {
    buf.writeInt(msg.windowId);
    buf.writeInt(msg.index);
  }

  public static SPacketScroll decode(PacketBuffer buf) {
    return new SPacketScroll(buf.readInt(), buf.readInt());
  }

  public static void handle(SPacketScroll msg, Supplier<NetworkEvent.Context> ctx) {
    ctx.get().enqueueWork(() -> {
      Minecraft mc = Minecraft.getInstance();
      ClientPlayerEntity clientPlayer = mc.player;
      Screen screen = mc.screen;

      if (clientPlayer != null) {
        Container container = clientPlayer.containerMenu;

        if (container instanceof CuriosContainer && container.containerId == msg.windowId) {
          ((CuriosContainer) container).scrollToIndex(msg.index);
        }
      }

      if (screen instanceof CuriosScreen) {
        ((CuriosScreen) screen).updateRenderButtons();
      }
    });
    ctx.get().setPacketHandled(true);
  }
}

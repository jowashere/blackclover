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

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class SPacketGrabbedItem {

  private ItemStack stack;

  public SPacketGrabbedItem(ItemStack stackIn) {
    this.stack = stackIn;
  }

  public static void encode(SPacketGrabbedItem msg, PacketBuffer buf) {
    buf.writeItem(msg.stack);
  }

  public static SPacketGrabbedItem decode(PacketBuffer buf) {
    return new SPacketGrabbedItem(buf.readItem());
  }

  public static void handle(SPacketGrabbedItem msg, Supplier<Context> ctx) {

    ctx.get().enqueueWork(() -> {
      ClientPlayerEntity clientPlayer = Minecraft.getInstance().player;

      if (clientPlayer != null) {
        clientPlayer.inventory.setCarried(msg.stack);
      }
    });
    ctx.get().setPacketHandled(true);
  }
}

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

import com.github.jowashere.blackclover.common.curios.inventory.container.CuriosContainerProvider;
import com.github.jowashere.blackclover.common.curios.network.server.SPacketGrabbedItem;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class CPacketOpenCurios {

  public static void encode(CPacketOpenCurios msg, PacketBuffer buf) {
  }

  public static CPacketOpenCurios decode(PacketBuffer buf) {
    return new CPacketOpenCurios();
  }

  public static void handle(CPacketOpenCurios msg, Supplier<NetworkEvent.Context> ctx) {
    ctx.get().enqueueWork(() -> {
      ServerPlayerEntity sender = ctx.get().getSender();

      if (sender != null) {
        ItemStack stack = sender.inventory.getCarried();
        sender.inventory.setCarried(ItemStack.EMPTY);
        NetworkHooks.openGui(sender, new CuriosContainerProvider());

        if (!stack.isEmpty()) {
          sender.inventory.setCarried(stack);
          NetworkLoader.INSTANCE
              .send(PacketDistributor.PLAYER.with(() -> sender), new SPacketGrabbedItem(stack));
        }
      }
    });
    ctx.get().setPacketHandled(true);
  }
}

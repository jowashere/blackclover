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

import com.github.jowashere.blackclover.api.curios.CuriosApi;
import com.github.jowashere.blackclover.api.curios.type.inventory.IDynamicStackHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;


import java.util.function.Supplier;

public class CPacketDestroy {

  public static void encode(CPacketDestroy msg, PacketBuffer buf) {
  }

  public static CPacketDestroy decode(PacketBuffer buf) {
    return new CPacketDestroy();
  }

  public static void handle(CPacketDestroy msg, Supplier<NetworkEvent.Context> ctx) {
    ctx.get().enqueueWork(() -> {
      ServerPlayerEntity sender = ctx.get().getSender();

      if (sender != null) {
        CuriosApi.getCuriosHelper().getCuriosHandler(sender)
            .ifPresent(handler -> handler.getCurios().values().forEach(stacksHandler -> {
              IDynamicStackHandler stackHandler = stacksHandler.getStacks();
              IDynamicStackHandler cosmeticStackHandler = stacksHandler.getCosmeticStacks();

              for (int i = 0; i < stackHandler.getSlots(); i++) {
                stackHandler.setStackInSlot(i, ItemStack.EMPTY);
                cosmeticStackHandler.setStackInSlot(i, ItemStack.EMPTY);
              }
            }));
      }
    });
    ctx.get().setPacketHandled(true);
  }
}

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
import com.github.jowashere.blackclover.common.curios.network.server.sync.SPacketSyncRender;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class CPacketToggleRender {

  String id;
  int index;

  public CPacketToggleRender(String id, int index) {
    this.id = id;
    this.index = index;
  }

  public static void encode(CPacketToggleRender msg, PacketBuffer buf) {
    buf.writeUtf(msg.id);
    buf.writeInt(msg.index);
  }

  public static CPacketToggleRender decode(PacketBuffer buf) {
    return new CPacketToggleRender(buf.readUtf(100), buf.readInt());
  }

  public static void handle(CPacketToggleRender msg, Supplier<NetworkEvent.Context> ctx) {
    ctx.get().enqueueWork(() -> {
      ServerPlayerEntity sender = ctx.get().getSender();

      if (sender != null) {
        CuriosApi.getCuriosHelper().getCuriosHandler(sender)
            .ifPresent(handler -> handler.getStacksHandler(msg.id).ifPresent(stacksHandler -> {
              NonNullList<Boolean> renderStatuses = stacksHandler.getRenders();

              if (renderStatuses.size() > msg.index) {
                boolean value = !renderStatuses.get(msg.index);
                renderStatuses.set(msg.index, value);
                NetworkLoader.INSTANCE
                    .send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> sender),
                        new SPacketSyncRender(sender.getId(), msg.id, msg.index, value));
              }
            }));
      }
    });
    ctx.get().setPacketHandled(true);
  }
}

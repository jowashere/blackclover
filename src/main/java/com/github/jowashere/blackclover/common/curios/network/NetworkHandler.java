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

package com.github.jowashere.blackclover.common.curios.network;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.api.curios.CuriosApi;
import com.github.jowashere.blackclover.common.curios.network.client.*;
import com.github.jowashere.blackclover.common.curios.network.server.SPacketBreak;
import com.github.jowashere.blackclover.common.curios.network.server.SPacketGrabbedItem;
import com.github.jowashere.blackclover.common.curios.network.server.SPacketScroll;
import com.github.jowashere.blackclover.common.curios.network.server.SPacketSetIcons;
import com.github.jowashere.blackclover.common.curios.network.server.sync.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class NetworkHandler {

  private static final String PTC_VERSION = "1";

  public static SimpleChannel INSTANCE;

  private static int id = 0;

  public static void register() {

    INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(Main.MODID, "blackclover"))
        .networkProtocolVersion(() -> PTC_VERSION).clientAcceptedVersions(PTC_VERSION::equals)
        .serverAcceptedVersions(PTC_VERSION::equals).simpleChannel();

    //Client Packets
    register(CPacketOpenCurios.class, CPacketOpenCurios::encode, CPacketOpenCurios::decode,
        CPacketOpenCurios::handle);
    register(CPacketOpenVanilla.class, CPacketOpenVanilla::encode, CPacketOpenVanilla::decode,
        CPacketOpenVanilla::handle);
    register(CPacketScroll.class, CPacketScroll::encode, CPacketScroll::decode,
        CPacketScroll::handle);
    register(CPacketDestroy.class, CPacketDestroy::encode, CPacketDestroy::decode,
        CPacketDestroy::handle);
    register(CPacketToggleRender.class, CPacketToggleRender::encode, CPacketToggleRender::decode,
        CPacketToggleRender::handle);

    // Server Packets
    register(SPacketSyncStack.class, SPacketSyncStack::encode, SPacketSyncStack::decode,
        SPacketSyncStack::handle);
    register(SPacketScroll.class, SPacketScroll::encode, SPacketScroll::decode,
        SPacketScroll::handle);
    register(SPacketSyncOperation.class, SPacketSyncOperation::encode, SPacketSyncOperation::decode,
        SPacketSyncOperation::handle);
    register(SPacketSyncCurios.class, SPacketSyncCurios::encode, SPacketSyncCurios::decode,
        SPacketSyncCurios::handle);
    register(SPacketBreak.class, SPacketBreak::encode, SPacketBreak::decode, SPacketBreak::handle);
    register(SPacketGrabbedItem.class, SPacketGrabbedItem::encode, SPacketGrabbedItem::decode,
        SPacketGrabbedItem::handle);
    register(SPacketSetIcons.class, SPacketSetIcons::encode, SPacketSetIcons::decode,
        SPacketSetIcons::handle);
    register(SPacketSyncRender.class, SPacketSyncRender::encode, SPacketSyncRender::decode,
        SPacketSyncRender::handle);
    register(SPacketSyncModifiers.class, SPacketSyncModifiers::encode, SPacketSyncModifiers::decode,
        SPacketSyncModifiers::handle);

    // Assignment of curio breaking to the network instance
    CuriosApi.getCuriosHelper().setBrokenCurioConsumer((id, index, livingEntity) -> INSTANCE
        .send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> livingEntity),
            new SPacketBreak(livingEntity.getId(), id, index)));
  }

  private static <M> void register(Class<M> messageType, BiConsumer<M, PacketBuffer> encoder,
                                   Function<PacketBuffer, M> decoder,
                                   BiConsumer<M, Supplier<NetworkEvent.Context>> messageConsumer) {
    INSTANCE.registerMessage(id++, messageType, encoder, decoder, messageConsumer);
  }
}

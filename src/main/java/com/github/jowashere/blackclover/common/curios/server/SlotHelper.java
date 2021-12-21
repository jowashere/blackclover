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

package com.github.jowashere.blackclover.common.curios.server;

import com.github.jowashere.blackclover.api.curios.CuriosApi;
import com.github.jowashere.blackclover.api.curios.type.ISlotType;
import com.github.jowashere.blackclover.api.curios.type.inventory.ICurioStacksHandler;
import com.github.jowashere.blackclover.api.curios.type.util.ISlotHelper;
import com.github.jowashere.blackclover.common.curios.inventory.CurioStacksHandler;
import com.github.jowashere.blackclover.common.curios.inventory.container.CuriosContainer;
import com.github.jowashere.blackclover.common.curios.network.server.sync.SPacketSyncOperation;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.PacketDistributor;


import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

public class SlotHelper implements ISlotHelper {

  private Map<String, ISlotType> idToType = new HashMap<>();

  @Override
  public void addSlotType(ISlotType slotType) {
    this.idToType.put(slotType.getIdentifier(), slotType);
  }

  @Override
  public Optional<ISlotType> getSlotType(String identifier) {
    return Optional.ofNullable(this.idToType.get(identifier));
  }

  @Override
  public Collection<ISlotType> getSlotTypes() {
    return Collections.unmodifiableCollection(idToType.values());
  }

  @Override
  public Collection<ISlotType> getSlotTypes(LivingEntity livingEntity) {
    return getSlotTypes();
  }

  @Override
  public SortedMap<ISlotType, ICurioStacksHandler> createSlots() {
    SortedMap<ISlotType, ICurioStacksHandler> curios = new TreeMap<>();
    this.getSlotTypes().stream().filter(type -> !type.isLocked()).collect(Collectors.toSet())
        .forEach(type -> curios.put(type,
            new CurioStacksHandler(null, type.getIdentifier(), type.getSize(), 0, type.isVisible(),
                type.hasCosmetic())));
    return curios;
  }

  @Override
  public SortedMap<ISlotType, ICurioStacksHandler> createSlots(LivingEntity livingEntity) {
    SortedMap<ISlotType, ICurioStacksHandler> curios = new TreeMap<>();
    CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).ifPresent(
        handler -> this.getSlotTypes().stream().filter(type -> !type.isLocked())
            .collect(Collectors.toSet()).forEach(type -> curios.put(type,
                new CurioStacksHandler(handler, type.getIdentifier(), type.getSize(), 0,
                    type.isVisible(), type.hasCosmetic()))));
    return curios;
  }

  @Override
  public Set<String> getSlotTypeIds() {
    return Collections.unmodifiableSet(idToType.keySet());
  }

  @Override
  public int getSlotsForType(@Nonnull final LivingEntity livingEntity, String identifier) {
    return CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).map(
        handler -> handler.getStacksHandler(identifier).map(ICurioStacksHandler::getSlots)
            .orElse(0)).orElse(0);
  }

  @Override
  public void setSlotsForType(String id, final LivingEntity livingEntity, int amount) {
    int difference = amount - getSlotsForType(livingEntity, id);

    if (difference > 0) {
      growSlotType(id, difference, livingEntity);
    } else if (difference < 0) {
      shrinkSlotType(id, Math.abs(difference), livingEntity);
    }
  }

  @Override
  public void growSlotType(String id, final LivingEntity livingEntity) {
    growSlotType(id, 1, livingEntity);
  }

  @Override
  public void growSlotType(String id, int amount, final LivingEntity livingEntity) {
    CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).ifPresent(handler -> {
      handler.growSlotType(id, amount);

      if (livingEntity instanceof ServerPlayerEntity) {
        ServerPlayerEntity player = (ServerPlayerEntity) livingEntity;
        NetworkLoader.INSTANCE
            .send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player),
                new SPacketSyncOperation(livingEntity.getId(), id, SPacketSyncOperation.Operation.GROW, amount));

        if (player.containerMenu instanceof CuriosContainer) {
          ((CuriosContainer) player.containerMenu).resetSlots();
        }
      }
    });
  }

  @Override
  public void shrinkSlotType(String id, final LivingEntity livingEntity) {
    shrinkSlotType(id, 1, livingEntity);
  }

  @Override
  public void shrinkSlotType(String id, int amount, final LivingEntity livingEntity) {
    CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).ifPresent(handler -> {
      handler.shrinkSlotType(id, amount);

      if (livingEntity instanceof ServerPlayerEntity) {
        ServerPlayerEntity player = (ServerPlayerEntity) livingEntity;
        NetworkLoader.INSTANCE
            .send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player),
                new SPacketSyncOperation(livingEntity.getId(), id, SPacketSyncOperation.Operation.SHRINK, amount));

        if (player.containerMenu instanceof CuriosContainer) {
          ((CuriosContainer) player.containerMenu).resetSlots();
        }
      }
    });
  }


  @Override
  public void unlockSlotType(String id, final LivingEntity livingEntity) {
    CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity)
        .ifPresent(handler -> this.getSlotType(id).ifPresent(type -> {
          handler.unlockSlotType(id, type.getSize(), type.isVisible(), type.hasCosmetic());

          if (livingEntity instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) livingEntity;
            NetworkLoader.INSTANCE
                .send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player),
                    new SPacketSyncOperation(livingEntity.getId(), id, SPacketSyncOperation.Operation.UNLOCK,
                        type.getSize(), type.isVisible(), type.hasCosmetic()));

            if (player.containerMenu instanceof CuriosContainer) {
              ((CuriosContainer) player.containerMenu).resetSlots();
            }
          }
        }));
  }

  @Override
  public void lockSlotType(String id, final LivingEntity livingEntity) {
    CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).ifPresent(handler -> {
      handler.lockSlotType(id);

      if (livingEntity instanceof ServerPlayerEntity) {
        ServerPlayerEntity player = (ServerPlayerEntity) livingEntity;
        NetworkLoader.INSTANCE
            .send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player),
                new SPacketSyncOperation(livingEntity.getId(), id, SPacketSyncOperation.Operation.LOCK));

        if (player.containerMenu instanceof CuriosContainer) {
          ((CuriosContainer) player.containerMenu).resetSlots();
        }
      }
    });
  }
}

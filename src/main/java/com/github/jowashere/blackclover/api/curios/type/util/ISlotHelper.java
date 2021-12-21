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

package com.github.jowashere.blackclover.api.curios.type.util;

import com.github.jowashere.blackclover.api.curios.type.ISlotType;
import com.github.jowashere.blackclover.api.curios.type.inventory.ICurioStacksHandler;
import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;

import java.util.*;

public interface ISlotHelper {

  /**
   * Registers a {@link com.github.jowashere.blackclover.api.curios.type.ISlotType} instance.
   * <br>
   * Modders: DO NOT USE DIRECTLY - Use IMC to send the appropriate {api.SlotTypeMessage}
   *
   * @param slotType The { ISlotType} instance
   */
  void addSlotType(ISlotType slotType);

  /**
   * Gets the {@link ISlotType} registered to the given identifier, or {@link Optional#empty()} if
   * none is registered.
   *
   * @param identifier The {@link ISlotType} identifier
   * @return The {@link ISlotType} registered to the identifier
   */
  Optional<ISlotType> getSlotType(String identifier);

  /**
   * @return A collection of all registered {@link ISlotType}
   */
  Collection<ISlotType> getSlotTypes();

  /**
   * @return A collection of all registered {@link ISlotType} for a specific entity
   */
  Collection<ISlotType> getSlotTypes(LivingEntity livingEntity);

  /**
   * Gets all unique registered {@link ISlotType} identifiers.
   *
   * @return A set of identifiers
   */
  Set<String> getSlotTypeIds();

  /**
   * Retrieves the number of slots that an entity has for a specific curio type.
   *
   * @param livingEntity The holder of the slot(s) as a {@link LivingEntity}
   * @param id           The identifier of the {@link ISlotType}
   * @return The number of slots
   */
  int getSlotsForType(LivingEntity livingEntity, String id);

  /**
   * Sets the number of slots that an entity has for a specific curio type.
   *
   * @param id           The identifier of the {@link ISlotType}
   * @param livingEntity The holder of the slot(s) as a {@link LivingEntity}
   * @param amount       The number of slots
   */
  void setSlotsForType(String id, LivingEntity livingEntity, int amount);

  /**
   * Adds a {@link ISlotType} to the entity with default settings.
   *
   * @param id           The identifier of the {@link ISlotType}
   * @param livingEntity The holder of the slot(s) as a {@link LivingEntity}
   */
  void unlockSlotType(String id, LivingEntity livingEntity);

  /**
   * Removes a {@link ISlotType} from the entity.
   *
   * @param id           The identifier of the {@link ISlotType}
   * @param livingEntity The holder of the slot(s) as a {@link LivingEntity}
   */
  void lockSlotType(String id, final LivingEntity livingEntity);

  // ============ DEPRECATED ================

  /**
   * @return A map sorted by {@link ISlotType} with instances of { ICurioStacksHandler} using
   * default settings
   * @deprecated Use {@link ISlotHelper#getSlotTypes(LivingEntity)}
   */
  @Deprecated
  SortedMap<ISlotType, ICurioStacksHandler> createSlots(LivingEntity livingEntity);

  /**
   * @return A map sorted by {@link ISlotType} with instances of { ICurioStacksHandler} using
   * default settings
   * @deprecated Use {@link ISlotHelper#getSlotTypes()}
   */
  @Deprecated
  SortedMap<ISlotType, ICurioStacksHandler> createSlots();

  /**
   * @param id           The identifier of the {@link ISlotType}
   * @param livingEntity The holder of the slot(s) as a {@link LivingEntity}
   * @deprecated Add a slot modifier instead using {ICuriosHelper#addSlotModifier(Multimap, String, UUID, double, AttributeModifier.Operation)}
   * when overriding {capability.ICurio#getAttributeModifiers(SlotContext, UUID)}
   * <br>
   * Adds a single slot to the {@link ISlotType} with the associated identifier. If the slot to
   * be added is for a type that is not enabled on the entity, it will not be added. For adding
   * slot(s) for types that are not yet available, there must first be a call to {@link
   * ISlotHelper#unlockSlotType(String, LivingEntity)}
   */
  @Deprecated
  void growSlotType(String id, LivingEntity livingEntity);

  /**
   * @param id           The identifier of the {@link ISlotType}
   * @param amount       The number of slots to add
   * @param livingEntity The holder of the slot(s) as a {@link LivingEntity}
   * @deprecated Add a slot modifier instead using {.util.ICuriosHelper#addSlotModifier(Multimap, String, UUID, double, AttributeModifier.Operation)}
   * when overriding {capability.ICurio#getAttributeModifiers(SlotContext, UUID)}
   * <br>
   * Adds multiple slots to the {@link ISlotType} with the associated identifier. If the slot to be
   * added is for a type that is not enabled on the entity, it will not be added. For adding slot(s)
   * for types that are not yet available, there must first be a call to {@link
   * ISlotHelper#unlockSlotType(String, LivingEntity)}
   */
  @Deprecated
  void growSlotType(String id, int amount, LivingEntity livingEntity);

  /**
   * @param id           The identifier of the {@link ISlotType}
   * @param livingEntity The holder of the slot(s) as a {@link LivingEntity}
   * @deprecated Add a slot modifier instead using {util.ICuriosHelper#addSlotModifier(Multimap, String, UUID, double, AttributeModifier.Operation)}
   * when overriding {capability.ICurio#getAttributeModifiers(SlotContext, UUID)}
   * <br>
   * Removes a single slot to the {@link ISlotType} with the associated identifier. If the slot to
   * be removed is the last slot available, it will not be removed. For the removal of the last
   * slot, please see {@link ISlotHelper#lockSlotType(String, LivingEntity)}
   */
  @Deprecated
  void shrinkSlotType(String id, LivingEntity livingEntity);

  /**
   * @param id           The identifier of the {@link ISlotType}
   * @param livingEntity The holder of the slot(s) as a {@link LivingEntity}
   * @deprecated Add a slot modifier instead using {util.ICuriosHelper#addSlotModifier(Multimap, String, UUID, double, AttributeModifier.Operation)}
   * when overriding {type.capability.ICurio#getAttributeModifiers(SlotContext, UUID)}
   * <br>
   * Removes multiple slots from the {@link ISlotType} with the associated identifier. If the slot
   * to be removed is the last slot available, it will not be removed. For the removal of the last
   * slot, please see {@link ISlotHelper#lockSlotType(String, LivingEntity)}
   */
  @Deprecated
  void shrinkSlotType(String id, int amount, LivingEntity livingEntity);
}

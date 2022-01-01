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

package com.github.jowashere.blackclover.api.curios.type.inventory;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface ICurioStacksHandler {

  /**
   * Gets the {@link IDynamicStackHandler} for the equipped curio stacks.
   *
   * @return The {@link IDynamicStackHandler} for the equipped curio stacks
   */
  IDynamicStackHandler getStacks();

  /**
   * Gets the {@link IDynamicStackHandler} for the equipped cosmetic curio stacks.
   * <br>
   * The size of this list should always match the sie of {@link ICurioStacksHandler#getStacks()}
   *
   * @return The {@link IDynamicStackHandler} for the equipped cosmetic curio stacks
   */
  IDynamicStackHandler getCosmeticStacks();

  /**
   * Gets a list of boolean values that represent render states. True for rendering and false for no
   * rendering.
   * <br>
   * The size of this list should always match the size of {@link ICurioStacksHandler#getStacks()}.
   *
   * @return A list of boolean values for render states
   */
  NonNullList<Boolean> getRenders();

  /**
   * Gets the number of slots for equipped curio stacks.
   * <br>
   * This number should always match the size of {@link ICurioStacksHandler#getStacks()}
   *
   * @return The number of slots for equipped curio stacks.
   */
  int getSlots();

  /**
   * Gets whether or not this stack handler should be visible. This does not lock the stack handler
   * from being used regardless.
   *
   * @return True or false for visibility
   */
  boolean isVisible();

  /**
   * Gets whether or not this stack handler has cosmetic handling. This does not lock the cosmetic
   * stack handler from being used regardless.
   *
   * @return True or false for cosmetic handling
   */
  boolean hasCosmetic();

  /**
   * Writes the data for this handler.
   *
   * @return A {@link CompoundNBT} representing the serialized data
   */
  CompoundNBT serializeNBT();

  /**
   * Reads the data into this handler.
   *
   * @param nbt A {@link CompoundNBT} representing the serialized data
   */
  void deserializeNBT(CompoundNBT nbt);

  /**
   * Retrieves the slot identifier associated with the handler.
   *
   * @return The slot identifier
   */
  String getIdentifier();

  /**
   * Retrieves all the slot modifiers on the handler.
   *
   * @return A map of modifiers with the UUID as keys and {@link AttributeModifier} as values
   */
  Map<UUID, AttributeModifier> getModifiers();

  /**
   * Retrieves all the permanent slot modifiers on the handler.
   * <br>
   * These slot modifiers are serialized on the handler.
   *
   * @return A set of {@link AttributeModifier}
   */
  Set<AttributeModifier> getPermanentModifiers();

  /**
   * Retrieves all the slot modifiers for a given operation on the handler.
   *
   * @param operation The operation of the modifiers
   * @return A collection of {@link AttributeModifier}
   */
  Collection<AttributeModifier> getModifiersByOperation(AttributeModifier.Operation operation);

  /**
   * Adds a temporary slot modifier to the handler.
   * <br>
   * These slot modifiers are not serialized on the handler.
   *
   * @param modifier The {@link AttributeModifier} instance to add
   */
  void addTransientModifier(AttributeModifier modifier);

  /**
   * Adds a permanent slot modifier to the handler.
   * <br>
   * These slot modifiers are serialized on the handler.
   *
   * @param modifier The {@link AttributeModifier} instance to add
   */
  void addPermanentModifier(AttributeModifier modifier);

  /**
   * Removes a slot modifier from the handler.
   *
   * @param uuid The UUID of the modifier to remove
   */
  void removeModifier(UUID uuid);

  /**
   * Removes all the slot modifiers on the handler.
   */
  void clearModifiers();

  /**
   * Removes the cached modifiers that appear upon deserialization of the handler.
   * <br>
   * Primarily for internal use, used as a workaround to avoid calculating slot stacks before slot
   * modifiers are initially applied.
   */
  void clearCachedModifiers();

  /**
   * Copies all the slot modifiers from another instance to this one.
   *
   * @param other The other instance
   */
  void copyModifiers(ICurioStacksHandler other);

  /**
   * Recalculates the slot modifiers and resizes the handler.
   */
  void update();

  /**
   * Retrieves the NBT data to sync to clients.
   *
   * @return The data represented as a {@link CompoundNBT}
   */
  CompoundNBT getSyncTag();

  /**
   * Applies the NBT data synced to clients.
   * <br>
   * Client-side only.
   *
   * @param tag The data represented as a {@link CompoundNBT}
   */
  void applySyncTag(CompoundNBT tag);

  // ============ DEPRECATED ================

  /**
   * @return The number of the size offset for this instance
   * @deprecated Use the new attribute modifier system through {@link ICurioStacksHandler#getModifiers()}
   * <br>
   * Gets the size offset for this instance. This value is used to persist size changes for this
   * handler even when the underlying size changes.
   */
  @Deprecated
  int getSizeShift();

  /**
   * @param amount The number of slots to add to the handler
   * @deprecated Add a slot modifier instead using {ICuriosHelper#addSlotModifier(Multimap, String, UUID, double, AttributeModifier.Operation)}
   * when overriding {ICurio#getAttributeModifiers(SlotContext, UUID)}
   * <p>
   * Increases the number of slots by the given amount.
   */
  @Deprecated
  void grow(int amount);

  /**
   * @param amount The number of slots to remove from the handler
   * @deprecated Add a slot modifier instead using {ICuriosHelper#addSlotModifier(Multimap, String, UUID, double, AttributeModifier.Operation)}
   * when overriding {ICurio#getAttributeModifiers(SlotContext, UUID)}
   * <p>
   * Decreases the number of slots by the given amount. This should not decrease the final number of
   * slots below 1.
   */
  @Deprecated
  void shrink(int amount);
}

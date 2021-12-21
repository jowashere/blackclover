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

import com.github.jowashere.blackclover.api.curios.SlotContext;
import com.github.jowashere.blackclover.api.curios.type.capability.ICurio;
import com.github.jowashere.blackclover.api.curios.type.capability.ICuriosItemHandler;
import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.logging.log4j.util.TriConsumer;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

public interface ICuriosHelper {

  /**
   * Gets a {@link LazyOptional} of the curio capability attached to the {@link ItemStack}.
   *
   * @param stack The {@link ItemStack} to get the curio capability from
   * @return {@link LazyOptional} of the curio capability
   */
  LazyOptional<ICurio> getCurio(ItemStack stack);

  /**
   * Gets a {@link LazyOptional} of the curio inventory capability attached to the entity.
   *
   * @param livingEntity The {@link LivingEntity} to get the curio inventory capability from
   * @return {@link LazyOptional} of the curio inventory capability
   */
  LazyOptional<ICuriosItemHandler> getCuriosHandler(LivingEntity livingEntity);

  /**
   * Retrieves a set of string identifiers from the curio tags associated with the given item.
   *
   * @param item The {@link Item} to retrieve curio tags for
   * @return Set of unique curio identifiers associated with the item
   */
  Set<String> getCurioTags(Item item);

  /**
   * Gets a {@link LazyOptional} of an {@link IItemHandlerModifiable} that contains all of the
   * equipped curio stacks (not including cosmetics).
   *
   * @param livingEntity The wearer of the curios
   * @return The equipped curio stacks, or empty if there is no curios handler
   */
  LazyOptional<IItemHandlerModifiable> getEquippedCurios(LivingEntity livingEntity);

  /**
   * Gets the first found {@link ItemStack} of the {@link Item} type equipped in a curio slot, or
   * {@link Optional#empty()} if no matches were found.
   *
   * @param item         The {@link Item} to find
   * @param livingEntity The wearer as a {@link LivingEntity} of the item to be found
   * @return An {@link Optional} wrapper of the found triplet, or {@link Optional#empty()} is
   * nothing was found.
   */
  Optional<ImmutableTriple<String, Integer, ItemStack>> findEquippedCurio(Item item,
                                                                          @Nonnull
                                                                              LivingEntity livingEntity);

  /**
   * Gets the first found {@link ItemStack} of the item type equipped in a curio slot that matches
   * the filter, or {@link Optional#empty()} if no matches were found.
   *
   * @param filter       The filter to test against
   * @param livingEntity The wearer as a {@link LivingEntity} of the item to be found
   * @return An {@link Optional#empty()} wrapper of the found triplet, or {@link Optional#empty()}
   * is nothing was found.
   */
  @Nonnull
  Optional<ImmutableTriple<String, Integer, ItemStack>> findEquippedCurio(
      Predicate<ItemStack> filter, @Nonnull LivingEntity livingEntity);

  /**
   * Retrieves a map of attribute modifiers for the ItemStack.
   * <br>
   * Note that only the identifier is guaranteed to be present in the slot context. For instances
   * where the ItemStack may not be in a curio slot, such as when retrieving item tooltips, the
   * index is -1 and the wearer may be null.
   *
   * @param slotContext Context about the slot that the ItemStack is equipped in or may potentially
   *                    be equipped in
   * @param uuid        Slot-unique UUID
   * @param stack       The ItemStack in question
   * @return A map of attribute modifiers
   */
  Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid,
                                                               ItemStack stack);

  /**
   * Adds a slot modifier to a specified attribute map.
   *
   * @param map         A {@link Multimap} of attributes to attribute modifiers
   * @param identifier  The identifier of the slot to add the modifier onto
   * @param uuid        A UUID associated wth the slot
   * @param amount      The amount of the modifier
   * @param operation   The operation of the modifier
   */
  void addSlotModifier(Multimap<Attribute, AttributeModifier> map, String identifier, UUID uuid,
                       double amount, AttributeModifier.Operation operation);

  /**
   * Checks if the ItemStack is valid for a particular stack and slot context.
   *
   * @param slotContext Context about the slot that the ItemStack is being checked for
   * @param stack       The ItemStack in question
   * @return True if the ItemStack is valid for the slot, false otherwise
   */
  boolean isStackValid(SlotContext slotContext, ItemStack stack);

  /**
   * Passes three inputs into an internal triple-input consumer that should be used from the
   * single-input consumer in {ItemStack#damageItem(int, LivingEntity, Consumer)}
   * <br>
   * This will be necessary in order to trigger break animations in curio slots
   * <br>
   * Example: { stack.damageItem(amount, entity, damager -> CuriosApi.getCuriosHelper().onBrokenCurio(id,
   * index, damager)); }
   *
   * @param id      The {type.ISlotType} String identifier
   * @param index   The slot index of the identifier
   * @param damager The entity that is breaking the item
   */
  void onBrokenCurio(String id, int index, LivingEntity damager);

  /**
   * Sets the {@link TriConsumer} that should be used with {ItemStack#damageItem(int,
   * LivingEntity, Consumer)} when triggering break animations in curio slots
   *
   * @param consumer The {@link TriConsumer} taking an {type.ISlotType}
   *                 identifier, a slot index, and the wearer as a {@link LivingEntity}
   */
  void setBrokenCurioConsumer(TriConsumer<String, Integer, LivingEntity> consumer);

  // ========= DEPRECATED =============

  /**
   * @deprecated See {@link ICuriosHelper#getAttributeModifiers(SlotContext, UUID, ItemStack)} for
   * an alternative method with additional context and a slot-unique UUID.
   */
  @Deprecated
  Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack);
}

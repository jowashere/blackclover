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

package com.github.jowashere.blackclover.common.curios.inventory;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.api.curios.CuriosApi;
import com.github.jowashere.blackclover.api.curios.SlotContext;
import com.github.jowashere.blackclover.api.curios.event.CurioEquipEvent;
import com.github.jowashere.blackclover.api.curios.event.CurioUnequipEvent;
import com.github.jowashere.blackclover.api.curios.type.inventory.IDynamicStackHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.items.SlotItemHandler;


import javax.annotation.Nonnull;

public class CurioSlot extends SlotItemHandler {

  private final String identifier;
  private final PlayerEntity player;
  private final SlotContext slotContext;

  private final NonNullList<Boolean> renderStatuses;

  public CurioSlot(PlayerEntity player, IDynamicStackHandler handler, int index, String identifier,
                   int xPosition, int yPosition, NonNullList<Boolean> renders) {
    super(handler, index, xPosition, yPosition);
    this.identifier = identifier;
    this.renderStatuses = renders;
    this.player = player;
    this.slotContext = new SlotContext(identifier, player, index);
    this.setBackground(PlayerContainer.BLOCK_ATLAS,
        player.getEntity().level.isClientSide() ? CuriosApi.getIconHelper().getIcon(identifier) :
            new ResourceLocation(Main.MODID, "item/empty_curio_slot"));
  }

  public String getIdentifier() {
    return this.identifier;
  }

  public boolean getRenderStatus() {
    return this.renderStatuses.get(this.getSlotIndex());
  }

  @OnlyIn(Dist.CLIENT)
  public String getSlotName() {
    return I18n.get("curios.identifier." + this.identifier);
  }

  @Override
  public boolean mayPlace(@Nonnull ItemStack stack) {
    CurioEquipEvent equipEvent = new CurioEquipEvent(stack, slotContext);
    MinecraftForge.EVENT_BUS.post(equipEvent);
    Event.Result result = equipEvent.getResult();

    if (result == Event.Result.DENY) {
      return false;
    }
    return result == Event.Result.ALLOW ||
        (CuriosApi.getCuriosHelper().isStackValid(slotContext, stack) &&
            CuriosApi.getCuriosHelper().getCurio(stack)
                .map(curio -> curio.canEquip(identifier, player)).orElse(true) &&
            super.mayPlace(stack));
  }

  @Override
  public boolean mayPickup(PlayerEntity playerIn) {
    ItemStack stack = this.getItem();
    CurioUnequipEvent unequipEvent = new CurioUnequipEvent(stack, slotContext);
    MinecraftForge.EVENT_BUS.post(unequipEvent);
    Event.Result result = unequipEvent.getResult();

    if (result == Event.Result.DENY) {
      return false;
    }
    return result == Event.Result.ALLOW ||
        ((stack.isEmpty() || playerIn.isCreative() || !EnchantmentHelper.hasBindingCurse(stack)) &&
            CuriosApi.getCuriosHelper().getCurio(stack)
                .map(curio -> curio.canUnequip(this.identifier, playerIn)).orElse(true) &&
            super.mayPickup(playerIn));
  }
}

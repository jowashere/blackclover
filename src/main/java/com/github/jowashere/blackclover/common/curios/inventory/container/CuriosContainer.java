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

package com.github.jowashere.blackclover.common.curios.inventory.container;

import com.github.jowashere.blackclover.api.curios.CuriosApi;
import com.github.jowashere.blackclover.api.curios.type.capability.ICuriosItemHandler;
import com.github.jowashere.blackclover.api.curios.type.inventory.ICurioStacksHandler;
import com.github.jowashere.blackclover.api.curios.type.inventory.IDynamicStackHandler;
import com.github.jowashere.blackclover.common.curios.CuriosRegistry;
import com.github.jowashere.blackclover.common.curios.inventory.CosmeticCurioSlot;
import com.github.jowashere.blackclover.common.curios.inventory.CurioSlot;
import com.github.jowashere.blackclover.common.curios.network.client.CPacketScroll;
import com.github.jowashere.blackclover.common.curios.network.server.SPacketScroll;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.mojang.datafixers.util.Pair;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;


import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Optional;

public class CuriosContainer extends PlayerContainer {

  private static final ResourceLocation[] ARMOR_SLOT_TEXTURES = new ResourceLocation[] {
      PlayerContainer.EMPTY_ARMOR_SLOT_BOOTS, PlayerContainer.EMPTY_ARMOR_SLOT_LEGGINGS,
      PlayerContainer.EMPTY_ARMOR_SLOT_CHESTPLATE, PlayerContainer.EMPTY_ARMOR_SLOT_HELMET};
  private static final EquipmentSlotType[] VALID_EQUIPMENT_SLOTS = new EquipmentSlotType[] {
      EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS,
      EquipmentSlotType.FEET};

  public final LazyOptional<ICuriosItemHandler> curiosHandler;

  private final PlayerEntity player;
  private final boolean isLocalWorld;

  private CraftingInventory craftMatrix = new CraftingInventory(this, 2, 2);
  private CraftResultInventory craftResult = new CraftResultInventory();
  private int lastScrollIndex;
  private boolean cosmeticColumn;

  public CuriosContainer(int windowId, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
    this(windowId, playerInventory);
  }

  public CuriosContainer(int windowId, PlayerInventory playerInventory) {
    super(playerInventory, playerInventory.player.level.isClientSide, playerInventory.player);
    this.menuType = CuriosRegistry.CONTAINER_TYPE;
    this.containerId = windowId;
    this.lastSlots.clear();
    this.slots.clear();
    this.player = playerInventory.player;
    this.isLocalWorld = this.player.level.isClientSide;
    this.curiosHandler = CuriosApi.getCuriosHelper().getCuriosHandler(this.player);
    this.addSlot(
        new CraftingResultSlot(playerInventory.player, this.craftMatrix, this.craftResult, 0, 154,
            28));

    for (int i = 0; i < 2; ++i) {

      for (int j = 0; j < 2; ++j) {
        this.addSlot(new Slot(this.craftMatrix, j + i * 2, 98 + j * 18, 18 + i * 18));
      }
    }

    for (int k = 0; k < 4; ++k) {
      final EquipmentSlotType equipmentslottype = VALID_EQUIPMENT_SLOTS[k];
      this.addSlot(new Slot(playerInventory, 36 + (3 - k), 8, 8 + k * 18) {

        @Override
        public int getMaxStackSize() {
          return 1;
        }

        @Override
        public boolean mayPlace(@Nonnull ItemStack stack) {
          return stack.canEquip(equipmentslottype, CuriosContainer.this.player);
        }

        @Override
        public boolean mayPickup(@Nonnull PlayerEntity playerIn) {
          ItemStack itemstack = this.getItem();
          return (itemstack.isEmpty() || playerIn.isCreative() || !EnchantmentHelper
              .hasBindingCurse(itemstack)) && super.mayPickup(playerIn);
        }


        @OnlyIn(Dist.CLIENT)
        @Override
        public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
          return Pair.of(PlayerContainer.BLOCK_ATLAS,
              ARMOR_SLOT_TEXTURES[equipmentslottype.getIndex()]);
        }
      });
    }

    for (int l = 0; l < 3; ++l) {

      for (int j1 = 0; j1 < 9; ++j1) {
        this.addSlot(new Slot(playerInventory, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + l * 18));
      }
    }

    for (int i1 = 0; i1 < 9; ++i1) {
      this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 142));
    }
    this.addSlot(new Slot(playerInventory, 40, 77, 62) {
      @OnlyIn(Dist.CLIENT)
      @Override
      public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
        return Pair
            .of(PlayerContainer.BLOCK_ATLAS, PlayerContainer.EMPTY_ARMOR_SLOT_SHIELD);
      }
    });

    this.curiosHandler.ifPresent(curios -> {
      Map<String, ICurioStacksHandler> curioMap = curios.getCurios();
      int slots = 0;
      int yOffset = 12;

      for (String identifier : curioMap.keySet()) {
        ICurioStacksHandler stacksHandler = curioMap.get(identifier);
        IDynamicStackHandler stackHandler = stacksHandler.getStacks();

        if (stacksHandler.isVisible()) {

          for (int i = 0; i < stackHandler.getSlots() && slots < 8; i++) {
            this.addSlot(new CurioSlot(this.player, stackHandler, i, identifier, -18, yOffset,
                stacksHandler.getRenders()));
            yOffset += 18;
            slots++;
          }
        }
      }
      yOffset = 12;
      slots = 0;

      for (String identifier : curioMap.keySet()) {
        ICurioStacksHandler stacksHandler = curioMap.get(identifier);
        IDynamicStackHandler stackHandler = stacksHandler.getStacks();

        if (stacksHandler.isVisible()) {

          for (int i = 0; i < stackHandler.getSlots() && slots < 8; i++) {

            if (stacksHandler.hasCosmetic()) {
              IDynamicStackHandler cosmeticHandler = stacksHandler.getCosmeticStacks();
              this.cosmeticColumn = true;
              this.addSlot(
                  new CosmeticCurioSlot(this.player, cosmeticHandler, i, identifier, -37, yOffset));
            }
            yOffset += 18;
            slots++;
          }
        }
      }
    });
    this.scrollToIndex(0);
  }

  public boolean hasCosmeticColumn() {
    return this.cosmeticColumn;
  }

  public void resetSlots() {
    this.scrollToIndex(this.lastScrollIndex);
  }

  public void scrollToIndex(int indexIn) {
    this.curiosHandler.ifPresent(curios -> {
      Map<String, ICurioStacksHandler> curioMap = curios.getCurios();
      int slots = 0;
      int yOffset = 12;
      int index = 0;
      int startingIndex = indexIn;
      this.slots.subList(46, this.slots.size()).clear();

      if (this.lastSlots != null) {
        this.lastSlots.subList(46, this.lastSlots.size()).clear();
      }

      for (String identifier : curioMap.keySet()) {
        ICurioStacksHandler stacksHandler = curioMap.get(identifier);
        IDynamicStackHandler stackHandler = stacksHandler.getStacks();

        if (stacksHandler.isVisible()) {

          for (int i = 0; i < stackHandler.getSlots() && slots < 8; i++) {

            if (index >= startingIndex) {
              slots++;
            }
            index++;
          }
        }
      }

      startingIndex = Math.min(startingIndex, Math.max(0, index - 8));
      index = 0;
      slots = 0;

      for (String identifier : curioMap.keySet()) {
        ICurioStacksHandler stacksHandler = curioMap.get(identifier);
        IDynamicStackHandler stackHandler = stacksHandler.getStacks();

        if (stacksHandler.isVisible()) {

          for (int i = 0; i < stackHandler.getSlots() && slots < 8; i++) {

            if (index >= startingIndex) {
              this.addSlot(new CurioSlot(this.player, stackHandler, i, identifier, -18, yOffset,
                  stacksHandler.getRenders()));
              yOffset += 18;
              slots++;
            }
            index++;
          }
        }
      }
      index = 0;
      slots = 0;
      yOffset = 12;

      for (String identifier : curioMap.keySet()) {
        ICurioStacksHandler stacksHandler = curioMap.get(identifier);
        IDynamicStackHandler stackHandler = stacksHandler.getStacks();

        if (stacksHandler.isVisible()) {

          for (int i = 0; i < stackHandler.getSlots() && slots < 8; i++) {

            if (index >= startingIndex) {

              if (stacksHandler.hasCosmetic()) {
                IDynamicStackHandler cosmeticHandler = stacksHandler.getCosmeticStacks();
                this.cosmeticColumn = true;
                this.addSlot(
                    new CosmeticCurioSlot(this.player, cosmeticHandler, i, identifier, -37,
                        yOffset));
              }
              yOffset += 18;
              slots++;
            }
            index++;
          }
        }
      }

      if (!this.isLocalWorld) {
        NetworkLoader.INSTANCE
            .send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) this.player),
                new SPacketScroll(this.containerId, indexIn));
      }
      this.lastScrollIndex = indexIn;
    });
  }

  public void scrollTo(float pos) {

    this.curiosHandler.ifPresent(curios -> {
      int k = (curios.getVisibleSlots() - 8);
      int j = (int) (pos * k + 0.5D);

      if (j < 0) {
        j = 0;
      }

      if (j == this.lastScrollIndex) {
        return;
      }

      if (this.isLocalWorld) {
        NetworkLoader.INSTANCE
            .send(PacketDistributor.SERVER.noArg(), new CPacketScroll(this.containerId, j));
      }
    });
  }

  @Override
  public void slotsChanged(@Nonnull IInventory inventoryIn) {

    if (!this.player.level.isClientSide) {
      ServerPlayerEntity playerMP = (ServerPlayerEntity) this.player;
      ItemStack stack = ItemStack.EMPTY;
      MinecraftServer server = this.player.level.getServer();

      if (server == null) {
        return;
      }

      Optional<ICraftingRecipe> recipe = server.getRecipeManager()
          .getRecipeFor(IRecipeType.CRAFTING, this.craftMatrix, this.player.level);

      if (recipe.isPresent()) {
        ICraftingRecipe craftingRecipe = recipe.get();
        if (this.craftResult.setRecipeUsed(this.player.level, playerMP, craftingRecipe)) {
          stack = craftingRecipe.assemble(this.craftMatrix);
        }
      }
      this.craftResult.setItem(0, stack);
      playerMP.connection.send(new SSetSlotPacket(this.containerId, 0, stack));
    }
  }

  @Override
  public void removed(@Nonnull PlayerEntity playerIn) {
    super.removed(playerIn);
    this.craftResult.clearContent();

    if (!playerIn.level.isClientSide) {
      this.clearContainer(playerIn, playerIn.level, this.craftMatrix);
    }
  }

  public boolean canScroll() {

    return this.curiosHandler.map(curios -> {

      if (curios.getVisibleSlots() > 8) {
        return 1;
      }
      return 0;
    }).orElse(0) == 1;
  }

  @Override
  public boolean stillValid(@Nonnull PlayerEntity playerIn) {

    return true;
  }

  @Nonnull
  @Override
  public ItemStack quickMoveStack(@Nonnull PlayerEntity playerIn, int index) {

    ItemStack itemstack = ItemStack.EMPTY;
    Slot slot = this.slots.get(index);

    if (slot != null && slot.hasItem()) {
      ItemStack itemstack1 = slot.getItem();
      itemstack = itemstack1.copy();
      EquipmentSlotType entityequipmentslot = MobEntity.getEquipmentSlotForItem(itemstack);
      if (index == 0) {

        if (!this.moveItemStackTo(itemstack1, 9, 45, true)) {
          return ItemStack.EMPTY;
        }
        slot.onQuickCraft(itemstack1, itemstack);
      } else if (index < 5) {

        if (!this.moveItemStackTo(itemstack1, 9, 45, false)) {
          return ItemStack.EMPTY;
        }
      } else if (index < 9) {

        if (!this.moveItemStackTo(itemstack1, 9, 45, false)) {
          return ItemStack.EMPTY;
        }
      } else if (entityequipmentslot.getType() == EquipmentSlotType.Group.ARMOR
          && !this.slots.get(8 - entityequipmentslot.getIndex()).hasItem()) {
        int i = 8 - entityequipmentslot.getIndex();

        if (!this.moveItemStackTo(itemstack1, i, i + 1, false)) {
          return ItemStack.EMPTY;
        }
      } else if (index < 46 && !CuriosApi.getCuriosHelper().getCurioTags(itemstack.getItem())
          .isEmpty()) {

        if (!this.moveItemStackTo(itemstack1, 46, this.slots.size(), false)) {
          return ItemStack.EMPTY;
        }
      } else if (entityequipmentslot == EquipmentSlotType.OFFHAND && !(this.slots.get(45))
          .hasItem()) {

        if (!this.moveItemStackTo(itemstack1, 45, 46, false)) {
          return ItemStack.EMPTY;
        }
      } else if (index < 36) {
        if (!this.moveItemStackTo(itemstack1, 36, 45, false)) {
          return ItemStack.EMPTY;
        }
      } else if (index < 45) {
        if (!this.moveItemStackTo(itemstack1, 9, 36, false)) {
          return ItemStack.EMPTY;
        }
      } else if (!this.moveItemStackTo(itemstack1, 9, 45, false)) {
        return ItemStack.EMPTY;
      }

      if (itemstack1.isEmpty()) {
        slot.set(ItemStack.EMPTY);
      } else {
        slot.setChanged();
      }

      if (itemstack1.getCount() == itemstack.getCount()) {
        return ItemStack.EMPTY;
      }
      ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);

      if (index == 0) {
        playerIn.drop(itemstack2, false);
      }
    }

    return itemstack;
  }

  @Nonnull
  @Override
  public RecipeBookCategory getRecipeBookType() {
    return RecipeBookCategory.CRAFTING;
  }

  @Override
  public void fillCraftSlotsStackedContents(@Nonnull RecipeItemHelper itemHelperIn) {
    this.craftMatrix.fillStackedContents(itemHelperIn);
  }

  @Override
  public void clearCraftingContent() {
    this.craftMatrix.clearContent();
    this.craftResult.clearContent();
  }

  @Override
  public boolean recipeMatches(IRecipe<? super CraftingInventory> recipeIn) {
    return recipeIn.matches(this.craftMatrix, this.player.level);
  }

  @Override
  public int getResultSlotIndex() {
    return 0;
  }

  @Override
  public int getGridWidth() {
    return this.craftMatrix.getWidth();
  }

  @Override
  public int getGridHeight() {
    return this.craftMatrix.getHeight();
  }

  @Override
  public int getSize() {
    return 5;
  }
}

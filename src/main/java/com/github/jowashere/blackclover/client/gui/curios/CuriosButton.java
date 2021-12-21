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

package com.github.jowashere.blackclover.client.gui.curios;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nonnull;

public class CuriosButton extends ImageButton {

  private final ContainerScreen<?> parentGui;
  private boolean isRecipeBookVisible = false;

  CuriosButton(ContainerScreen<?> parentGui, int xIn, int yIn, int widthIn, int heightIn,
               int textureOffsetX, int textureOffsetY, int yDiffText, ResourceLocation resource) {

    super(xIn, yIn, widthIn, heightIn, textureOffsetX, textureOffsetY, yDiffText, resource,
        (button) -> {
          Minecraft mc = Minecraft.getInstance();

          if (parentGui instanceof CuriosScreen && mc.player != null) {
            InventoryScreen inventory = new InventoryScreen(mc.player);
            ItemStack stack = mc.player.inventory.getCarried();
            mc.player.inventory.setCarried(ItemStack.EMPTY);
            mc.setScreen(inventory);
            mc.player.inventory.setCarried(stack);
            NetworkHandler.INSTANCE
                .send(PacketDistributor.SERVER.noArg(), new CPacketOpenVanilla());
          } else {

            if (parentGui instanceof InventoryScreen) {
              InventoryScreen inventory = (InventoryScreen) parentGui;
              RecipeBookGui recipeBookGui = inventory.getRecipeBookComponent();

              if (recipeBookGui.isVisible()) {
                recipeBookGui.toggleVisibility();
              }
            }
            NetworkHandler.INSTANCE.send(PacketDistributor.SERVER.noArg(), new CPacketOpenCurios());
          }
        });
    this.parentGui = parentGui;
  }

  @Override
  public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY,
                     float partialTicks) {
    Tuple<Integer, Integer> offsets = CuriosScreen.getButtonOffset(parentGui instanceof CreativeScreen);
    x = parentGui.getGuiLeft() + offsets.getA();
    int yOffset = parentGui instanceof CreativeScreen ? 68 : 83;
    y = parentGui.getGuiTop() + offsets.getB() + yOffset;

    if (parentGui instanceof CreativeScreen) {
      CreativeScreen gui = (CreativeScreen) parentGui;
      boolean isInventoryTab = gui.getSelectedTab() == ItemGroup.TAB_INVENTORY.getId();
      this.active = isInventoryTab;

      if (!isInventoryTab) {
        return;
      }
    }
    super.render(matrixStack, mouseX, mouseY, partialTicks);
  }
}

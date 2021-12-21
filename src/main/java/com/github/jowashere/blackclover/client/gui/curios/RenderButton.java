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
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import top.theillusivec4.curios.common.inventory.CurioSlot;

import javax.annotation.Nonnull;

public class RenderButton extends ImageButton {

  private final ResourceLocation resourceLocation;
  private final int yTexStart;
  private final int xTexStart;
  private final CurioSlot slot;

  public RenderButton(CurioSlot slot, int xIn, int yIn, int widthIn, int heightIn, int xTexStartIn,
                      int yTexStartIn, int yDiffTextIn, ResourceLocation resourceLocationIn,
                      IPressable onPressIn) {
    super(xIn, yIn, widthIn, heightIn, xTexStartIn, yTexStartIn, yDiffTextIn, resourceLocationIn,
        256, 256, onPressIn);
    this.resourceLocation = resourceLocationIn;
    this.yTexStart = yTexStartIn;
    this.xTexStart = xTexStartIn;
    this.slot = slot;
  }

  @Override
  public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
    // NO-OP
  }

  public void renderButtonOverlay(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY,
                                  float partialTicks) {
    Minecraft minecraft = Minecraft.getInstance();
    minecraft.getTextureManager().bindTexture(this.resourceLocation);
    RenderSystem.disableDepthTest();
    int j = this.xTexStart;

    if (!slot.getRenderStatus()) {
      j += 8;
    }
    blit(matrixStack, this.x, this.y, (float) j, (float) this.yTexStart, this.width, this.height,
        256, 256);
    RenderSystem.enableDepthTest();
  }
}

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

package com.github.jowashere.blackclover.client.curios.gui.curios;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.api.curios.CuriosApi;
import com.github.jowashere.blackclover.api.curios.type.capability.ICuriosItemHandler;
import com.github.jowashere.blackclover.client.curios.CuriosClientConfig;
import com.github.jowashere.blackclover.client.curios.KeyRegistry;
import com.github.jowashere.blackclover.common.curios.inventory.CosmeticCurioSlot;
import com.github.jowashere.blackclover.common.curios.inventory.CurioSlot;
import com.github.jowashere.blackclover.common.curios.inventory.container.CuriosContainer;
import com.github.jowashere.blackclover.common.curios.network.client.CPacketToggleRender;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nonnull;

public class CuriosScreen extends ContainerScreen<CuriosContainer> implements IRecipeShownListener {

  static final ResourceLocation CURIO_INVENTORY = new ResourceLocation(Main.MODID,
      "textures/gui/inventory.png");
  static final ResourceLocation RECIPE_BUTTON_TEXTURE = new ResourceLocation(
      "minecraft:textures/gui/recipe_button.png");

  private static final ResourceLocation CREATIVE_INVENTORY_TABS = new ResourceLocation(
      "textures/gui/container/creative_inventory/tabs.png");

  private static float currentScroll;

  private final RecipeBookGui recipeBookGui = new RecipeBookGui();

  public boolean hasScrollBar;
  public boolean widthTooNarrow;

  private CuriosButton buttonCurios;
  private boolean isScrolling;
  private boolean buttonClicked;
  private boolean isRenderButtonHovered;

  public CuriosScreen(CuriosContainer curiosContainer, PlayerInventory playerInventory,
                      ITextComponent title) {
    super(curiosContainer, playerInventory, title);
    this.passEvents = true;
  }

  public static Tuple<Integer, Integer> getButtonOffset(boolean isCreative) {
    CuriosClientConfig.Client client = CuriosClientConfig.CLIENT;
    CuriosClientConfig.Client.ButtonCorner corner = client.buttonCorner.get();
    int x = 0;
    int y = 0;

    if (isCreative) {
      x += corner.getCreativeXoffset() + client.creativeButtonXOffset.get();
      y += corner.getCreativeYoffset() + client.creativeButtonYOffset.get();
    } else {
      x += corner.getXoffset() + client.buttonXOffset.get();
      y += corner.getYoffset() + client.buttonYOffset.get();
    }
    return new Tuple<>(x, y);
  }

  @Override
  public void init() {
    super.init();

    if (this.minecraft != null) {

      if (this.minecraft.player != null) {
        this.hasScrollBar = CuriosApi.getCuriosHelper().getCuriosHandler(this.minecraft.player)
            .map(handler -> handler.getVisibleSlots() > 8).orElse(false);

        if (this.hasScrollBar) {
          this.menu.scrollTo(currentScroll);
        }
      }
      int neededWidth = 431;

      if (this.hasScrollBar) {
        neededWidth += 30;
      }

      if (this.menu.hasCosmeticColumn()) {
        neededWidth += 40;
      }
      this.widthTooNarrow = this.width < neededWidth;
      this.recipeBookGui
          .init(this.width, this.height, this.minecraft, this.widthTooNarrow, this.menu);
      this.updateScreenPosition();
      this.children.add(this.recipeBookGui);
      this.setInitialFocus(this.recipeBookGui);

      /*
        This may not be a perfect workaround as it doesn't return the book upon switching back
        to survival mode. Creative inventory doesn't have this problem because it doesn't have
        recipe book at all, but here we only have one Screen and must toggle it circumstantially,
        and sadly Curios' recipe book isn't and must not be an independent object. I can't think
        of better implementation at the moment though.

        Anyhow, this is better than letting the book persist in creative without a way to toggle it.
        @author Extegral
       */
      if (this.getMinecraft().player != null && this.getMinecraft().player.isCreative()
          && this.recipeBookGui.isVisible()) {
        this.recipeBookGui.toggleVisibility();
        this.updateScreenPosition();
      }

      Tuple<Integer, Integer> offsets = getButtonOffset(false);
      this.buttonCurios = new CuriosButton(this, this.getGuiLeft() + offsets.getA(),
          this.height / 2 + offsets.getB(), 14, 14, 50, 0, 14, CURIO_INVENTORY);
      this.addButton(this.buttonCurios);

      if (!this.inventory.player.isCreative()) {
        this.addButton(new ImageButton(this.leftPos + 104, this.height / 2 - 22, 20, 18, 0, 0, 19,
            RECIPE_BUTTON_TEXTURE, (button) -> {
          this.recipeBookGui.initVisuals(this.widthTooNarrow);
          this.recipeBookGui.toggleVisibility();
          this.updateScreenPosition();
          ((ImageButton) button).setPosition(this.leftPos + 104, this.height / 2 - 22);
          this.buttonCurios
              .setPosition(this.leftPos + offsets.getA(), this.height / 2 + offsets.getB());
        }));
      }

      this.updateRenderButtons();
    }
  }

  public void updateRenderButtons() {
    this.buttons.removeIf(widget -> widget instanceof RenderButton);
    this.children.removeIf(widget -> widget instanceof RenderButton);

    for (Slot inventorySlot : this.menu.slots) {

      if (inventorySlot instanceof CurioSlot && !(inventorySlot instanceof CosmeticCurioSlot)) {
        this.addButton(
            new RenderButton((CurioSlot) inventorySlot, this.leftPos + inventorySlot.x + 11,
                this.topPos + inventorySlot.y - 3, 8, 8, 75, 0, 8, CURIO_INVENTORY,
                (button) -> NetworkLoader.INSTANCE.send(PacketDistributor.SERVER.noArg(),
                    new CPacketToggleRender(((CurioSlot) inventorySlot).getIdentifier(),
                        inventorySlot.getSlotIndex()))));
      }
    }
  }

  private void updateScreenPosition() {
    int i;

    if (this.recipeBookGui.isVisible() && !this.widthTooNarrow) {
      int offset = 148;

      if (this.hasScrollBar) {
        offset -= 30;
      }

      if (this.menu.hasCosmeticColumn()) {
        offset -= 40;
      }
      i = 177 + (this.width - this.getXSize() - offset) / 2;
    } else {
      i = (this.width - this.getXSize()) / 2;
    }
    this.leftPos = i;
    this.updateRenderButtons();
  }

  @Override
  public void tick() {
    super.tick();
    this.recipeBookGui.tick();
  }

  private boolean inScrollBar(double mouseX, double mouseY) {
    int i = this.leftPos;
    int j = this.leftPos;
    int k = i - 34;
    int l = j + 12;
    int i1 = k + 14;
    int j1 = l + 139;

    if (this.menu.hasCosmeticColumn()) {
      i1 -= 19;
      k -= 19;
    }
    return mouseX >= k && mouseY >= l && mouseX < i1 && mouseY < j1;
  }

  @Override
  public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(matrixStack);

    if (this.recipeBookGui.isVisible() && this.widthTooNarrow) {
      this.renderBg(matrixStack, partialTicks, mouseX, mouseY);
      this.recipeBookGui.render(matrixStack, mouseX, mouseY, partialTicks);
    } else {
      this.recipeBookGui.render(matrixStack, mouseX, mouseY, partialTicks);
      super.render(matrixStack, mouseX, mouseY, partialTicks);
      this.recipeBookGui.renderGhostRecipe(matrixStack, this.leftPos, this.topPos, true, partialTicks);

      boolean isButtonHovered = false;

      for (Widget button : this.buttons) {

        if (button instanceof RenderButton) {
          ((RenderButton) button).renderButtonOverlay(matrixStack, mouseX, mouseY, partialTicks);

          if (button.isHovered()) {
            isButtonHovered = true;
          }
        }
      }
      this.isRenderButtonHovered = isButtonHovered;
      ClientPlayerEntity clientPlayer = Minecraft.getInstance().player;

      if (!this.isRenderButtonHovered && clientPlayer != null && clientPlayer.inventory
          .getCarried().isEmpty() && this.getSlotUnderMouse() != null) {
        Slot slot = this.getSlotUnderMouse();

        if (slot instanceof CurioSlot && !slot.hasItem()) {
          CurioSlot slotCurio = (CurioSlot) slot;
          this.renderTooltip(matrixStack, new StringTextComponent(slotCurio.getSlotName()), mouseX,
              mouseY);
        }
      }
    }
    this.renderTooltip(matrixStack, mouseX, mouseY);
  }

  @Override
  protected void renderTooltip(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY) {
    Minecraft mc = this.minecraft;

    if (mc != null) {
      ClientPlayerEntity clientPlayer = mc.player;

      if (clientPlayer != null && clientPlayer.inventory.getCarried().isEmpty()) {

        if (this.isRenderButtonHovered) {
          this.renderTooltip(matrixStack, new TranslationTextComponent("gui.curios.toggle"), mouseX,
              mouseY);
        } else if (this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
          this.renderTooltip(matrixStack, this.hoveredSlot.getItem(), mouseX, mouseY);
        }
      }
    }
  }

  @Override
  public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {

    if (this.recipeBookGui.isVisible() && this.widthTooNarrow) {
      this.recipeBookGui.toggleVisibility();
      this.updateScreenPosition();
      return true;
    } else if (KeyRegistry.openCurios
        .isActiveAndMatches(InputMappings.getKey(p_keyPressed_1_, p_keyPressed_2_))) {
      ClientPlayerEntity playerEntity = this.getMinecraft().player;

      if (playerEntity != null) {
        playerEntity.closeContainer();
      }
      return true;
    } else {
      return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
    }
  }

  @Override
  protected void renderLabels(@Nonnull MatrixStack matrixStack, int mouseX,
                                                 int mouseY) {

    if (this.minecraft != null && this.minecraft.player != null) {
      this.font.draw(matrixStack, this.title, 97, 6, 4210752);
    }
  }

  /**
   * Draws the background layer of this container (behind the item).
   */

  @Override
  protected void renderBg(@Nonnull MatrixStack matrixStack,
                                                 float partialTicks, int mouseX, int mouseY) {

    if (this.minecraft != null && this.minecraft.player != null) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.minecraft.getTextureManager().bind(INVENTORY_LOCATION);
      int i = this.leftPos;
      int j = this.topPos;
      this.blit(matrixStack, i, j, 0, 0, this.getXSize(), this.getYSize());
      InventoryScreen.renderEntityInInventory(i + 51, j + 75, 30, (float) (i + 51) - mouseX,
          (float) (j + 75 - 50) - mouseY, this.minecraft.player);
      CuriosApi.getCuriosHelper().getCuriosHandler(this.minecraft.player).ifPresent(handler -> {
        int slotCount = handler.getVisibleSlots();

        if (slotCount > 0) {
          int upperHeight = 7 + Math.min(slotCount, 9) * 18;
          RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
          this.getMinecraft().getTextureManager().bind(CURIO_INVENTORY);
          int xTexOffset = 0;
          int width = 27;
          int xOffset = -26;

          if (this.menu.hasCosmeticColumn()) {
            xTexOffset = 92;
            width = 46;
            xOffset -= 19;
          }
          this.blit(matrixStack, i + xOffset, j + 4, xTexOffset, 0, width, upperHeight);

          if (slotCount <= 8) {
            this.blit(matrixStack, i + xOffset, j + 4 + upperHeight, xTexOffset, 151, width, 7);
          } else {
            this.blit(matrixStack, i + xOffset - 16, j + 4, 27, 0, 23, 158);
            this.getMinecraft().getTextureManager().bind(CREATIVE_INVENTORY_TABS);
            this.blit(matrixStack, i + xOffset - 8, j + 12 + (int) (127f * currentScroll), 232, 0,
                12, 15);
          }

          for (Slot slot : this.menu.slots) {

            if (slot instanceof CosmeticCurioSlot) {
              int x = this.leftPos + slot.x - 1;
              int y = this.topPos + slot.y - 1;
              this.getMinecraft().getTextureManager().bind(CURIO_INVENTORY);
              this.blit(matrixStack, x, y, 138, 0, 18, 18);
            }
          }
        }
      });
    }
  }

  /**
   * Test if the 2D point is in a rectangle (relative to the GUI). Args : rectX, rectY, rectWidth,
   * rectHeight, pointX, pointY
   */
  @Override
  protected boolean isHovering(int rectX, int rectY, int rectWidth, int rectHeight,
                                    double pointX, double pointY) {

    if (this.isRenderButtonHovered) {
      return false;
    }
    return (!this.widthTooNarrow || !this.recipeBookGui.isVisible()) && super
        .isHovering(rectX, rectY, rectWidth, rectHeight, pointX, pointY);
  }

  /**
   * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
   */
  @Override
  public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {

    if (this.recipeBookGui.mouseClicked(mouseX, mouseY, mouseButton)) {
      return true;
    } else if (this.inScrollBar(mouseX, mouseY)) {
      this.isScrolling = this.needsScrollBars();
      return true;
    }
    return this.widthTooNarrow && this.recipeBookGui.isVisible() || super
        .mouseClicked(mouseX, mouseY, mouseButton);
  }

  @Override
  public boolean mouseReleased(double mouseReleased1, double mouseReleased3, int mouseReleased5) {

    if (mouseReleased5 == 0) {
      this.isScrolling = false;
    }

    if (this.buttonClicked) {
      this.buttonClicked = false;
      return true;
    } else {
      return super.mouseReleased(mouseReleased1, mouseReleased3, mouseReleased5);
    }
  }

  @Override
  public boolean mouseDragged(double pMouseDragged1, double pMouseDragged3, int pMouseDragged5,
                              double pMouseDragged6, double pMouseDragged8) {

    if (this.isScrolling) {
      int i = this.getGuiTop() + 8;
      int j = i + 148;
      currentScroll = ((float) pMouseDragged3 - i - 7.5F) / (j - i - 15.0F);
      currentScroll = MathHelper.clamp(currentScroll, 0.0F, 1.0F);
      this.menu.scrollTo(currentScroll);
      return true;
    } else {
      return super.mouseDragged(pMouseDragged1, pMouseDragged3, pMouseDragged5, pMouseDragged6,
          pMouseDragged8);
    }
  }

  @Override
  public boolean mouseScrolled(double pMouseScrolled1, double pMouseScrolled3,
                               double pMouseScrolled5) {

    if (!this.needsScrollBars()) {
      return false;
    } else {
      int i = (this.menu).curiosHandler.map(ICuriosItemHandler::getVisibleSlots).orElse(1);
      currentScroll = (float) (currentScroll - pMouseScrolled5 / i);
      currentScroll = MathHelper.clamp(currentScroll, 0.0F, 1.0F);
      this.menu.scrollTo(currentScroll);
      return true;
    }
  }

  private boolean needsScrollBars() {
    return this.menu.canScroll();
  }

  @Override
  protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeftIn, int guiTopIn,
                                      int mouseButton) {
    boolean flag = mouseX < guiLeftIn || mouseY < guiTopIn || mouseX >= guiLeftIn + this.getXSize()
        || mouseY >= guiTopIn + this.getYSize();
    return this.recipeBookGui
        .hasClickedOutside(mouseX, mouseY, this.leftPos, this.topPos, this.getXSize(), this.getYSize(),
            mouseButton) && flag;
  }

  @Override
  protected void slotClicked(@Nonnull Slot slotIn, int slotId, int mouseButton,
                                  @Nonnull ClickType type) {
    super.slotClicked(slotIn, slotId, mouseButton, type);
    this.recipeBookGui.slotClicked(slotIn);
  }

  @Override
  public void recipesUpdated() {
    this.recipeBookGui.recipesUpdated();
  }

  @Override
  public void onClose() {
    this.recipeBookGui.removed();
    super.onClose();
  }

  @Nonnull
  @Override
  public RecipeBookGui getRecipeBookComponent() {
    return this.recipeBookGui;
  }
}

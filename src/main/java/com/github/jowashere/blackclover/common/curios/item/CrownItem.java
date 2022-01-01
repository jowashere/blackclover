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

package com.github.jowashere.blackclover.common.curios.item;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.api.curios.CuriosApi;
import com.github.jowashere.blackclover.api.curios.SlotTypePreset;
import com.github.jowashere.blackclover.api.curios.type.capability.ICurio;
import com.github.jowashere.blackclover.api.curios.type.capability.ICurioItem;
import com.github.jowashere.blackclover.client.curios.render.model.CrownModel;
import com.github.jowashere.blackclover.common.curios.capability.CurioItemCapability;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;


import javax.annotation.Nonnull;
import java.util.Objects;

public class CrownItem extends Item implements ICurioItem {

  private static final ResourceLocation CROWN_TEXTURE = new ResourceLocation(Main.MODID,
      "textures/entity/crown.png");

  public CrownItem() {
    super(new Properties().tab(ItemGroup.TAB_TOOLS).stacksTo(1));
    this.setRegistryName(Main.MODID, "crown");
  }

  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT unused) {
    return CurioItemCapability.createProvider(new ICurio() {
      private Object model;

      @Override
      public void curioTick(String identifier, int index, LivingEntity livingEntity) { //TODO give empty crown also a texture

        if (!livingEntity.getEntity().level.isClientSide && livingEntity.tickCount % 20 == 0) {
          livingEntity
              .addEffect(new EffectInstance(Effects.NIGHT_VISION, 300, -1, true, true));
          stack.hurtAndBreak(1, livingEntity,
              damager -> CuriosApi.getCuriosHelper().onBrokenCurio(identifier, index, damager));
        }
      }


      @Override
      public boolean canRender(String identifier, int index, LivingEntity livingEntity) {
        return true;
      }

      @Override
      public void render(String identifier, int index, MatrixStack matrixStack,
                         IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity,
                         float limbSwing, float limbSwingAmount, float partialTicks,
                         float ageInTicks, float netHeadYaw, float headPitch) {

        if (!(this.model instanceof CrownModel)) {
          model = new CrownModel<>();
        }
        CrownModel<?> crown = (CrownModel<?>) this.model;
        ICurio.RenderHelper.followHeadRotations(livingEntity, crown.crown);
        IVertexBuilder vertexBuilder = ItemRenderer
            .getFoilBuffer(renderTypeBuffer, crown.renderType(CROWN_TEXTURE), false,
                stack.hasFoil());
        crown.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F,
            1.0F);
      }
    });
  }

  @Override
  public boolean isFoil(@Nonnull ItemStack stack) {
    return true;
  }
}

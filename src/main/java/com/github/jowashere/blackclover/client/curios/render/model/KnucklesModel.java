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

package com.github.jowashere.blackclover.client.curios.render.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

import javax.annotation.Nonnull;

public class KnucklesModel<T extends LivingEntity> extends BipedModel<T>{

  public KnucklesModel() {
    super(1.0F);
    this.texWidth = 16;
    this.texHeight = 16;
    this.rightArm = new ModelRenderer(this, 0, 0);
    this.rightArm.setPos(-5.0F, 2.0F, 0.0F);
    this.rightArm.addBox(-3.0F, 9.0F, -2.0F, 2, 1, 4, 0.4F);
    this.leftArm = new ModelRenderer(this, 0, 0);
    this.leftArm.mirror = true;
    this.leftArm.setPos(5.0F, 2.0F, 0.0F);
    this.leftArm.addBox(1.0F, 9.0F, -2.0F, 2, 1, 4, 0.4F);
  }


  @Override
  public void renderToBuffer(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
    this.rightArm.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_);
    this.leftArm.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_);
  }
  @Override
  public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

  }
  public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    modelRenderer.xRot = x;
    modelRenderer.yRot = y;
    modelRenderer.zRot = z;
  }
}

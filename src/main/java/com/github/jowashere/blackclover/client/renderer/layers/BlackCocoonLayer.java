package com.github.jowashere.blackclover.client.renderer.layers;

import com.github.jowashere.blackclover.client.renderer.layers.models.BlackCocoonModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public class BlackCocoonLayer<T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {

    private static final ResourceLocation resourcelocation = new ResourceLocation("blackclover:textures/entities/layers/spells/blackcocoon.png");
    private BlackCocoonModel model = new BlackCocoonModel();

    public BlackCocoonLayer(IEntityRenderer<T, M> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entitylivingbaseIn.isInvisible() && entitylivingbaseIn instanceof PlayerEntity) {

            AbstractClientPlayerEntity player = (AbstractClientPlayerEntity) entitylivingbaseIn;

            boolean blackcocoon = player.getPersistentData().getBoolean("blackclover_black_cocoon");

            if (blackcocoon) {
                matrixStackIn.pushPose();
                this.model.setupAnim(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                IVertexBuilder ivertexbuilder = ItemRenderer.getFoilBuffer(bufferIn, this.model.renderType(resourcelocation), false, false);
                this.model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                matrixStackIn.popPose();
            }
        }
    }

}

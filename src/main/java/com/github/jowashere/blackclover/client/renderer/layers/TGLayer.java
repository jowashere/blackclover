package com.github.jowashere.blackclover.client.renderer.layers;

import com.github.jowashere.blackclover.client.renderer.layers.models.ThunderGodGearModel;
import com.github.jowashere.blackclover.entities.mobs.BCEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public class TGLayer <T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {

    private static final ResourceLocation resourcelocation = new ResourceLocation("blackclover:textures/entities/layers/spells/thundergodgear.png");

    private ThunderGodGearModel model = new ThunderGodGearModel();

    public TGLayer(IEntityRenderer<T, M> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        boolean playerOrBC = entitylivingbaseIn instanceof PlayerEntity || entitylivingbaseIn instanceof BCEntity;

        if (!entitylivingbaseIn.isInvisible() && playerOrBC) {

            boolean gloves = entitylivingbaseIn.getPersistentData().getBoolean("blackclover_tg_gloves");
            boolean boots = entitylivingbaseIn.getPersistentData().getBoolean("blackclover_tg_boots");

            model.showBoots = boots;
            model.showGloves = gloves;

            if (gloves || boots) {
                matrixStackIn.pushPose();
                this.getParentModel().copyPropertiesTo(this.model);
                this.model.setupAnim(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.eyes(resourcelocation));
                this.model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                matrixStackIn.popPose();
            }
        }
    }

}

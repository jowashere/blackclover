package com.github.jowashere.blackclover.client.renderer.layers;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.client.renderer.layers.models.ModelGrimoire;
import com.github.jowashere.blackclover.entities.mobs.BCEntity;
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
import net.minecraftforge.common.util.LazyOptional;

public class GrimoireLayer <T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {

    private static final ResourceLocation resourcelocation = new ResourceLocation(Main.MODID, "textures/entity/layer/scroll.png");
    private ModelGrimoire grimoireModel = new ModelGrimoire();

    public GrimoireLayer(IEntityRenderer<T, M> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        boolean playerOrBC = entitylivingbaseIn instanceof PlayerEntity || entitylivingbaseIn instanceof BCEntity;

        if (!entitylivingbaseIn.isInvisible() && playerOrBC) {

            boolean shouldShow;
            String textLoc;

            if (entitylivingbaseIn instanceof PlayerEntity) {
                AbstractClientPlayerEntity player = (AbstractClientPlayerEntity) entitylivingbaseIn;

                LazyOptional<IPlayerHandler> playerc = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                IPlayerHandler player_cap = playerc.orElse(new PlayerCapability());

                shouldShow = player_cap.returnHasGrimoire() && player_cap.returnSpellModeToggle();
                textLoc = player_cap.getGrimoireTexture();

            } else {
                BCEntity entity = (BCEntity) entitylivingbaseIn;

                shouldShow = entity.getTarget() != null && entity.getMagicLevel() > 5;
                textLoc = entity.getGrimoireTexLoc();

            }


            if (!shouldShow)
                return;

            matrixStackIn.pushPose();
            this.getParentModel().copyPropertiesTo(this.grimoireModel);
            this.grimoireModel.setupAnim(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            IVertexBuilder ivertexbuilder = ItemRenderer.getFoilBuffer(bufferIn, this.grimoireModel.renderType(new ResourceLocation(Main.MODID, textLoc)), false, false);
            this.grimoireModel.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStackIn.popPose();
        }
    }

}

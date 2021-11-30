package com.github.jowashere.blackclover.client.renderer.spells.others;

import com.github.jowashere.blackclover.entities.spells.darkness.BlackHoleEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class BlackHoleRenderer extends EntityRenderer<BlackHoleEntity>{

    private static final ResourceLocation texture = new ResourceLocation("blackclover:textures/entities/projectiles/avidyaslash.png");

    public BlackHoleRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public void render(BlackHoleEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        IVertexBuilder vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.yRotO, entityIn.yRot) - 90));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(90 + MathHelper.lerp(partialTicks, entityIn.xRotO, entityIn.xRot)));
        EntityModel model = new BlackHoleModel();
        model.renderToBuffer(matrixStackIn, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.0625f);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    public static class Factory implements IRenderFactory<BlackHoleEntity> {

        @Override
        public EntityRenderer<? super BlackHoleEntity> createRenderFor(EntityRendererManager manager) {
            return new BlackHoleRenderer(manager);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(BlackHoleEntity entity) {
        return texture;
    }

    public class BlackHoleModel extends EntityModel<Entity> {

        private final ModelRenderer bb_main;

        public BlackHoleModel() {
            texWidth = 16;
            texHeight = 16;

            bb_main = new ModelRenderer(this);
            bb_main.setPos(0.0F, 24.0F, 0.0F);
            bb_main.texOffs(0, 0).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
        }

        @Override
        public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
            //previously the render function, render code was moved to a method below
        }

        @Override
        public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
            bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
        }

        public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
            modelRenderer.xRot = x;
            modelRenderer.yRot = y;
            modelRenderer.zRot = z;
        }
    }

}

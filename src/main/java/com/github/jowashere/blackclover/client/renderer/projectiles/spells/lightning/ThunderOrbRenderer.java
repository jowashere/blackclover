package com.github.jowashere.blackclover.client.renderer.projectiles.spells.lightning;

import com.github.jowashere.blackclover.entities.projectiles.spells.lightning.ThunderOrbEntity;
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
public class ThunderOrbRenderer extends EntityRenderer<ThunderOrbEntity>{

    private static final ResourceLocation texture = new ResourceLocation("blackclover:textures/entities/projectiles/crumblingorb.png");

    public ThunderOrbRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public void render(ThunderOrbEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        IVertexBuilder vb = bufferIn.getBuffer(RenderType.eyes(texture));
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.yRotO, entityIn.yRot) - 90));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(90 + MathHelper.lerp(partialTicks, entityIn.xRotO, entityIn.xRot)));
        EntityModel model = new ModelCrumblingOrb();
        model.renderToBuffer(matrixStackIn, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.0625f);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    public static class Factory implements IRenderFactory<ThunderOrbEntity> {

        @Override
        public EntityRenderer<? super ThunderOrbEntity> createRenderFor(EntityRendererManager manager) {
            return new ThunderOrbRenderer(manager);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(ThunderOrbEntity entity) {
        return texture;
    }

    private class ModelCrumblingOrb extends EntityModel<Entity> {
        private final ModelRenderer bb_main;
        private final ModelRenderer cube_r1;
        public ModelCrumblingOrb() {
            texWidth = 32;
            texHeight = 32;
            bb_main = new ModelRenderer(this);
            bb_main.setPos(0.0F, 24.0F, 0.0F);

            cube_r1 = new ModelRenderer(this);
            cube_r1.setPos(0.0F, -8.0F, 0.0F);
            bb_main.addChild(cube_r1);
            setRotationAngle(cube_r1, -0.7854F, -0.7854F, 0.7854F);
            cube_r1.texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
        }

        @Override
        public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
                           float alpha) {
            bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
        }

        public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
            modelRenderer.xRot = x;
            modelRenderer.yRot = y;
            modelRenderer.zRot = z;
        }

        @Override
        public void setupAnim(Entity e, float f, float f1, float f2, float f3, float f4) {
        }
    }
}

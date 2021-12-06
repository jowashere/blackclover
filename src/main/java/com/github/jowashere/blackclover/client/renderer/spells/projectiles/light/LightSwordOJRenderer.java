package com.github.jowashere.blackclover.client.renderer.spells.projectiles.light;

import com.github.jowashere.blackclover.entities.spells.light.LightSwordOJEntity;
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
public class LightSwordOJRenderer extends EntityRenderer<LightSwordOJEntity>{

    private static final ResourceLocation texture = new ResourceLocation("blackclover:textures/entities/projectiles/light_sword_oj.png");

    public LightSwordOJRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public void render(LightSwordOJEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        IVertexBuilder vb = bufferIn.getBuffer(RenderType.eyes(texture));
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.yRotO, entityIn.yRot) - 90));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(90 + MathHelper.lerp(partialTicks, entityIn.xRotO, entityIn.xRot)));
        EntityModel model = new LightSwordOJModel();
        model.renderToBuffer(matrixStackIn, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.0625f);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    public static class Factory implements IRenderFactory<LightSwordOJEntity> {

        @Override
        public EntityRenderer<? super LightSwordOJEntity> createRenderFor(EntityRendererManager manager) {
            return new LightSwordOJRenderer(manager);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(LightSwordOJEntity entity) {
        return texture;
    }

    private class LightSwordOJModel extends EntityModel<Entity> {

        private final ModelRenderer bone;
        private final ModelRenderer cube_r1;
        private final ModelRenderer cube_r2;

        public LightSwordOJModel() {
            texWidth = 48;
            texHeight = 48;

            bone = new ModelRenderer(this);
            bone.setPos(0.0F, 24.0F, 0.0F);
            setRotationAngle(bone, 0.0F, -1.5708F, 0.0F);
            bone.texOffs(0, 23).addBox(-1.0F, -12.5F, -1.0F, 2.0F, 7.0F, 2.0F, -0.5F, false);
            bone.texOffs(9, 23).addBox(-1.0F, -17.25F, -1.0F, 2.0F, 6.0F, 2.0F, -0.6F, false);
            bone.texOffs(18, 23).addBox(-1.0F, -20.75F, -1.0F, 2.0F, 5.0F, 2.0F, -0.7F, false);
            bone.texOffs(9, 32).addBox(-1.0F, -22.25F, -1.0F, 2.0F, 3.0F, 2.0F, -0.8F, false);
            bone.texOffs(0, 32).addBox(-1.0F, -7.25F, -1.0F, 2.0F, 4.0F, 2.0F, -0.6F, false);
            bone.texOffs(9, 38).addBox(-1.0F, -4.75F, -1.0F, 2.0F, 3.0F, 2.0F, -0.7F, false);
            bone.texOffs(0, 38).addBox(-1.0F, -3.25F, -1.0F, 2.0F, 2.0F, 2.0F, -0.8F, false);
            bone.texOffs(0, 0).addBox(-8.0F, -22.5F, -0.2F, 16.0F, 22.0F, 0.0F, -0.25F, false);

            cube_r1 = new ModelRenderer(this);
            cube_r1.setPos(0.0071F, -21.95F, 0.05F);
            bone.addChild(cube_r1);
            setRotationAngle(cube_r1, 0.0F, 0.0F, 0.7854F);
            cube_r1.texOffs(18, 32).addBox(-0.75F, -0.75F, -1.05F, 2.0F, 2.0F, 2.0F, -0.75F, false);

            cube_r2 = new ModelRenderer(this);
            cube_r2.setPos(0.0F, -1.25F, 0.5F);
            bone.addChild(cube_r2);
            setRotationAngle(cube_r2, 0.0F, 0.0F, 0.7854F);
            cube_r2.texOffs(18, 38).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, -0.25F, false);
        }

        @Override
        public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
            bone.render(matrixStack, buffer, packedLight, packedOverlay);
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

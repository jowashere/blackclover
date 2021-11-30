package com.github.jowashere.blackclover.client.renderer.spells.projectiles.slash;

import com.github.jowashere.blackclover.entities.spells.slash.DeathScytheEntity;
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
public class DeathScytheRenderer extends EntityRenderer<DeathScytheEntity>{

    private static final ResourceLocation texture = new ResourceLocation("blackclover:textures/entities/projectiles/windblade.png");

    public DeathScytheRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public void render(DeathScytheEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        IVertexBuilder vb = bufferIn.getBuffer(RenderType.eyes(texture));
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.yRotO, entityIn.yRot) - 90));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(90 + MathHelper.lerp(partialTicks, entityIn.xRotO, entityIn.xRot)));
        EntityModel model = new ModelDeathScythe();
        model.renderToBuffer(matrixStackIn, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.0625f);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    public static class Factory implements IRenderFactory<DeathScytheEntity> {

        @Override
        public EntityRenderer<? super DeathScytheEntity> createRenderFor(EntityRendererManager manager) {
            return new DeathScytheRenderer(manager);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(DeathScytheEntity entity) {
        return texture;
    }

    private class ModelDeathScythe extends EntityModel<Entity> {
        private final ModelRenderer bone;
        private final ModelRenderer cube_r1;
        private final ModelRenderer cube_r2;
        private final ModelRenderer cube_r3;
        private final ModelRenderer cube_r4;
        private final ModelRenderer cube_r5;
        public ModelDeathScythe() {
            texWidth = 16;
            texHeight = 16;

            bone = new ModelRenderer(this);
            bone.setPos(-6.1294F, 19.983F, 0.5F);


            cube_r1 = new ModelRenderer(this);
            cube_r1.setPos(0.6192F, -4.1326F, 0.0F);
            bone.addChild(cube_r1);
            setRotationAngle(cube_r1, 0.0F, 0.0F, 1.5708F);
            cube_r1.texOffs(3, 4).addBox(-1.0F, -9.1F, -0.5F, 2.0F, 7.0F, 1.0F, 0.0F, false);

            cube_r2 = new ModelRenderer(this);
            cube_r2.setPos(2.0743F, -3.339F, 0.0F);
            bone.addChild(cube_r2);
            setRotationAngle(cube_r2, 0.0F, 0.0F, 0.7854F);
            cube_r2.texOffs(3, 4).addBox(-0.7165F, -1.8F, -0.5F, 2.0F, 4.0F, 1.0F, 0.0F, false);

            cube_r3 = new ModelRenderer(this);
            cube_r3.setPos(10.701F, -3.339F, 0.0F);
            bone.addChild(cube_r3);
            setRotationAngle(cube_r3, 0.0F, 0.0F, -0.7854F);
            cube_r3.texOffs(3, 4).addBox(-1.5F, -2.0F, -0.5F, 2.0F, 4.0F, 1.0F, 0.0F, false);

            cube_r4 = new ModelRenderer(this);
            cube_r4.setPos(12.2588F, 0.0F, 0.0F);
            bone.addChild(cube_r4);
            setRotationAngle(cube_r4, 0.0F, 0.0F, -0.48F);
            cube_r4.texOffs(3, 4).addBox(-0.8F, -2.0F, -0.5F, 2.0F, 4.0F, 1.0F, 0.0F, false);

            cube_r5 = new ModelRenderer(this);
            cube_r5.setPos(0.0F, 0.0F, 0.0F);
            bone.addChild(cube_r5);
            setRotationAngle(cube_r5, 0.0F, 0.0F, 0.48F);
            cube_r5.texOffs(3, 4).addBox(-1.0F, -2.0F, -0.5F, 2.0F, 4.0F, 1.0F, 0.0F, false);
        }

        @Override
        public void setupAnim(Entity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

        }

        @Override
        public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
                           float alpha) {
            bone.render(matrixStack, buffer, packedLight, packedOverlay);
        }

        public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
            modelRenderer.xRot = x;
            modelRenderer.yRot = y;
            modelRenderer.zRot = z;
        }
    }
}

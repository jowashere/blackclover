package com.github.jowashere.blackclover.client.renderer.spells.projectiles.wind;

import com.github.jowashere.blackclover.entities.spells.wind.WindCrescentEntity;
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
public class WindCrescentRenderer extends EntityRenderer<WindCrescentEntity>{

    private static final ResourceLocation texture = new ResourceLocation("blackclover:textures/entities/projectiles/windblade.png");

    public WindCrescentRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public void render(WindCrescentEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        IVertexBuilder vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.yRotO, entityIn.yRot) - 90));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(90 + MathHelper.lerp(partialTicks, entityIn.xRotO, entityIn.xRot)));
        EntityModel model = new CresentSlash();
        model.renderToBuffer(matrixStackIn, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.0625f);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    public static class Factory implements IRenderFactory<WindCrescentEntity> {

        @Override
        public EntityRenderer<? super WindCrescentEntity> createRenderFor(EntityRendererManager manager) {
            return new WindCrescentRenderer(manager);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(WindCrescentEntity entity) {
        return texture;
    }

    public class CresentSlash extends EntityModel<Entity> {

        private final ModelRenderer bone;
        private final ModelRenderer cube_r1;
        private final ModelRenderer cube_r2;

        public CresentSlash() {
            texWidth = 16;
            texHeight = 16;

            bone = new ModelRenderer(this);
            bone.setPos(-0.5F, 13.256F, -0.1F);
            setRotationAngle(bone, -1.5708F, 0.0F, 0.0F);
            bone.texOffs(0, 0).addBox(-0.5F, -10.256F, -9.4F, 1.0F, 20.0F, 5.0F, 0.0F, false);

            cube_r1 = new ModelRenderer(this);
            cube_r1.setPos(0.0F, 4.244F, -5.4F);
            bone.addChild(cube_r1);
            setRotationAngle(cube_r1, -0.4363F, 0.0F, 0.0F);
            cube_r1.texOffs(0, 0).addBox(-0.5F, -31.2319F, -9.6F, 1.0F, 20.0F, 5.0F, 0.0F, false);

            cube_r2 = new ModelRenderer(this);
            cube_r2.setPos(0.0F, 5.244F, -5.4F);
            bone.addChild(cube_r2);
            setRotationAngle(cube_r2, 0.4363F, 0.0F, 0.0F);
            cube_r2.texOffs(0, 0).addBox(-0.5F, 2.5F, -5.5F, 1.0F, 20.0F, 5.0F, 0.0F, false);
        }

        @Override
        public void setupAnim(Entity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

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

        public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {
        }
    }

}

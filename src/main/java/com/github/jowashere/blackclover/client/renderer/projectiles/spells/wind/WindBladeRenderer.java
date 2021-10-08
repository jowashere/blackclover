package com.github.jowashere.blackclover.client.renderer.projectiles.spells.wind;

import com.github.jowashere.blackclover.entities.projectiles.spells.wind.WindBladeEntity;
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
public class WindBladeRenderer extends EntityRenderer<WindBladeEntity>{

    private static final ResourceLocation texture = new ResourceLocation("blackclover:textures/entities/projectiles/windblade.png");

    public WindBladeRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public void render(WindBladeEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        IVertexBuilder vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.yRotO, entityIn.yRot) - 90));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(90 + MathHelper.lerp(partialTicks, entityIn.xRotO, entityIn.xRot)));
        EntityModel model = new Modelwindblade_model();
        model.renderToBuffer(matrixStackIn, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.0625f);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    public static class Factory implements IRenderFactory<WindBladeEntity> {

        @Override
        public EntityRenderer<? super WindBladeEntity> createRenderFor(EntityRendererManager manager) {
            return new WindBladeRenderer(manager);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(WindBladeEntity entity) {
        return texture;
    }

    private class Modelwindblade_model extends EntityModel<Entity> {
        private final ModelRenderer sword;
        private final ModelRenderer cube_r1;
        private final ModelRenderer cube_r2;
        private final ModelRenderer cube_r3;

        public Modelwindblade_model() {
            texWidth = 16;
            texHeight = 16;
            sword = new ModelRenderer(this);
            sword.setPos(0.0F, 22.75F, 0.0F);
            sword.texOffs(0, 0).addBox(-1.0F, -6.5F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
            sword.texOffs(0, 0).addBox(-1.0F, -14.0F, -2.0F, 2.0F, 8.0F, 4.0F, 0.0F, false);
            sword.texOffs(0, 0).addBox(-1.0F, -14.85F, -0.91F, 2.0F, 1.0F, 2.0F, 0.0F, false);
            cube_r1 = new ModelRenderer(this);
            cube_r1.setPos(-0.5F, -14.5F, 0.5F);
            sword.addChild(cube_r1);
            setRotationAngle(cube_r1, -0.7854F, 0.0F, 0.0F);
            cube_r1.texOffs(0, 0).addBox(-0.5F, -0.7F, -1.39F, 2.0F, 1.0F, 1.0F, 0.0F, false);
            cube_r1.texOffs(0, 0).addBox(-0.5F, 0.1F, -1.4F, 2.0F, 2.0F, 1.0F, 0.0F, false);
            cube_r2 = new ModelRenderer(this);
            cube_r2.setPos(-0.5F, -14.5F, 0.5F);
            sword.addChild(cube_r2);
            setRotationAngle(cube_r2, 0.7854F, 0.0F, 0.0F);
            cube_r2.texOffs(0, 0).addBox(-0.5F, -0.6F, -0.31F, 2.0F, 2.0F, 1.0F, 0.0F, false);
            cube_r3 = new ModelRenderer(this);
            cube_r3.setPos(-0.5F, -1.5F, 0.0F);
            sword.addChild(cube_r3);
            setRotationAngle(cube_r3, -0.7854F, 0.0F, 0.0F);
            cube_r3.texOffs(0, 0).addBox(-0.5F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, 0.0F, false);
        }

        @Override
        public void setupAnim(Entity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

        }

        @Override
        public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
                                   float alpha) {
            sword.render(matrixStack, buffer, packedLight, packedOverlay);
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

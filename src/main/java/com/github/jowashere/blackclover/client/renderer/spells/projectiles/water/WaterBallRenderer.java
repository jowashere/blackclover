package com.github.jowashere.blackclover.client.renderer.spells.projectiles.water;// Made with Blockbench 4.1.1
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.github.jowashere.blackclover.client.renderer.spells.projectiles.wind.WindBladeRenderer;
import com.github.jowashere.blackclover.entities.spells.water.WaterBallEntity;
import com.github.jowashere.blackclover.entities.spells.wind.WindBladeEntity;
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
public class WaterBallRenderer extends EntityRenderer<WaterBallEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	private static final ResourceLocation texture = new ResourceLocation("blackclover:textures/entities/projectiles/waterball.png");
	public WaterBallRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public void render(WaterBallEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		IVertexBuilder vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
		matrixStackIn.pushPose();
		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.yRotO, entityIn.yRot) - 90));
		matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(90 + MathHelper.lerp(partialTicks, entityIn.xRotO, entityIn.xRot)));
		EntityModel model = new Modelwaterball_model();
		model.renderToBuffer(matrixStackIn, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.0625f);
		matrixStackIn.popPose();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	public static class Factory implements IRenderFactory<WaterBallEntity> {

		@Override
		public EntityRenderer<? super WaterBallEntity> createRenderFor(EntityRendererManager manager) {
			return new WaterBallRenderer(manager);
		}
	}

	@Override
	public ResourceLocation getTextureLocation(WaterBallEntity entity) {
		return texture;
	}

	private class Modelwaterball_model extends EntityModel<Entity>
	{
		private final ModelRenderer bone9;
		private final ModelRenderer bone7;
		private final ModelRenderer cube_r1;
		private final ModelRenderer cube_r2;
		private final ModelRenderer cube_r3;
		private final ModelRenderer cube_r4;
		private final ModelRenderer cube_r5;
		private final ModelRenderer cube_r6;
		private final ModelRenderer bone2;
		private final ModelRenderer cube_r7;
		private final ModelRenderer cube_r8;
		private final ModelRenderer cube_r9;
		private final ModelRenderer cube_r10;
		private final ModelRenderer cube_r11;
		private final ModelRenderer cube_r12;

		public Modelwaterball_model() {
			texWidth = 80;
			texHeight = 80;

			bone9 = new ModelRenderer(this);
			bone9.setPos(0.0F, 18.0F, -1.0F);
			bone9.texOffs(0, 0).addBox(0.0F, -6.0F, -6.0F, 0.0F, 12.0F, 12.0F, 0.0F, false);

			bone7 = new ModelRenderer(this);
			bone7.setPos(0.5F, 3.0F, 14.0F);
			bone9.addChild(bone7);
			bone7.texOffs(39, 32).addBox(1.7071F, -2.7071F, -17.0F, 1.0F, 2.0F, 5.0F, 0.0F, false);
			bone7.texOffs(17, 25).addBox(0.0F, -6.0F, -17.0F, 2.0F, 1.0F, 5.0F, 0.0F, false);
			bone7.texOffs(9, 58).addBox(0.0F, -5.2929F, -12.2929F, 2.0F, 2.0F, 1.0F, 0.0F, false);
			bone7.texOffs(13, 32).addBox(1.7071F, -5.2929F, -17.0F, 1.0F, 3.0F, 5.0F, 0.0F, false);
			bone7.texOffs(36, 53).addBox(0.0F, -3.7071F, -17.7071F, 2.0F, 3.0F, 1.0F, 0.0F, false);
			bone7.texOffs(18, 58).addBox(0.0F, -5.2929F, -17.7071F, 2.0F, 2.0F, 1.0F, 0.0F, false);
			bone7.texOffs(34, 25).addBox(0.0F, -1.0F, -17.0F, 2.0F, 1.0F, 5.0F, 0.0F, false);
			bone7.texOffs(27, 53).addBox(0.0F, -3.7071F, -12.2929F, 2.0F, 3.0F, 1.0F, 0.0F, false);

			cube_r1 = new ModelRenderer(this);
			cube_r1.setPos(1.5F, -0.7071F, -12.0F);
			bone7.addChild(cube_r1);
			setRotationAngle(cube_r1, -0.7854F, 0.0F, 0.0F);
			cube_r1.texOffs(0, 62).addBox(-1.5F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);

			cube_r2 = new ModelRenderer(this);
			cube_r2.setPos(1.5F, -5.2929F, -12.0F);
			bone7.addChild(cube_r2);
			setRotationAngle(cube_r2, 0.7854F, 0.0F, 0.0F);
			cube_r2.texOffs(9, 62).addBox(-1.5F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);

			cube_r3 = new ModelRenderer(this);
			cube_r3.setPos(1.5F, -0.7071F, -17.0F);
			bone7.addChild(cube_r3);
			setRotationAngle(cube_r3, 0.7854F, 0.0F, 0.0F);
			cube_r3.texOffs(18, 62).addBox(-1.5F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);

			cube_r4 = new ModelRenderer(this);
			cube_r4.setPos(1.5F, -5.2929F, -17.0F);
			bone7.addChild(cube_r4);
			setRotationAngle(cube_r4, -0.7854F, 0.0F, 0.0F);
			cube_r4.texOffs(27, 62).addBox(-1.5F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);

			cube_r5 = new ModelRenderer(this);
			cube_r5.setPos(2.0F, 0.0F, -14.5F);
			bone7.addChild(cube_r5);
			setRotationAngle(cube_r5, 0.0F, 0.0F, -0.7854F);
			cube_r5.texOffs(26, 41).addBox(0.0F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, 0.0F, false);

			cube_r6 = new ModelRenderer(this);
			cube_r6.setPos(2.0F, -6.0F, -14.5F);
			bone7.addChild(cube_r6);
			setRotationAngle(cube_r6, 0.0F, 0.0F, 0.7854F);
			cube_r6.texOffs(39, 41).addBox(0.0F, 0.0F, -2.5F, 1.0F, 1.0F, 5.0F, 0.0F, false);

			bone2 = new ModelRenderer(this);
			bone2.setPos(-0.5F, 3.0F, 14.0F);
			bone9.addChild(bone2);
			bone2.texOffs(39, 32).addBox(-2.7071F, -2.7071F, -17.0F, 1.0F, 2.0F, 5.0F, 0.0F, true);
			bone2.texOffs(17, 25).addBox(-2.0F, -6.0F, -17.0F, 3.0F, 1.0F, 5.0F, 0.0F, true);
			bone2.texOffs(9, 58).addBox(-2.0F, -5.2929F, -12.2929F, 3.0F, 2.0F, 1.0F, 0.0F, true);
			bone2.texOffs(13, 32).addBox(-2.7071F, -5.2929F, -17.0F, 1.0F, 3.0F, 5.0F, 0.0F, true);
			bone2.texOffs(36, 53).addBox(-2.0F, -3.7071F, -17.7071F, 3.0F, 3.0F, 1.0F, 0.0F, true);
			bone2.texOffs(18, 58).addBox(-2.0F, -5.2929F, -17.7071F, 3.0F, 2.0F, 1.0F, 0.0F, true);
			bone2.texOffs(34, 25).addBox(-2.0F, -1.0F, -17.0F, 3.0F, 1.0F, 5.0F, 0.0F, true);
			bone2.texOffs(27, 53).addBox(-2.0F, -3.7071F, -12.2929F, 3.0F, 3.0F, 1.0F, 0.0F, true);

			cube_r7 = new ModelRenderer(this);
			cube_r7.setPos(-1.5F, -0.7071F, -12.0F);
			bone2.addChild(cube_r7);
			setRotationAngle(cube_r7, -0.7854F, 0.0F, 0.0F);
			cube_r7.texOffs(0, 62).addBox(-0.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, 0.0F, true);

			cube_r8 = new ModelRenderer(this);
			cube_r8.setPos(-1.5F, -5.2929F, -12.0F);
			bone2.addChild(cube_r8);
			setRotationAngle(cube_r8, 0.7854F, 0.0F, 0.0F);
			cube_r8.texOffs(9, 62).addBox(-0.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, 0.0F, true);

			cube_r9 = new ModelRenderer(this);
			cube_r9.setPos(-1.5F, -0.7071F, -17.0F);
			bone2.addChild(cube_r9);
			setRotationAngle(cube_r9, 0.7854F, 0.0F, 0.0F);
			cube_r9.texOffs(18, 62).addBox(-0.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, 0.0F, true);

			cube_r10 = new ModelRenderer(this);
			cube_r10.setPos(-1.5F, -5.2929F, -17.0F);
			bone2.addChild(cube_r10);
			setRotationAngle(cube_r10, -0.7854F, 0.0F, 0.0F);
			cube_r10.texOffs(27, 62).addBox(-0.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, 0.0F, true);

			cube_r11 = new ModelRenderer(this);
			cube_r11.setPos(-2.0F, 0.0F, -14.5F);
			bone2.addChild(cube_r11);
			setRotationAngle(cube_r11, 0.0F, 0.0F, 0.7854F);
			cube_r11.texOffs(26, 41).addBox(-1.0F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, 0.0F, true);

			cube_r12 = new ModelRenderer(this);
			cube_r12.setPos(-2.0F, -6.0F, -14.5F);
			bone2.addChild(cube_r12);
			setRotationAngle(cube_r12, 0.0F, 0.0F, -0.7854F);
			cube_r12.texOffs(39, 41).addBox(-1.0F, 0.0F, -2.5F, 1.0F, 1.0F, 5.0F, 0.0F, true);
	}

		@Override
		public void setupAnim(Entity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

		}

		@Override
		public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
								   float alpha) {
			bone9.render(matrixStack, buffer, packedLight, packedOverlay);
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
package com.github.jowashere.blackclover.client.renderer.spells.others;// Made with Blockbench 4.1.5
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.github.jowashere.blackclover.client.renderer.spells.projectiles.water.WaterSpearRenderer;
import com.github.jowashere.blackclover.entities.spells.water.WaterDomeEntity;
import com.github.jowashere.blackclover.entities.spells.water.WaterSpearEntity;
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
public class WaterDomeRenderer extends EntityRenderer<WaterDomeEntity> {

	private static final ResourceLocation texture = new ResourceLocation("blackclover:textures/entities/projectiles/waterdome.png");
	public WaterDomeRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
	}


	@Override
	public void render(WaterDomeEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		IVertexBuilder vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
		matrixStackIn.pushPose();
		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.yRotO, entityIn.yRot) - 90));
		matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(90 + MathHelper.lerp(partialTicks, entityIn.xRotO, entityIn.xRot)));
		EntityModel model = new Waterdome_model();
		model.renderToBuffer(matrixStackIn, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.0625f);
		matrixStackIn.popPose();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	public static class Factory implements IRenderFactory<WaterDomeEntity> {

		@Override
		public EntityRenderer<? super WaterDomeEntity> createRenderFor(EntityRendererManager manager) {
			return new WaterDomeRenderer(manager);
		}
	}

	@Override
	public ResourceLocation getTextureLocation(WaterDomeEntity entity) {
		return texture;
	}


	public class Waterdome_model extends EntityModel<Entity> {
		private final ModelRenderer WaterDomeMain;
		private final ModelRenderer WaterDome1;
		private final ModelRenderer WaterDome2;
		private final ModelRenderer WaterDome3;
		private final ModelRenderer WaterDome4;
		private final ModelRenderer WaterDome5;
		private final ModelRenderer WaterDome6;
		private final ModelRenderer WaterDome7;
		private final ModelRenderer WaterDome8;
		private final ModelRenderer WaterDome9;
		private final ModelRenderer WaterDome10;

		public Waterdome_model() {
			texWidth = 16;
			texHeight = 16;

			WaterDomeMain = new ModelRenderer(this);
			WaterDomeMain.setPos(1.0F, 28.0F, -17.0F);


			WaterDome1 = new ModelRenderer(this);
			WaterDome1.setPos(0.0F, 0.0F, 0.0F);
			WaterDomeMain.addChild(WaterDome1);
			WaterDome1.texOffs(0, 0).addBox(-10.0F, -5.0F, -2.0F, 18.0F, 1.0F, 3.0F, 0.0F, false);
			WaterDome1.texOffs(0, 0).addBox(-10.0F, -9.0F, 1.0F, 18.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome1.texOffs(0, 0).addBox(-10.0F, -9.0F, 0.0F, 18.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome1.texOffs(0, 0).addBox(-10.0F, -9.0F, -1.0F, 18.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome1.texOffs(0, 0).addBox(-10.0F, -9.0F, -2.0F, 18.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome1.texOffs(0, 0).addBox(-3.0F, -6.0F, -2.0F, 11.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome1.texOffs(0, 0).addBox(-9.0F, -6.0F, -2.0F, 12.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome1.texOffs(0, 0).addBox(-7.0F, -6.0F, -1.0F, 11.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome1.texOffs(0, 0).addBox(-9.0F, -6.0F, 0.0F, 11.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome1.texOffs(0, 0).addBox(-6.0F, -6.0F, 0.0F, 11.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome1.texOffs(0, 0).addBox(-4.0F, -6.0F, -1.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome1.texOffs(0, 0).addBox(-3.0F, -6.0F, 0.0F, 11.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome1.texOffs(0, 0).addBox(-10.0F, -6.0F, -1.0F, 11.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome1.texOffs(0, 0).addBox(-5.0F, -6.0F, -2.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome1.texOffs(0, 0).addBox(-2.0F, -6.0F, -1.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome1.texOffs(0, 0).addBox(-6.0F, -7.0F, -1.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome1.texOffs(0, 0).addBox(-10.0F, -7.0F, -1.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome1.texOffs(0, 0).addBox(-8.0F, -7.0F, -2.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome1.texOffs(0, 0).addBox(-8.0F, -7.0F, 0.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome1.texOffs(0, 0).addBox(-6.0F, -7.0F, 0.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome1.texOffs(0, 0).addBox(-2.0F, -7.0F, 0.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);

			WaterDome2 = new ModelRenderer(this);
			WaterDome2.setPos(15.0F, 0.0F, 3.0F);
			WaterDomeMain.addChild(WaterDome2);
			WaterDome2.texOffs(0, 0).addBox(-10.0F, -5.0F, -2.0F, 8.0F, 1.0F, 3.0F, 0.0F, false);
			WaterDome2.texOffs(0, 0).addBox(-10.0F, -9.0F, 1.0F, 8.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome2.texOffs(0, 0).addBox(-10.0F, -9.0F, 0.0F, 8.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome2.texOffs(0, 0).addBox(-10.0F, -9.0F, -1.0F, 8.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome2.texOffs(0, 0).addBox(-10.0F, -9.0F, -2.0F, 8.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome2.texOffs(0, 0).addBox(-3.0F, -6.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome2.texOffs(0, 0).addBox(-9.0F, -6.0F, -2.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome2.texOffs(0, 0).addBox(-7.0F, -6.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome2.texOffs(0, 0).addBox(-9.0F, -6.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome2.texOffs(0, 0).addBox(-6.0F, -6.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome2.texOffs(0, 0).addBox(-4.0F, -6.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome2.texOffs(0, 0).addBox(-3.0F, -6.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome2.texOffs(0, 0).addBox(-10.0F, -6.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome2.texOffs(0, 0).addBox(-5.0F, -6.0F, -2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome2.texOffs(0, 0).addBox(-2.0F, -6.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome2.texOffs(0, 0).addBox(-6.0F, -7.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome2.texOffs(0, 0).addBox(-10.0F, -7.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome2.texOffs(0, 0).addBox(-8.0F, -7.0F, -2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome2.texOffs(0, 0).addBox(-8.0F, -7.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome2.texOffs(0, 0).addBox(-6.0F, -7.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome2.texOffs(0, 0).addBox(-2.0F, -7.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);

			WaterDome3 = new ModelRenderer(this);
			WaterDome3.setPos(-23.0F, 0.0F, 0.0F);
			WaterDomeMain.addChild(WaterDome3);
			WaterDome3.texOffs(0, 0).addBox(8.0F, -5.0F, 1.0F, 8.0F, 1.0F, 3.0F, 0.0F, false);
			WaterDome3.texOffs(0, 0).addBox(8.0F, -9.0F, 4.0F, 8.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome3.texOffs(0, 0).addBox(8.0F, -9.0F, 3.0F, 8.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome3.texOffs(0, 0).addBox(8.0F, -9.0F, 2.0F, 8.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome3.texOffs(0, 0).addBox(8.0F, -9.0F, 1.0F, 8.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome3.texOffs(0, 0).addBox(15.0F, -6.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome3.texOffs(0, 0).addBox(9.0F, -6.0F, 1.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome3.texOffs(0, 0).addBox(11.0F, -6.0F, 2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome3.texOffs(0, 0).addBox(9.0F, -6.0F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome3.texOffs(0, 0).addBox(12.0F, -6.0F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome3.texOffs(0, 0).addBox(14.0F, -6.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome3.texOffs(0, 0).addBox(15.0F, -6.0F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome3.texOffs(0, 0).addBox(8.0F, -6.0F, 2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome3.texOffs(0, 0).addBox(13.0F, -6.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome3.texOffs(0, 0).addBox(16.0F, -6.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome3.texOffs(0, 0).addBox(12.0F, -7.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome3.texOffs(0, 0).addBox(8.0F, -7.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome3.texOffs(0, 0).addBox(10.0F, -7.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome3.texOffs(0, 0).addBox(10.0F, -7.0F, 3.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome3.texOffs(0, 0).addBox(12.0F, -7.0F, 3.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome3.texOffs(0, 0).addBox(16.0F, -7.0F, 3.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);

			WaterDome4 = new ModelRenderer(this);
			WaterDome4.setPos(-14.0F, 0.0F, 19.0F);
			WaterDomeMain.addChild(WaterDome4);
			setRotationAngle(WaterDome4, 0.0F, -1.5708F, 0.0F);
			WaterDome4.texOffs(0, 0).addBox(-15.0F, -5.0F, 1.0F, 12.0F, 1.0F, 3.0F, 0.0F, false);
			WaterDome4.texOffs(0, 0).addBox(-15.0F, -9.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome4.texOffs(0, 0).addBox(-15.0F, -9.0F, 3.0F, 12.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome4.texOffs(0, 0).addBox(-15.0F, -9.0F, 2.0F, 12.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome4.texOffs(0, 0).addBox(-15.0F, -9.0F, 1.0F, 12.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome4.texOffs(0, 0).addBox(-8.0F, -6.0F, 1.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome4.texOffs(0, 0).addBox(-14.0F, -6.0F, 1.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome4.texOffs(0, 0).addBox(-12.0F, -6.0F, 2.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome4.texOffs(0, 0).addBox(-14.0F, -6.0F, 3.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome4.texOffs(0, 0).addBox(-11.0F, -6.0F, 3.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome4.texOffs(0, 0).addBox(-9.0F, -6.0F, 2.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome4.texOffs(0, 0).addBox(-8.0F, -6.0F, 3.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome4.texOffs(0, 0).addBox(-15.0F, -6.0F, 2.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome4.texOffs(0, 0).addBox(-10.0F, -6.0F, 1.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome4.texOffs(0, 0).addBox(-7.0F, -6.0F, 2.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome4.texOffs(0, 0).addBox(-11.0F, -7.0F, 2.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome4.texOffs(0, 0).addBox(-15.0F, -7.0F, 2.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome4.texOffs(0, 0).addBox(-13.0F, -7.0F, 1.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome4.texOffs(0, 0).addBox(-13.0F, -7.0F, 3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome4.texOffs(0, 0).addBox(-11.0F, -7.0F, 3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome4.texOffs(0, 0).addBox(-7.0F, -7.0F, 3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);

			WaterDome5 = new ModelRenderer(this);
			WaterDome5.setPos(-7.0F, 0.0F, -17.0F);
			WaterDomeMain.addChild(WaterDome5);
			setRotationAngle(WaterDome5, 0.0F, 1.5708F, 0.0F);
			WaterDome5.texOffs(0, 0).addBox(-33.0F, -5.0F, 20.0F, 12.0F, 1.0F, 3.0F, 0.0F, false);
			WaterDome5.texOffs(0, 0).addBox(-33.0F, -9.0F, 23.0F, 12.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome5.texOffs(0, 0).addBox(-33.0F, -9.0F, 22.0F, 12.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome5.texOffs(0, 0).addBox(-33.0F, -9.0F, 21.0F, 12.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome5.texOffs(0, 0).addBox(-33.0F, -9.0F, 20.0F, 12.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome5.texOffs(0, 0).addBox(-26.0F, -6.0F, 20.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome5.texOffs(0, 0).addBox(-32.0F, -6.0F, 20.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome5.texOffs(0, 0).addBox(-30.0F, -6.0F, 21.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome5.texOffs(0, 0).addBox(-32.0F, -6.0F, 22.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome5.texOffs(0, 0).addBox(-29.0F, -6.0F, 22.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome5.texOffs(0, 0).addBox(-27.0F, -6.0F, 21.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome5.texOffs(0, 0).addBox(-26.0F, -6.0F, 22.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome5.texOffs(0, 0).addBox(-33.0F, -6.0F, 21.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome5.texOffs(0, 0).addBox(-28.0F, -6.0F, 20.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome5.texOffs(0, 0).addBox(-25.0F, -6.0F, 21.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome5.texOffs(0, 0).addBox(-29.0F, -7.0F, 21.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome5.texOffs(0, 0).addBox(-33.0F, -7.0F, 21.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome5.texOffs(0, 0).addBox(-31.0F, -7.0F, 20.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome5.texOffs(0, 0).addBox(-31.0F, -7.0F, 22.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome5.texOffs(0, 0).addBox(-29.0F, -7.0F, 22.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome5.texOffs(0, 0).addBox(-25.0F, -7.0F, 22.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);

			WaterDome6 = new ModelRenderer(this);
			WaterDome6.setPos(0.0F, 0.0F, 33.0F);
			WaterDomeMain.addChild(WaterDome6);
			WaterDome6.texOffs(0, 0).addBox(-10.0F, -5.0F, -2.0F, 18.0F, 1.0F, 3.0F, 0.0F, false);
			WaterDome6.texOffs(0, 0).addBox(-10.0F, -9.0F, 1.0F, 18.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome6.texOffs(0, 0).addBox(-10.0F, -9.0F, 0.0F, 18.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome6.texOffs(0, 0).addBox(-10.0F, -9.0F, -1.0F, 18.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome6.texOffs(0, 0).addBox(-10.0F, -9.0F, -2.0F, 18.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome6.texOffs(0, 0).addBox(-3.0F, -6.0F, -2.0F, 11.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome6.texOffs(0, 0).addBox(-9.0F, -6.0F, -2.0F, 12.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome6.texOffs(0, 0).addBox(-7.0F, -6.0F, -1.0F, 11.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome6.texOffs(0, 0).addBox(-9.0F, -6.0F, 0.0F, 11.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome6.texOffs(0, 0).addBox(-6.0F, -6.0F, 0.0F, 11.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome6.texOffs(0, 0).addBox(-4.0F, -6.0F, -1.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome6.texOffs(0, 0).addBox(-3.0F, -6.0F, 0.0F, 11.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome6.texOffs(0, 0).addBox(-10.0F, -6.0F, -1.0F, 11.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome6.texOffs(0, 0).addBox(-5.0F, -6.0F, -2.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome6.texOffs(0, 0).addBox(-2.0F, -6.0F, -1.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome6.texOffs(0, 0).addBox(-6.0F, -7.0F, -1.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome6.texOffs(0, 0).addBox(-10.0F, -7.0F, -1.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome6.texOffs(0, 0).addBox(-8.0F, -7.0F, -2.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome6.texOffs(0, 0).addBox(-8.0F, -7.0F, 0.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome6.texOffs(0, 0).addBox(-6.0F, -7.0F, 0.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome6.texOffs(0, 0).addBox(-2.0F, -7.0F, 0.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);

			WaterDome7 = new ModelRenderer(this);
			WaterDome7.setPos(15.0F, 0.0F, 30.0F);
			WaterDomeMain.addChild(WaterDome7);
			WaterDome7.texOffs(0, 0).addBox(-10.0F, -5.0F, -2.0F, 8.0F, 1.0F, 3.0F, 0.0F, false);
			WaterDome7.texOffs(0, 0).addBox(-10.0F, -9.0F, 1.0F, 8.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome7.texOffs(0, 0).addBox(-10.0F, -9.0F, 0.0F, 8.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome7.texOffs(0, 0).addBox(-10.0F, -9.0F, -1.0F, 8.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome7.texOffs(0, 0).addBox(-10.0F, -9.0F, -2.0F, 8.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome7.texOffs(0, 0).addBox(-3.0F, -6.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome7.texOffs(0, 0).addBox(-9.0F, -6.0F, -2.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome7.texOffs(0, 0).addBox(-7.0F, -6.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome7.texOffs(0, 0).addBox(-9.0F, -6.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome7.texOffs(0, 0).addBox(-6.0F, -6.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome7.texOffs(0, 0).addBox(-4.0F, -6.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome7.texOffs(0, 0).addBox(-3.0F, -6.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome7.texOffs(0, 0).addBox(-10.0F, -6.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome7.texOffs(0, 0).addBox(-5.0F, -6.0F, -2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome7.texOffs(0, 0).addBox(-2.0F, -6.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome7.texOffs(0, 0).addBox(-6.0F, -7.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome7.texOffs(0, 0).addBox(-10.0F, -7.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome7.texOffs(0, 0).addBox(-8.0F, -7.0F, -2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome7.texOffs(0, 0).addBox(-8.0F, -7.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome7.texOffs(0, 0).addBox(-6.0F, -7.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome7.texOffs(0, 0).addBox(-2.0F, -7.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);

			WaterDome8 = new ModelRenderer(this);
			WaterDome8.setPos(-23.0F, 0.0F, 27.0F);
			WaterDomeMain.addChild(WaterDome8);
			WaterDome8.texOffs(0, 0).addBox(8.0F, -5.0F, 1.0F, 8.0F, 1.0F, 3.0F, 0.0F, false);
			WaterDome8.texOffs(0, 0).addBox(8.0F, -9.0F, 4.0F, 8.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome8.texOffs(0, 0).addBox(8.0F, -9.0F, 3.0F, 8.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome8.texOffs(0, 0).addBox(8.0F, -9.0F, 2.0F, 8.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome8.texOffs(0, 0).addBox(8.0F, -9.0F, 1.0F, 8.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome8.texOffs(0, 0).addBox(15.0F, -6.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome8.texOffs(0, 0).addBox(9.0F, -6.0F, 1.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome8.texOffs(0, 0).addBox(11.0F, -6.0F, 2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome8.texOffs(0, 0).addBox(9.0F, -6.0F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome8.texOffs(0, 0).addBox(12.0F, -6.0F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome8.texOffs(0, 0).addBox(14.0F, -6.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome8.texOffs(0, 0).addBox(15.0F, -6.0F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome8.texOffs(0, 0).addBox(8.0F, -6.0F, 2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome8.texOffs(0, 0).addBox(13.0F, -6.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome8.texOffs(0, 0).addBox(16.0F, -6.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome8.texOffs(0, 0).addBox(12.0F, -7.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome8.texOffs(0, 0).addBox(8.0F, -7.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome8.texOffs(0, 0).addBox(10.0F, -7.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome8.texOffs(0, 0).addBox(10.0F, -7.0F, 3.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome8.texOffs(0, 0).addBox(12.0F, -7.0F, 3.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome8.texOffs(0, 0).addBox(16.0F, -7.0F, 3.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);

			WaterDome9 = new ModelRenderer(this);
			WaterDome9.setPos(-14.0F, 0.0F, 35.0F);
			WaterDomeMain.addChild(WaterDome9);
			setRotationAngle(WaterDome9, 0.0F, -1.5708F, 0.0F);
			WaterDome9.texOffs(0, 0).addBox(-19.0F, -5.0F, 1.0F, 12.0F, 1.0F, 3.0F, 0.0F, false);
			WaterDome9.texOffs(0, 0).addBox(-19.0F, -9.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome9.texOffs(0, 0).addBox(-19.0F, -9.0F, 3.0F, 12.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome9.texOffs(0, 0).addBox(-19.0F, -9.0F, 2.0F, 12.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome9.texOffs(0, 0).addBox(-19.0F, -9.0F, 1.0F, 12.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome9.texOffs(0, 0).addBox(-12.0F, -6.0F, 1.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome9.texOffs(0, 0).addBox(-18.0F, -6.0F, 1.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome9.texOffs(0, 0).addBox(-16.0F, -6.0F, 2.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome9.texOffs(0, 0).addBox(-18.0F, -6.0F, 3.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome9.texOffs(0, 0).addBox(-15.0F, -6.0F, 3.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome9.texOffs(0, 0).addBox(-13.0F, -6.0F, 2.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome9.texOffs(0, 0).addBox(-12.0F, -6.0F, 3.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome9.texOffs(0, 0).addBox(-19.0F, -6.0F, 2.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome9.texOffs(0, 0).addBox(-14.0F, -6.0F, 1.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome9.texOffs(0, 0).addBox(-11.0F, -6.0F, 2.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome9.texOffs(0, 0).addBox(-15.0F, -7.0F, 2.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome9.texOffs(0, 0).addBox(-19.0F, -7.0F, 2.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome9.texOffs(0, 0).addBox(-17.0F, -7.0F, 1.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome9.texOffs(0, 0).addBox(-17.0F, -7.0F, 3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome9.texOffs(0, 0).addBox(-15.0F, -7.0F, 3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome9.texOffs(0, 0).addBox(-11.0F, -7.0F, 3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);

			WaterDome10 = new ModelRenderer(this);
			WaterDome10.setPos(-7.0F, 0.0F, -1.0F);
			WaterDomeMain.addChild(WaterDome10);
			setRotationAngle(WaterDome10, 0.0F, 1.5708F, 0.0F);
			WaterDome10.texOffs(0, 0).addBox(-29.0F, -5.0F, 20.0F, 12.0F, 1.0F, 3.0F, 0.0F, false);
			WaterDome10.texOffs(0, 0).addBox(-29.0F, -9.0F, 23.0F, 12.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome10.texOffs(0, 0).addBox(-29.0F, -9.0F, 22.0F, 12.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome10.texOffs(0, 0).addBox(-29.0F, -9.0F, 21.0F, 12.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome10.texOffs(0, 0).addBox(-29.0F, -9.0F, 20.0F, 12.0F, 4.0F, 0.0F, 0.0F, false);
			WaterDome10.texOffs(0, 0).addBox(-22.0F, -6.0F, 20.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome10.texOffs(0, 0).addBox(-28.0F, -6.0F, 20.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome10.texOffs(0, 0).addBox(-26.0F, -6.0F, 21.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome10.texOffs(0, 0).addBox(-28.0F, -6.0F, 22.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome10.texOffs(0, 0).addBox(-25.0F, -6.0F, 22.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome10.texOffs(0, 0).addBox(-23.0F, -6.0F, 21.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome10.texOffs(0, 0).addBox(-22.0F, -6.0F, 22.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome10.texOffs(0, 0).addBox(-29.0F, -6.0F, 21.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome10.texOffs(0, 0).addBox(-24.0F, -6.0F, 20.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome10.texOffs(0, 0).addBox(-21.0F, -6.0F, 21.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome10.texOffs(0, 0).addBox(-25.0F, -7.0F, 21.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome10.texOffs(0, 0).addBox(-29.0F, -7.0F, 21.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome10.texOffs(0, 0).addBox(-27.0F, -7.0F, 20.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome10.texOffs(0, 0).addBox(-27.0F, -7.0F, 22.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome10.texOffs(0, 0).addBox(-25.0F, -7.0F, 22.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
			WaterDome10.texOffs(0, 0).addBox(-21.0F, -7.0F, 22.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		}

		@Override
		public void setupAnim(Entity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.xRot = x;
			modelRenderer.yRot = y;
			modelRenderer.zRot = z;
		}

		@Override
		public void renderToBuffer(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
			WaterDomeMain.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_);
		}
	}
}
package com.github.jowashere.blackclover.client.renderer.spells.projectiles.water;// Made with Blockbench 4.1.5
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.github.jowashere.blackclover.entities.spells.water.PointBlankDragonEntity;
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
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class PointBlankWaterDragonRenderer extends EntityRenderer<PointBlankDragonEntity> {

	private static final ResourceLocation texture = new ResourceLocation("blackclover:textures/entities/projectiles/waterdragonpointblank.png");
	public PointBlankWaterDragonRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public void render(PointBlankDragonEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		IVertexBuilder vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
		matrixStackIn.pushPose();
		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.yRotO, entityIn.yRot) - 90));
		matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(90 + MathHelper.lerp(partialTicks, entityIn.xRotO, entityIn.xRot)));
		EntityModel model = new Water_point_blank_dragon_model();
		model.renderToBuffer(matrixStackIn, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.0625f);
		matrixStackIn.popPose();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	public static class Factory implements IRenderFactory<PointBlankDragonEntity> {

		@Override
		public EntityRenderer<? super PointBlankDragonEntity> createRenderFor(EntityRendererManager manager) {
			return new PointBlankWaterDragonRenderer(manager);
		}
	}

	@Override
	public ResourceLocation getTextureLocation(PointBlankDragonEntity entity)
	{
		return texture;
	}


	public class Water_point_blank_dragon_model extends EntityModel<Entity> {
		private final ModelRenderer WaterDragonMain;
		private final ModelRenderer WaterDragonHead;
		private final ModelRenderer UpperSnout;
		private final ModelRenderer TopTeeth;
		private final ModelRenderer Tooth23_r1;
		private final ModelRenderer LowerSnout;
		private final ModelRenderer BottomTeeth;
		private final ModelRenderer Tooth23_r2;
		private final ModelRenderer WaterDragonHeadDetails;
		private final ModelRenderer HeadD3_r1;
		private final ModelRenderer HeadD2_r1;
		private final ModelRenderer HeadD1_r1;
		private final ModelRenderer WaterDragonBody;
		private final ModelRenderer WaterDragonBodyDetails;
		private final ModelRenderer BodyD4_r1;
		private final ModelRenderer BodyD3_r1;

		public Water_point_blank_dragon_model() {
			texWidth = 16;
			texHeight = 16;

			WaterDragonMain = new ModelRenderer(this);
			WaterDragonMain.setPos(0.0F, 24.0F, 0.0F);
			setRotationAngle(WaterDragonMain, 0.0F, 1.5708F, 0.0F);


			WaterDragonHead = new ModelRenderer(this);
			WaterDragonHead.setPos(-572.0F, -30.0F, 0.0F);
			WaterDragonMain.addChild(WaterDragonHead);
			WaterDragonHead.texOffs(0, 0).addBox(556.0F, -52.0F, -23.0F, 10.0F, 47.0F, 29.0F, 0.0F, false);
			WaterDragonHead.texOffs(0, 0).addBox(559.0F, -55.0F, -27.0F, 9.0F, 51.0F, 37.0F, 0.0F, false);
			WaterDragonHead.texOffs(0, 0).addBox(568.0F, -55.0F, -27.0F, 24.0F, 11.0F, 37.0F, 0.0F, false);
			WaterDragonHead.texOffs(0, 0).addBox(568.0F, -14.0F, -27.0F, 24.0F, 11.0F, 37.0F, 0.0F, false);
			WaterDragonHead.texOffs(0, 0).addBox(591.0F, -54.0F, -26.0F, 10.0F, 49.0F, 35.0F, 0.0F, false);
			WaterDragonHead.texOffs(0, 0).addBox(584.0F, -48.0F, -26.0F, 7.0F, 7.0F, 35.0F, 0.0F, false);
			WaterDragonHead.texOffs(0, 0).addBox(584.0F, -17.0F, -26.0F, 7.0F, 7.0F, 35.0F, 0.0F, false);
			WaterDragonHead.texOffs(0, 0).addBox(568.0F, -17.0F, -26.0F, 7.0F, 7.0F, 35.0F, 0.0F, false);
			WaterDragonHead.texOffs(0, 0).addBox(568.0F, -48.0F, -26.0F, 7.0F, 7.0F, 35.0F, 0.0F, false);

			UpperSnout = new ModelRenderer(this);
			UpperSnout.setPos(599.0F, -32.0F, -9.0F);
			WaterDragonHead.addChild(UpperSnout);
			setRotationAngle(UpperSnout, 0.0F, 0.0F, -0.2182F);
			UpperSnout.texOffs(0, 0).addBox(-5.0F, -17.0F, -13.0F, 16.0F, 17.0F, 27.0F, 0.0F, false);
			UpperSnout.texOffs(0, 0).addBox(10.0F, -14.0F, -11.0F, 11.0F, 14.0F, 23.0F, 0.0F, false);
			UpperSnout.texOffs(0, 0).addBox(19.0F, -10.0F, -8.0F, 26.0F, 10.0F, 17.0F, 0.0F, false);
			UpperSnout.texOffs(0, 0).addBox(36.0F, -11.0F, -9.0F, 10.0F, 11.0F, 19.0F, 0.0F, false);

			TopTeeth = new ModelRenderer(this);
			TopTeeth.setPos(-3.0F, 23.0F, 0.0F);
			UpperSnout.addChild(TopTeeth);
			setRotationAngle(TopTeeth, 0.0F, 0.0F, -0.3927F);


			Tooth23_r1 = new ModelRenderer(this);
			Tooth23_r1.setPos(71.0F, -21.0F, 11.0F);
			TopTeeth.addChild(Tooth23_r1);
			setRotationAngle(Tooth23_r1, 0.0F, 0.0F, 0.3927F);
			Tooth23_r1.texOffs(0, 0).addBox(-47.2602F, 18.198F, -1.5F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			Tooth23_r1.texOffs(0, 0).addBox(-50.2602F, 19.198F, -1.5F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			Tooth23_r1.texOffs(0, 0).addBox(-44.2602F, 22.198F, -1.5F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			Tooth23_r1.texOffs(0, 0).addBox(-44.2602F, 22.198F, -21.5F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			Tooth23_r1.texOffs(0, 0).addBox(-47.2602F, 22.198F, -23.5F, 2.0F, 2.0F, 2.0F, 0.0F, false);
			Tooth23_r1.texOffs(0, 0).addBox(-50.2602F, 22.198F, -23.5F, 2.0F, 3.0F, 2.0F, 0.0F, false);
			Tooth23_r1.texOffs(0, 0).addBox(-17.2602F, 22.198F, -18.5F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			Tooth23_r1.texOffs(0, 0).addBox(-20.2602F, 22.198F, -18.5F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			Tooth23_r1.texOffs(0, 0).addBox(-23.2602F, 22.198F, -18.5F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			Tooth23_r1.texOffs(0, 0).addBox(-26.2602F, 22.198F, -18.5F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			Tooth23_r1.texOffs(0, 0).addBox(-29.2602F, 22.198F, -18.5F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			Tooth23_r1.texOffs(0, 0).addBox(-32.2602F, 22.198F, -18.5F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			Tooth23_r1.texOffs(0, 0).addBox(-32.2602F, 22.198F, -4.5F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			Tooth23_r1.texOffs(0, 0).addBox(-29.2602F, 22.198F, -4.5F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			Tooth23_r1.texOffs(0, 0).addBox(-26.2602F, 22.198F, -4.5F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			Tooth23_r1.texOffs(0, 0).addBox(-23.2602F, 22.198F, -4.5F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			Tooth23_r1.texOffs(0, 0).addBox(-20.2602F, 22.198F, -4.5F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			Tooth23_r1.texOffs(0, 0).addBox(-17.2602F, 22.198F, -4.5F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			Tooth23_r1.texOffs(0, 0).addBox(-12.2602F, 22.198F, -8.5F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			Tooth23_r1.texOffs(0, 0).addBox(-12.2602F, 22.198F, -14.5F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			Tooth23_r1.texOffs(0, 0).addBox(-11.2602F, 22.198F, -11.5F, 2.0F, 3.0F, 2.0F, 0.0F, false);
			Tooth23_r1.texOffs(0, 0).addBox(-14.2602F, 22.198F, -17.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			Tooth23_r1.texOffs(0, 0).addBox(-14.2602F, 22.198F, -6.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

			LowerSnout = new ModelRenderer(this);
			LowerSnout.setPos(592.0F, -3.0F, -9.0F);
			WaterDragonHead.addChild(LowerSnout);
			setRotationAngle(LowerSnout, 0.0F, 0.0F, 0.6545F);
			LowerSnout.texOffs(0, 0).addBox(-7.0F, -10.0F, -12.0F, 17.0F, 10.0F, 25.0F, 0.0F, false);
			LowerSnout.texOffs(0, 0).addBox(7.0F, -10.0F, -10.0F, 13.0F, 10.0F, 21.0F, 0.0F, false);
			LowerSnout.texOffs(0, 0).addBox(17.0F, -10.0F, -7.0F, 27.0F, 10.0F, 15.0F, 0.0F, false);

			BottomTeeth = new ModelRenderer(this);
			BottomTeeth.setPos(-1.0F, -6.0F, 0.0F);
			LowerSnout.addChild(BottomTeeth);
			setRotationAngle(BottomTeeth, 0.0F, 0.0F, -0.3927F);


			Tooth23_r2 = new ModelRenderer(this);
			Tooth23_r2.setPos(63.0F, -11.0F, 11.0F);
			BottomTeeth.addChild(Tooth23_r2);
			setRotationAngle(Tooth23_r2, 0.0F, 0.0F, 0.3927F);
			Tooth23_r2.texOffs(0, 0).addBox(-43.5F, 28.198F, -2.5F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			Tooth23_r2.texOffs(0, 0).addBox(-49.5F, 28.198F, -0.5F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			Tooth23_r2.texOffs(0, 0).addBox(-46.5F, 27.198F, -0.2F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			Tooth23_r2.texOffs(0, 0).addBox(-46.5F, 27.198F, -22.8F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			Tooth23_r2.texOffs(0, 0).addBox(-49.5F, 28.198F, -22.5F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			Tooth23_r2.texOffs(0, 0).addBox(-43.5F, 28.198F, -20.5F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			Tooth23_r2.texOffs(0, 0).addBox(-17.5F, 28.198F, -17.5F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			Tooth23_r2.texOffs(0, 0).addBox(-20.5F, 28.198F, -17.5F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			Tooth23_r2.texOffs(0, 0).addBox(-23.5F, 28.198F, -17.5F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			Tooth23_r2.texOffs(0, 0).addBox(-26.5F, 28.198F, -17.5F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			Tooth23_r2.texOffs(0, 0).addBox(-29.5F, 28.198F, -17.5F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			Tooth23_r2.texOffs(0, 0).addBox(-32.5F, 28.198F, -17.5F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			Tooth23_r2.texOffs(0, 0).addBox(-32.5F, 28.198F, -5.5F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			Tooth23_r2.texOffs(0, 0).addBox(-29.5F, 28.198F, -5.5F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			Tooth23_r2.texOffs(0, 0).addBox(-26.5F, 28.198F, -5.5F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			Tooth23_r2.texOffs(0, 0).addBox(-23.5F, 28.198F, -5.5F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			Tooth23_r2.texOffs(0, 0).addBox(-20.5F, 28.198F, -5.5F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			Tooth23_r2.texOffs(0, 0).addBox(-17.5F, 28.198F, -5.5F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			Tooth23_r2.texOffs(0, 0).addBox(-12.5F, 27.198F, -8.5F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			Tooth23_r2.texOffs(0, 0).addBox(-12.0F, 29.198F, -11.5F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			Tooth23_r2.texOffs(0, 0).addBox(-12.5F, 27.198F, -14.5F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			Tooth23_r2.texOffs(0, 0).addBox(-14.2602F, 27.198F, -16.8F, 2.0F, 4.0F, 2.0F, 0.0F, false);
			Tooth23_r2.texOffs(0, 0).addBox(-14.2602F, 27.198F, -6.2F, 2.0F, 4.0F, 2.0F, 0.0F, false);

			WaterDragonHeadDetails = new ModelRenderer(this);
			WaterDragonHeadDetails.setPos(-16.0F, 0.0F, 0.0F);
			WaterDragonHead.addChild(WaterDragonHeadDetails);


			HeadD3_r1 = new ModelRenderer(this);
			HeadD3_r1.setPos(567.0F, -37.0F, 14.0F);
			WaterDragonHeadDetails.addChild(HeadD3_r1);
			setRotationAngle(HeadD3_r1, 0.0F, 0.2618F, 0.0F);
			HeadD3_r1.texOffs(0, 0).addBox(-100.0F, -18.0F, 0.0F, 115.0F, 36.0F, 0.0F, 0.0F, false);

			HeadD2_r1 = new ModelRenderer(this);
			HeadD2_r1.setPos(567.0F, -37.0F, -31.0F);
			WaterDragonHeadDetails.addChild(HeadD2_r1);
			setRotationAngle(HeadD2_r1, 0.0F, -0.2618F, 0.0F);
			HeadD2_r1.texOffs(0, 0).addBox(-100.0F, -18.0F, 0.0F, 115.0F, 36.0F, 0.0F, 0.0F, false);

			HeadD1_r1 = new ModelRenderer(this);
			HeadD1_r1.setPos(562.0F, -62.0F, -8.5F);
			WaterDragonHeadDetails.addChild(HeadD1_r1);
			setRotationAngle(HeadD1_r1, 0.0F, 0.0F, 0.3491F);
			HeadD1_r1.texOffs(0, 0).addBox(-95.0F, 0.0F, -20.5F, 116.0F, 0.0F, 41.0F, 0.0F, false);

			WaterDragonBody = new ModelRenderer(this);
			WaterDragonBody.setPos(-572.0F, -22.0F, 0.0F);
			WaterDragonMain.addChild(WaterDragonBody);
			WaterDragonBody.texOffs(0, 0).addBox(536.0F, -49.0F, -22.0F, 22.0F, 27.0F, 27.0F, 0.0F, false);
			WaterDragonBody.texOffs(0, 0).addBox(516.0F, -49.0F, -22.0F, 22.0F, 27.0F, 27.0F, 0.0F, false);
			WaterDragonBody.texOffs(0, 0).addBox(496.0F, -49.0F, -22.0F, 22.0F, 27.0F, 27.0F, 0.0F, false);
			WaterDragonBody.texOffs(0, 0).addBox(476.0F, -49.0F, -22.0F, 22.0F, 27.0F, 27.0F, 0.0F, false);
			WaterDragonBody.texOffs(0, 0).addBox(456.0F, -49.0F, -22.0F, 22.0F, 27.0F, 27.0F, 0.0F, false);
			WaterDragonBody.texOffs(0, 0).addBox(436.0F, -49.0F, -22.0F, 22.0F, 27.0F, 27.0F, 0.0F, false);
			WaterDragonBody.texOffs(0, 0).addBox(417.0F, -49.0F, -22.0F, 22.0F, 27.0F, 27.0F, 0.0F, false);

			WaterDragonBodyDetails = new ModelRenderer(this);
			WaterDragonBodyDetails.setPos(0.0F, 0.0F, 0.0F);
			WaterDragonBody.addChild(WaterDragonBodyDetails);
			WaterDragonBodyDetails.texOffs(0, 0).addBox(411.0F, -53.0F, -26.0F, 138.0F, 0.0F, 35.0F, 0.0F, false);
			WaterDragonBodyDetails.texOffs(0, 0).addBox(411.0F, -18.0F, -26.0F, 138.0F, 0.0F, 35.0F, 0.0F, false);

			BodyD4_r1 = new ModelRenderer(this);
			BodyD4_r1.setPos(0.0F, -44.0F, 60.0F);
			WaterDragonBodyDetails.addChild(BodyD4_r1);
			setRotationAngle(BodyD4_r1, 1.5708F, 0.0F, 0.0F);
			BodyD4_r1.texOffs(0, 0).addBox(411.0F, -51.0F, -26.0F, 138.0F, 0.0F, 35.0F, 0.0F, false);

			BodyD3_r1 = new ModelRenderer(this);
			BodyD3_r1.setPos(0.0F, -44.0F, 25.0F);
			WaterDragonBodyDetails.addChild(BodyD3_r1);
			setRotationAngle(BodyD3_r1, 1.5708F, 0.0F, 0.0F);
			BodyD3_r1.texOffs(0, 0).addBox(411.0F, -51.0F, -26.0F, 138.0F, 0.0F, 35.0F, 0.0F, false);
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
			WaterDragonMain.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_);
		}
	}
}
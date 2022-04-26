package com.github.jowashere.blackclover.client.renderer.spells.projectiles.water;// Made with Blockbench 4.1.5
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


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
public class WaterSpearRenderer extends EntityRenderer<WaterSpearEntity> {

	private static final ResourceLocation texture = new ResourceLocation("blackclover:textures/entities/projectiles/waterspear.png");
	public WaterSpearRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public void render(WaterSpearEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		IVertexBuilder vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
		matrixStackIn.pushPose();
		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.yRotO, entityIn.yRot) - 90));
		matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(90 + MathHelper.lerp(partialTicks, entityIn.xRotO, entityIn.xRot)));
		EntityModel model = new Waterspear_model();
		model.renderToBuffer(matrixStackIn, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.0625f);
		matrixStackIn.popPose();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	public static class Factory implements IRenderFactory<WaterSpearEntity> {

		@Override
		public EntityRenderer<? super WaterSpearEntity> createRenderFor(EntityRendererManager manager) {
			return new WaterSpearRenderer(manager);
		}
	}

	@Override
	public ResourceLocation getTextureLocation(WaterSpearEntity entity) {
		return texture;
	}

	public class Waterspear_model extends EntityModel<Entity> {
		private final ModelRenderer WaterSpearMain;
		private final ModelRenderer WaterSpear;
		private final ModelRenderer WaterSpearDetails;
		private final ModelRenderer Tier1;
		private final ModelRenderer Tier2;
		private final ModelRenderer WaterSpearSwirls;
		private final ModelRenderer Swirl;
		private final ModelRenderer SwirlG1;
		private final ModelRenderer Swirl4_r1;
		private final ModelRenderer Swirl3_r1;
		private final ModelRenderer SwirlG2;
		private final ModelRenderer Swirl4_r2;
		private final ModelRenderer Swirl3_r2;

		public Waterspear_model() {
			texWidth = 16;
			texHeight = 16;

			WaterSpearMain = new ModelRenderer(this);
			WaterSpearMain.setPos(1.0F, 24.0F, 24.0F);


			WaterSpear = new ModelRenderer(this);
			WaterSpear.setPos(0.0F, 0.0F, 0.0F);
			WaterSpearMain.addChild(WaterSpear);
			WaterSpear.texOffs(0, 0).addBox(-1.0F, -15.0F, -47.0F, 1.0F, 1.0F, 72.0F, 0.0F, false);
			WaterSpear.texOffs(0, 0).addBox(-1.5F, -15.5F, -56.0F, 2.0F, 2.0F, 12.0F, 0.0F, false);
			WaterSpear.texOffs(0, 0).addBox(-2.0F, -16.0F, -61.0F, 3.0F, 3.0F, 12.0F, 0.0F, false);
			WaterSpear.texOffs(0, 0).addBox(-3.0F, -17.0F, -52.0F, 5.0F, 5.0F, 3.0F, 0.0F, false);
			WaterSpear.texOffs(0, 0).addBox(-2.5F, -16.5F, -55.0F, 4.0F, 4.0F, 7.0F, 0.0F, false);
			WaterSpear.texOffs(0, 0).addBox(-1.5F, -15.5F, -64.0F, 2.0F, 2.0F, 12.0F, 0.0F, false);
			WaterSpear.texOffs(0, 0).addBox(-1.0F, -15.0F, -69.0F, 1.0F, 1.0F, 8.0F, 0.0F, false);

			WaterSpearDetails = new ModelRenderer(this);
			WaterSpearDetails.setPos(0.0F, 0.0F, 0.0F);
			WaterSpear.addChild(WaterSpearDetails);


			Tier1 = new ModelRenderer(this);
			Tier1.setPos(0.0F, 0.0F, 0.0F);
			WaterSpearDetails.addChild(Tier1);
			Tier1.texOffs(0, 0).addBox(2.5F, -17.5F, -55.0F, 0.0F, 6.0F, 15.0F, 0.0F, false);
			Tier1.texOffs(0, 0).addBox(-3.5F, -17.5F, -55.0F, 0.0F, 6.0F, 15.0F, 0.0F, false);
			Tier1.texOffs(0, 0).addBox(-3.5F, -17.5F, -55.0F, 6.0F, 0.0F, 15.0F, 0.0F, false);
			Tier1.texOffs(0, 0).addBox(-3.5F, -11.5F, -55.0F, 6.0F, 0.0F, 15.0F, 0.0F, false);

			Tier2 = new ModelRenderer(this);
			Tier2.setPos(0.0F, 0.0F, -13.0F);
			WaterSpearDetails.addChild(Tier2);
			Tier2.texOffs(0, 0).addBox(1.5F, -16.5F, -50.0F, 0.0F, 4.0F, 7.0F, 0.0F, false);
			Tier2.texOffs(0, 0).addBox(-2.5F, -16.5F, -50.0F, 0.0F, 4.0F, 7.0F, 0.0F, false);
			Tier2.texOffs(0, 0).addBox(-2.5F, -16.5F, -50.0F, 4.0F, 0.0F, 7.0F, 0.0F, false);
			Tier2.texOffs(0, 0).addBox(-2.5F, -12.5F, -50.0F, 4.0F, 0.0F, 7.0F, 0.0F, false);

			WaterSpearSwirls = new ModelRenderer(this);
			WaterSpearSwirls.setPos(22.0F, -18.0F, 0.0F);
			WaterSpearMain.addChild(WaterSpearSwirls);
			setRotationAngle(WaterSpearSwirls, 0.0F, 0.0F, -1.5708F);


			Swirl = new ModelRenderer(this);
			Swirl.setPos(-22.0F, -22.0F, 10.0F);
			WaterSpearSwirls.addChild(Swirl);


			SwirlG1 = new ModelRenderer(this);
			SwirlG1.setPos(19.7321F, -6.0F, 0.0F);
			Swirl.addChild(SwirlG1);
			SwirlG1.texOffs(0, 0).addBox(0.0F, 4.0F, -50.0F, 0.0F, 3.0F, 65.0F, 0.0F, false);
			SwirlG1.texOffs(0, 0).addBox(-3.0F, 4.0F, -50.0F, 0.0F, 3.0F, 65.0F, 0.0F, false);

			Swirl4_r1 = new ModelRenderer(this);
			Swirl4_r1.setPos(0.0F, 0.0F, 0.0F);
			SwirlG1.addChild(Swirl4_r1);
			setRotationAngle(Swirl4_r1, 0.0F, 0.0F, -1.5708F);
			Swirl4_r1.texOffs(0, 0).addBox(-4.0F, -3.0F, -50.0F, 0.0F, 3.0F, 65.0F, 0.0F, false);

			Swirl3_r1 = new ModelRenderer(this);
			Swirl3_r1.setPos(0.0F, 3.0F, 0.0F);
			SwirlG1.addChild(Swirl3_r1);
			setRotationAngle(Swirl3_r1, 0.0F, 0.0F, -1.5708F);
			Swirl3_r1.texOffs(0, 0).addBox(-4.0F, -3.0F, -50.0F, 0.0F, 3.0F, 65.0F, 0.0F, false);

			SwirlG2 = new ModelRenderer(this);
			SwirlG2.setPos(19.7321F, -6.0F, 0.0F);
			Swirl.addChild(SwirlG2);
			SwirlG2.texOffs(0, 0).addBox(1.0F, 3.0F, -50.0F, 0.0F, 5.0F, 65.0F, 0.0F, false);
			SwirlG2.texOffs(0, 0).addBox(-4.0F, 3.0F, -50.0F, 0.0F, 5.0F, 65.0F, 0.0F, false);

			Swirl4_r2 = new ModelRenderer(this);
			Swirl4_r2.setPos(0.0F, -1.0F, 0.0F);
			SwirlG2.addChild(Swirl4_r2);
			setRotationAngle(Swirl4_r2, 0.0F, 0.0F, -1.5708F);
			Swirl4_r2.texOffs(0, 0).addBox(-4.0F, -4.0F, -50.0F, 0.0F, 5.0F, 65.0F, 0.0F, false);

			Swirl3_r2 = new ModelRenderer(this);
			Swirl3_r2.setPos(0.0F, 4.0F, 0.0F);
			SwirlG2.addChild(Swirl3_r2);
			setRotationAngle(Swirl3_r2, 0.0F, 0.0F, -1.5708F);
			Swirl3_r2.texOffs(0, 0).addBox(-4.0F, -4.0F, -50.0F, 0.0F, 5.0F, 65.0F, 0.0F, false);
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
			WaterSpearMain.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_);
		}
	}
}
package com.github.jowashere.blackclover.client.renderer.spells.others;// Made with Blockbench 4.1.5
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports

import com.github.jowashere.blackclover.entities.spells.water.WaterShieldEntity;
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
public class WaterShieldRenderer extends EntityRenderer<WaterShieldEntity> {

	private static final ResourceLocation texture = new ResourceLocation("blackclover:textures/entities/projectiles/watershield.png");

	public WaterShieldRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
	}



	@Override
	public void render(WaterShieldEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		IVertexBuilder vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
		matrixStackIn.pushPose();
		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.yRotO, entityIn.yRot) - 90));
		matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(90 + MathHelper.lerp(partialTicks, entityIn.xRotO, entityIn.xRot)));
		EntityModel model = new Watershield_model();
		model.renderToBuffer(matrixStackIn, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.0625f);
		matrixStackIn.popPose();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	public static class Factory implements IRenderFactory<WaterShieldEntity> {

		@Override
		public EntityRenderer<? super WaterShieldEntity> createRenderFor(EntityRendererManager manager) {
			return new WaterShieldRenderer(manager);
		}
	}

	@Override
	public ResourceLocation getTextureLocation(WaterShieldEntity entity) {
		return texture;
	}

	public class Watershield_model extends EntityModel<Entity> {
		private final ModelRenderer WaterShieldMain;
		private final ModelRenderer WaterShield;

		public Watershield_model() {
			texWidth = 256;
			texHeight = 256;

			WaterShieldMain = new ModelRenderer(this);
			WaterShieldMain.setPos(13.0F, 39.0F, 0.0F);
			setRotationAngle(WaterShieldMain, 0.0F, 1.5708F, 0.0F);


			WaterShield = new ModelRenderer(this);
			WaterShield.setPos(0.0F, 0.0F, 0.0F);
			WaterShieldMain.addChild(WaterShield);
			WaterShield.texOffs(0, 0).addBox(0.9F, -66.0F, -37.0F, 0.0F, 49.0F, 48.0F, 0.0F, false);
			WaterShield.texOffs(52, 53).addBox(1.0F, -64.0F, -35.0F, 1.0F, 45.0F, 44.0F, 0.0F, false);
			WaterShield.texOffs(98, 0).addBox(2.0F, -60.0F, -31.0F, 1.0F, 37.0F, 36.0F, 0.0F, false);
			WaterShield.texOffs(63, 142).addBox(0.0F, -66.0F, -37.0F, 1.0F, 49.0F, 7.0F, 0.0F, false);
			WaterShield.texOffs(135, 135).addBox(0.0F, -66.0F, 4.0F, 1.0F, 49.0F, 7.0F, 0.0F, false);
			WaterShield.texOffs(0, 108).addBox(0.0F, -66.0F, -30.0F, 1.0F, 7.0F, 34.0F, 0.0F, false);
			WaterShield.texOffs(0, 0).addBox(0.0F, -24.0F, -30.0F, 1.0F, 7.0F, 34.0F, 0.0F, false);
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
			WaterShieldMain.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_);
		}
	}
}
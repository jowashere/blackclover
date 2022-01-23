package com.github.jowashere.blackclover.client.renderer.entities.hostile;

import com.github.jowashere.blackclover.client.renderer.models.VolcanoMonsterModel;
import com.github.jowashere.blackclover.client.renderer.spells.projectiles.light.LightSwordOJRenderer;
import com.github.jowashere.blackclover.client.renderer.spells.projectiles.sword.OriginFlashRenderer;
import com.github.jowashere.blackclover.entities.mobs.hostile.VolcanoMonsterEntity;
import com.github.jowashere.blackclover.entities.spells.light.LightSwordOJEntity;
import com.github.jowashere.blackclover.entities.spells.sword.OriginFlashEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
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

import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class VolcanoMonsterRenderer extends MobRenderer<VolcanoMonsterEntity, VolcanoMonsterModel<VolcanoMonsterEntity>>
{
    private static final ResourceLocation texture = new ResourceLocation("blackclover:textures/entities/mobs/hostile/volcano_monster.png");

    public VolcanoMonsterRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new VolcanoMonsterModel(), 1f);
    }

    @Override
    public void render(VolcanoMonsterEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }


    public static class Factory implements IRenderFactory<VolcanoMonsterEntity> {

        @Override
        public EntityRenderer<? super VolcanoMonsterEntity> createRenderFor(EntityRendererManager manager) {
            return new VolcanoMonsterRenderer(manager);
        }
    }


    @Override
    public ResourceLocation getTextureLocation(VolcanoMonsterEntity entity) {
        return texture;
    }

}
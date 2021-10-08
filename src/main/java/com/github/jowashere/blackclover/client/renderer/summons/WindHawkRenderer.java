package com.github.jowashere.blackclover.client.renderer.summons;

import com.github.jowashere.blackclover.client.renderer.models.WindHawkModel;
import com.github.jowashere.blackclover.entities.projectiles.spells.wind.WindCrescentEntity;
import com.github.jowashere.blackclover.entities.summons.WindHawkEntity;
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

@OnlyIn(Dist.CLIENT)
public class WindHawkRenderer extends MobRenderer<WindHawkEntity, WindHawkModel<WindHawkEntity>> {

    private static final ResourceLocation texture = new ResourceLocation("blackclover:textures/entities/projectiles/windblade.png");

    public WindHawkRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new WindHawkModel(), 0.1F);
    }

    @Override
    public ResourceLocation getTextureLocation(WindHawkEntity entity) {
        return texture;
    }

}



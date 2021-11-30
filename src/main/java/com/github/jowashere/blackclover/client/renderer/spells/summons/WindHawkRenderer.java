package com.github.jowashere.blackclover.client.renderer.spells.summons;

import com.github.jowashere.blackclover.client.renderer.models.WindHawkModel;
import com.github.jowashere.blackclover.entities.spells.wind.WindHawkEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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



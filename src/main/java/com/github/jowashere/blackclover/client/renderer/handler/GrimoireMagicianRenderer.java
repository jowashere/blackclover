package com.github.jowashere.blackclover.client.renderer.handler;

import com.github.jowashere.blackclover.client.renderer.models.GrimoireMagicianModel;
import com.github.jowashere.blackclover.entities.mobs.questers.GrimoireMagicianEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GrimoireMagicianRenderer extends MobRenderer<GrimoireMagicianEntity, GrimoireMagicianModel<GrimoireMagicianEntity>>
{
    private static final ResourceLocation texture = new ResourceLocation("blackclover:textures/entities/mobs/questers/grimoire_magician.png");

    public GrimoireMagicianRenderer(EntityRendererManager rendererManager)
    {
        super(rendererManager, new GrimoireMagicianModel(), 1F);
    }

    @Override
    public ResourceLocation getTextureLocation(GrimoireMagicianEntity entity)
    {
        return texture;
    }
}

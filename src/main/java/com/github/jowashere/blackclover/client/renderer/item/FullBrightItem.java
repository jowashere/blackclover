package com.github.jowashere.blackclover.client.renderer.item;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.pipeline.LightUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FullBrightItem implements IBakedModel {

    private final IBakedModel delegate;
    private Map<Direction, List<BakedQuad>> cachedQuads = Maps.newHashMap();

    public FullBrightItem(IBakedModel delegate) {
        this.delegate = delegate;
    }

    @Override
    public IBakedModel getBakedModel() {
        return delegate;
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
        return getQuads(state, side, rand);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
        return cachedQuads.computeIfAbsent(side, (face) -> {
            List<BakedQuad> quads = delegate.getQuads(state, side, rand);
            for (BakedQuad quad : quads)
                LightUtil.setLightData(quad, 0xF000F0);
            return quads;
        });
    }

    @Override
    public boolean useAmbientOcclusion() {
        return delegate.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return delegate.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return delegate.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return delegate.isCustomRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return delegate.getParticleIcon();
    }

    @Override
    public ItemCameraTransforms getTransforms() {
        return delegate.getTransforms();
    }

    @Override
    public ItemOverrideList getOverrides() {
        return delegate.getOverrides();
    }

    @Override
    public IBakedModel handlePerspective(ItemCameraTransforms.TransformType cameraTransformType, MatrixStack mat) {
        return net.minecraftforge.client.ForgeHooksClient.handlePerspective(this, cameraTransformType, mat);
    }

}

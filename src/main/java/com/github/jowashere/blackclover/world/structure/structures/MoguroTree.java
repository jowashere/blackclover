package com.github.jowashere.blackclover.world.structure.structures;

import com.github.jowashere.blackclover.init.ConfiguredFeaturesInit;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;

public class MoguroTree extends Tree
{

    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random p_225546_1_, boolean p_225546_2_) {
        return ConfiguredFeaturesInit.MOGURO_TREE;
    }
}

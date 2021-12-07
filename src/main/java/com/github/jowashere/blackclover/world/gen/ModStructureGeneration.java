package com.github.jowashere.blackclover.world.gen;

import com.github.jowashere.blackclover.init.StructuresInit;
import com.github.jowashere.blackclover.world.structure.configured.ConfiguredStructures;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class ModStructureGeneration
{
    public static void generateStructures(final BiomeLoadingEvent event)
    {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_MAGICTOWER); //TODO make it generate in specific biomes and villages
    }
}

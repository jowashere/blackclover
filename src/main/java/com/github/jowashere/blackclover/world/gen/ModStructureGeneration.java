package com.github.jowashere.blackclover.world.gen;

import com.github.jowashere.blackclover.world.biome.ModBiomes;
import com.github.jowashere.blackclover.world.structure.configured.ConfiguredStructures;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.Set;


public class ModStructureGeneration
{
    //structures that spawn in specific biomes
    //TODO make it more readable
    public static void generateStructures(final BiomeLoadingEvent event)
    {
        RegistryKey key = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
        Set types = BiomeDictionary.getTypes(key);

        if (types.contains(BiomeDictionary.Type.PLAINS) && !types.contains(BiomeDictionary.Type.NETHER) && !types.contains(BiomeDictionary.Type.END))
        {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_MAGICTOWER);
        }
        if (!types.contains(BiomeDictionary.Type.OCEAN) && !types.contains(BiomeDictionary.Type.DEAD) && !types.contains(BiomeDictionary.Type.COLD) && !types.contains(BiomeDictionary.Type.HOT) && !types.contains(BiomeDictionary.Type.WET) && !types.contains(BiomeDictionary.Type.WATER) && !types.contains(BiomeDictionary.Type.END) && !types.contains(BiomeDictionary.Type.NETHER)  )
        {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_BANDIT_CAMP);
        }
        if (event.getName().equals(ModBiomes.GRAND_MAGIC_ZONE_VOLCANO.get().getRegistryName()))
        {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_MINI_VOLCANO);

        }

    }
}

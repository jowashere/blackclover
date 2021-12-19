package com.github.jowashere.blackclover.world.gen;

import com.github.jowashere.blackclover.world.structure.configured.ConfiguredStructures;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.Set;


public class ModStructureGeneration
{
    public static void generateStructures(final BiomeLoadingEvent event)
    {
        RegistryKey key = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
        Set types = BiomeDictionary.getTypes(key);

        if (types.contains(BiomeDictionary.Type.PLAINS))
        {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_MAGICTOWER);
        }
        if (!types.contains(BiomeDictionary.Type.OCEAN) || !types.contains(BiomeDictionary.Type.DEAD))
        {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_BANDIT_CAMP);
        }
    }
}

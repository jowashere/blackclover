package com.github.jowashere.blackclover.world.gen;

import com.github.jowashere.blackclover.init.EntityInit;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.Set;

public class ModEntityGeneration
{
    public static void onEntitySpawn(final BiomeLoadingEvent event)
    {
        //TODO fix the spawning of the entity
        RegistryKey key = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
        Set types = BiomeDictionary.getTypes(key);

        if (types.contains(BiomeDictionary.Type.PLAINS)) // Only spawns in plains
        {
            //Weight of spawn: 100, minimum: 4, max: 6
            event.getSpawns().addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityInit.BANDIT.get(), 100, 4, 6)).build();
        }
    }
}

package com.github.jowashere.blackclover.world.gen;

import com.github.jowashere.blackclover.init.EntityInit;
import com.github.jowashere.blackclover.init.StructuresInit;
import com.github.jowashere.blackclover.world.biome.ModBiomes;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.StructureSpawnListGatherEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class ModEntityGeneration
{
    //entities that spawn in specific biomes
    public static void onEntitySpawn(final BiomeLoadingEvent event)
    {
        RegistryKey key = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
        Set types = BiomeDictionary.getTypes(key);

        if (types.contains(BiomeDictionary.Type.PLAINS)) // Only spawns in plains
        {
            //Weight of spawn: 100, minimum: 4, max: 6
            event.getSpawns().addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityInit.BANDIT.get(), 400, 3, 4)).build();

        }
        if (event.getName().toString().equals(ModBiomes.GRAND_MAGIC_ZONE_VOLCANO.getId().toString()))
        {
            event.getSpawns().addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityInit.VOLCANO_MONSTER.get(), 600, 1, 2)).build();
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(EntityInit.VOLCANO_MONSTER.get(), 600, 1, 2));
        }
    }

    //entities that spawn in specific structures
    public static void onEntitySpawnInStructure(final StructureSpawnListGatherEvent event)
    {
        if (event.getEntitySpawns().containsKey(EntityClassification.CREATURE))
        {
            boolean isCamp = event.getStructure().equals(StructuresInit.BANDIT_CAMP.get());

            if (isCamp)
            {
                event.addEntitySpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityInit.BANDIT.get(), 200, 5, 5));
            }
        }
    }
}

package com.github.jowashere.blackclover.init;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.world.structure.structures.MoguroTree;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class BlocksInit
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MODID);

    public static final RegistryObject<Block> MOGURO_LOG = BLOCKS.register("moguro_log",
            () -> new Block(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLUE)
                    .strength(2f, 7f)
                    .harvestTool(ToolType.AXE)
                    .harvestLevel(2)
                    .sound(SoundType.WOOD)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> MOGURO_LEAF = BLOCKS.register("moguro_leaf",
            () -> new Block(AbstractBlock.Properties.of(Material.LEAVES, MaterialColor.COLOR_BLUE)
                    .strength(0.5f, 0.5f)
                    .harvestTool(ToolType.AXE)
                    .harvestLevel(0)
                    .sound(SoundType.GRASS)));

    public static final RegistryObject<Block> MOGURO_SAPLING = BLOCKS.register("moguro_sapling",
            () -> new SaplingBlock(new MoguroTree(), AbstractBlock.Properties.of(Material.WOOD)));
}

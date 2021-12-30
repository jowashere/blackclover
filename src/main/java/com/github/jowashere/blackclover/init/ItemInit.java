package com.github.jowashere.blackclover.init;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.items.ModSpawnEgg;
import com.github.jowashere.blackclover.items.armors.*;
import com.github.jowashere.blackclover.items.weapons.MagicSwordItem;
import com.github.jowashere.blackclover.items.weapons.SwordMagicSwords;
import com.github.jowashere.blackclover.items.weapons.YamisKatana;
import com.github.jowashere.blackclover.spells.SpellRegistry;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);
    public List<Supplier<Item>> items = new ArrayList<>();

    //Block Items
    public static final RegistryObject<BlockItem> MOGURO_LOG = ITEMS.register("moguro_log", () -> new BlockItem(BlocksInit.MOGURO_LOG.get(),
            new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> MOGURO_LEAF = ITEMS.register("moguro_leaf", () -> new BlockItem(BlocksInit.MOGURO_LEAF.get(),
            new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> MOGURO_SAPLING = ITEMS.register("moguro_sapling", () -> new BlockItem(BlocksInit.MOGURO_SAPLING.get(),
            new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)));

    //Armors
    public static final RegistryObject<Item> MAGE_HAT = ITEMS.register("mage_hat", ()-> new MageArmorItem("mage", EquipmentSlotType.HEAD));
    public static final RegistryObject<Item> MAGE_CHEST = ITEMS.register("mage_chest", ()-> new MageArmorItem("mage", EquipmentSlotType.CHEST));
    public static final RegistryObject<Item> MAGE_LEGS = ITEMS.register("mage_legs", ()-> new MageArmorItem("mage", EquipmentSlotType.LEGS));
    public static final RegistryObject<Item> MAGE_FEET = ITEMS.register("mage_feet", ()-> new MageArmorItem("mage", EquipmentSlotType.FEET));

    public static final RegistryObject<Item> ASTA_HAT = ITEMS.register("asta_hat", ()-> new AstaClothesItem("asta", EquipmentSlotType.HEAD));
    public static final RegistryObject<Item> ASTA_CHEST = ITEMS.register("asta_chest", ()-> new AstaClothesItem("asta", EquipmentSlotType.CHEST));
    public static final RegistryObject<Item> ASTA_LEGS = ITEMS.register("asta_legs", ()-> new AstaClothesItem("asta", EquipmentSlotType.LEGS));
    public static final RegistryObject<Item> ASTA_BOOTS = ITEMS.register("asta_feet", ()-> new AstaClothesItem("asta", EquipmentSlotType.FEET));

    public static final RegistryObject<Item> ASTA_HAT2 = ITEMS.register("asta_2_hat", ()-> new AstaClothesPostItem("asta_2", EquipmentSlotType.HEAD));
    public static final RegistryObject<Item> ASTA_CHEST2 = ITEMS.register("asta_2_chest", ()-> new AstaClothesPostItem("asta_2", EquipmentSlotType.CHEST));
    public static final RegistryObject<Item> ASTA_LEGS2 = ITEMS.register("asta_2_legs", ()-> new AstaClothesPostItem("asta_2", EquipmentSlotType.LEGS));
    public static final RegistryObject<Item> ASTA_BOOTS2 = ITEMS.register("asta_2_feet", ()-> new AstaClothesPostItem("asta_2", EquipmentSlotType.FEET));

    public static final RegistryObject<Item> YUNO_CHEST = ITEMS.register("yuno_chest", ()-> new YunoClothesItem("yuno", EquipmentSlotType.CHEST));
    public static final RegistryObject<Item> YUNO_LEGS = ITEMS.register("yuno_legs", ()-> new YunoClothesItem("yuno", EquipmentSlotType.LEGS));
    public static final RegistryObject<Item> YUNO_BOOTS = ITEMS.register("yuno_feet", ()-> new YunoClothesItem("yuno", EquipmentSlotType.FEET));

    public static final RegistryObject<Item> GOLDEN_DAWN_CHEST = ITEMS.register("golden_dawn_chest", ()-> new GoldenDawnUniArmorItem("golden_dawn", EquipmentSlotType.CHEST));
    public static final RegistryObject<Item> GOLDEN_DAWN_LEGS = ITEMS.register("golden_dawn_legs", ()-> new GoldenDawnUniArmorItem("golden_dawn", EquipmentSlotType.LEGS));
    public static final RegistryObject<Item> GOLDEN_DAWN_FEET = ITEMS.register("golden_dawn_feet", ()-> new GoldenDawnUniArmorItem("golden_dawn", EquipmentSlotType.FEET));

    //Weapons
    public static final RegistryObject<Item> YAMIS_KATANA = ITEMS.register("yamis_katana", () -> new YamisKatana(new Item.Properties().tab(ItemGroups.EQUIPMENT).stacksTo(1), 6, -1.6f));

    public static final RegistryObject<Item> DEMON_SLAYER = ITEMS.register("demon_slayer", () -> new SwordMagicSwords(new Item.Properties().tab(ItemGroups.EQUIPMENT).stacksTo(1).setNoRepair(), 8, -3.2f));
    public static final RegistryObject<Item> DEMON_DESTROYER = ITEMS.register("demon_destroyer", () -> new SwordMagicSwords(new Item.Properties().tab(ItemGroups.EQUIPMENT).stacksTo(1).setNoRepair(), 6, -2.4f));
    public static final RegistryObject<Item> DEMON_DWELLER = ITEMS.register("demon_dweller", () -> new SwordMagicSwords(new Item.Properties().tab(ItemGroups.EQUIPMENT).stacksTo(1).setNoRepair(), 6, -2.4f));

    public static final RegistryObject<Item> LIGHT_SWORD = ITEMS.register("light_sword", () -> new MagicSwordItem(SpellRegistry.LIGHT_SWORD, 5, -2.4f, new Item.Properties().tab(ItemGroups.EQUIPMENT).stacksTo(1).setNoRepair()));

    //Spawn Eggs
    public static final RegistryObject<ModSpawnEgg> BANDIT_SPAWN_EGG = ITEMS.register("bandit_spawn_egg", () -> new ModSpawnEgg(EntityInit.BANDIT, 5660240, 8213829, new Item.Properties().tab(ItemGroup.TAB_MISC)));

}

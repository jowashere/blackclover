package com.github.jowashere.blackclover.init;

import com.github.jowashere.blackclover.ItemGroups;
import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.items.SwordMagicSwords;
import com.github.jowashere.blackclover.items.YamisKatana;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);
    public List<Supplier<Item>> items = new ArrayList<>();

    //Weapons
    public static final RegistryObject<Item> YAMIS_KATANA = ITEMS.register("yamis_katana", () -> new YamisKatana(new Item.Properties().tab(ItemGroups.WEAPONS).stacksTo(1), 6, -1.6f));

    public static final RegistryObject<Item> DEMON_SLAYER = ITEMS.register("demon_slayer", () -> new SwordMagicSwords(new Item.Properties().tab(ItemGroups.WEAPONS).stacksTo(1).setNoRepair(), 8, -3.2f));
    public static final RegistryObject<Item> DEMON_DESTROYER = ITEMS.register("demon_destroyer", () -> new SwordMagicSwords(new Item.Properties().tab(ItemGroups.WEAPONS).stacksTo(1).setNoRepair(), 6, -2.4f));
    public static final RegistryObject<Item> DEMON_DWELLER = ITEMS.register("demon_dweller", () -> new SwordMagicSwords(new Item.Properties().tab(ItemGroups.WEAPONS).stacksTo(1).setNoRepair(), 6, -2.4f));


}

package com.github.jowashere.blackclover.init;

import com.github.jowashere.blackclover.ItemGroups;
import com.github.jowashere.blackclover.Main;
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

    //Grimoires
    public static final RegistryObject<Item> GRIMOIRE_YUNO = ITEMS.register("grimoire_yuno", () -> new Item(new Item.Properties().tab(ItemGroups.GRIMOIRES).stacksTo(1)));

}

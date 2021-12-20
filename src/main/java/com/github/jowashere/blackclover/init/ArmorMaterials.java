package com.github.jowashere.blackclover.init;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.api.GenericArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ArmorMaterials {

    public static final GenericArmorMaterial BASIC_ARMOR_MATERIAL = new GenericArmorMaterial(Main.MODID + ":basic_armor", 100, new int[] { 2, 6, 5, 2 }, 4, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, () -> Ingredient.of(Items.LEATHER));


}

package com.github.jowashere.blackclover.init;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemGroups {

    public static final ItemGroup EQUIPMENT = new ItemGroup("BCEquipment")
    {
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(ItemInit.YAMIS_KATANA.get());
        }
    };

}

package com.github.jowashere.blackclover;

import com.github.jowashere.blackclover.init.ItemInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemGroups {

    public static final ItemGroup GRIMOIRES = new ItemGroup("bcGrimoires")
    {
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(ItemInit.GRIMOIRE_YUNO.get());
        }
    };

}

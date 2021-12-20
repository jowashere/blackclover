package com.github.jowashere.blackclover.items.weapons;

import com.github.jowashere.blackclover.init.GenericItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class YamisKatana extends SwordItem {

    public YamisKatana(Properties properties, int damage, float speed) {
        super(GenericItemTier.WEAPON, damage, speed, properties);
    }

    @Override
    public int getEnchantmentValue()
    {
        return 14;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return true;
    }

}

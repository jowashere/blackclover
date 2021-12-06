package com.github.jowashere.blackclover.items.weapons;

import com.github.jowashere.blackclover.init.GenericItemTier;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ResourceLocation;

public class YamisKatana extends SwordItem {

    private IItemPropertyGetter antiMagicProperty = (itemStack, world, livingEntity) ->
    {
        float antiMagic = 0;

        antiMagic = itemStack.getOrCreateTag().getBoolean("antimagic") ? 1 : 0;

        return antiMagic;
    };

    private IItemPropertyGetter blackMode = (itemStack, world, livingEntity) ->
    {
        if(livingEntity == null)
            return 0;

        float blackmode = 0;

        boolean isBlackModeActive = livingEntity.getPersistentData().getBoolean("blackclover_black_mode");

        boolean isAntiMagic = itemStack.getOrCreateTag().getBoolean("antimagic");

        boolean holdingItem;

        boolean mainHandFlag = livingEntity.getMainHandItem() == itemStack;
        boolean offHandFlag = livingEntity.getOffhandItem() == itemStack;
        holdingItem = mainHandFlag || offHandFlag;

        blackmode =  isBlackModeActive && holdingItem && isAntiMagic? 1 : 0;

        return blackmode;
    };

    public YamisKatana(Properties properties, int damage, float speed) {
        super(GenericItemTier.WEAPON, damage, speed, properties);
        ItemModelsProperties.register(this, new ResourceLocation("antimagic"), this.antiMagicProperty);
        ItemModelsProperties.register(this, new ResourceLocation("blackmode"), this.blackMode);
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

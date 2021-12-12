package com.github.jowashere.blackclover.items.weapons;

import net.minecraft.item.IItemPropertyGetter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WeaponProperties {

    @OnlyIn(Dist.CLIENT)
    public static IItemPropertyGetter antiMagicProperty = (itemStack, world, livingEntity) ->
    {
        float antiMagic = 0;

        antiMagic = itemStack.getOrCreateTag().getBoolean("antimagic") ? 1 : 0;

        return antiMagic;
    };

    @OnlyIn(Dist.CLIENT)
    public static IItemPropertyGetter blackMode = (itemStack, world, livingEntity) ->
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

}

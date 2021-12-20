package com.github.jowashere.blackclover.items.armors;

import com.github.jowashere.blackclover.client.renderer.models.armors.GoldenDawnUniModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class GoldenDawnUniArmorItem extends BasicArmorItem {


    public GoldenDawnUniArmorItem(String name, EquipmentSlotType type)
    {
        this(name, type, false);
    }

    public GoldenDawnUniArmorItem(String name, EquipmentSlotType type, boolean hasOverlay)
    {
        super(name, type, hasOverlay);
        this.name = name;
        this.hasOverlay = hasOverlay;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    @Nullable
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default)
    {

        A armorModel = (A) new GoldenDawnUniModel(armorSlot);

        return armorModel;
    }

}

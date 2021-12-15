package com.github.jowashere.blackclover.items.armors;

import com.github.jowashere.blackclover.client.renderer.models.armors.MageRobesModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class MageArmorItem extends BasicArmorItem {

    public MageArmorItem(String name, EquipmentSlotType type)
    {
        this(name, type, false);
    }

    public MageArmorItem(String name, EquipmentSlotType type, boolean hasOverlay)
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

        A armorModel = (A) new MageRobesModel(armorSlot);

        return armorModel;
    }
}

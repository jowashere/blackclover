package com.github.jowashere.blackclover.items.armors;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.client.renderer.models.armors.MageRobesModel;
import com.github.jowashere.blackclover.init.ArmorMaterials;
import com.github.jowashere.blackclover.init.ItemGroups;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class MageArmorItem extends DyeableArmorItem {

    private String name;
    private boolean hasOverlay;

    public MageArmorItem(String name, EquipmentSlotType type)
    {
        this(name, type, false);
    }

    public MageArmorItem(String name, EquipmentSlotType type, boolean hasOverlay)
    {
        super(ArmorMaterials.BASIC_ARMOR_MATERIAL, type, (new Properties()).tab(ItemGroups.EQUIPMENT));
        this.name = name;
        this.hasOverlay = hasOverlay;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    @Nullable
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default)
    {
        boolean showHead = armorSlot.equals(EquipmentSlotType.HEAD);
        boolean showChest = armorSlot.equals(EquipmentSlotType.CHEST);
        boolean showLegs = armorSlot.equals(EquipmentSlotType.LEGS);
        boolean showFeet = armorSlot.equals(EquipmentSlotType.FEET);

        A armorModel = (A) new MageRobesModel(showHead, showChest, showLegs, showFeet);

        return armorModel;
    }

    @Override
    @Nullable
    public String getArmorTexture(ItemStack itemStack, Entity entity, EquipmentSlotType slot, String type)
    {
        return String.format("%s:textures/models/armor/%s_%d%s.png", Main.MODID, this.name, slot == EquipmentSlotType.LEGS ? 2 : 1, type == null || !this.hasOverlay ? "" : String.format("_%s", type));
    }

}

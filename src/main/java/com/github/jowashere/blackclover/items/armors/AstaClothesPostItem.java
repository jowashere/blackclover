package com.github.jowashere.blackclover.items.armors;

import com.github.jowashere.blackclover.client.renderer.models.armors.AstaClothesPostModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class AstaClothesPostItem extends BasicArmorItem {


    public AstaClothesPostItem(String name, EquipmentSlotType type)
    {
        this(name, type, false);
    }

    public AstaClothesPostItem(String name, EquipmentSlotType type, boolean hasOverlay)
    {
        super(name, type, hasOverlay);
        this.name = name;
        this.hasOverlay = hasOverlay;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(new StringTextComponent("Post-Timeskip"));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    @Nullable
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default)
    {

        A armorModel = (A) new AstaClothesPostModel(armorSlot);

        return armorModel;
    }

}

package com.github.jowashere.blackclover.api;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public class GenericArmorMaterial implements IArmorMaterial {

    private static final int[] MAX_DAMAGE = new int[]
            {
                    13, 15, 16, 11
            };
    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final Ingredient repairMaterial;

    public GenericArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness, Supplier<Ingredient> supplier)
    {
        this.name = name;
        this.maxDamageFactor = maxDamageFactor;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.repairMaterial = supplier.get();
    }

    @Override
    public int getDurabilityForSlot(EquipmentSlotType slotIn)
    {
        return MAX_DAMAGE[slotIn.getIndex()] * this.maxDamageFactor;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlotType slotIn)
    {
        return this.damageReductionAmountArray[slotIn.getIndex()];
    }

    @Override
    public int getEnchantmentValue()
    {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound()
    {
        return this.soundEvent;
    }

    @Override
    public Ingredient getRepairIngredient()
    {
        return this.repairMaterial;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public float getToughness()
    {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance()
    {
        return 0;
    }

}

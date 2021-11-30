package com.github.jowashere.blackclover.init;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

public enum GenericItemTier implements IItemTier {

    WEAPON(1000, 8, 0, 1, 20),
    SWORDMAGIC(0, 8, 0, 1, 20);

    private int maxUses;
    private float efficiency;
    private float attackDamage;
    private int harvestLevel;
    private int enchantability;

    private GenericItemTier(int maxUses, float efficiency, float attackDamage, int harvestLevel, int enchantability) {
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.harvestLevel = harvestLevel;
        this.enchantability = enchantability;
    }

    @Override
    public int getUses() {
        return this.maxUses;
    }

    @Override
    public float getSpeed() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    @Override
    public int getLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
    }

}

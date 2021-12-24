package com.github.jowashere.blackclover.items.weapons;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.init.GenericItemTier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.world.World;

public class MagicSwordItem extends SwordItem {

    private AbstractSpell spell = null;

    public MagicSwordItem(AbstractSpell spell, int damage, float speed, Properties properties)
    {
        super(GenericItemTier.SWORDMAGIC, damage, speed, properties);
        this.spell = spell;
    }

    @Override
    public void inventoryTick(ItemStack itemStack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        super.inventoryTick(itemStack, world, entity, itemSlot, isSelected);
        if (entity instanceof PlayerEntity && this.spell != null)
        {
            PlayerEntity owner = (PlayerEntity) entity;

            boolean deleteSword = true;

            for (AbstractSpell spell : BCMRegistry.SPELLS.getValues()) {
                if (spell.isToggle()) {
                    String nbtName = spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName();

                    if(spell == null || !this.spell.equals(spell))
                        continue;

                    if (owner.getPersistentData().getBoolean(nbtName))
                        deleteSword = false;
                }
            }

            if(deleteSword)
                owner.inventory.removeItem(itemStack);
        }
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack itemStack, ItemEntity entityItem)
    {
        if(entityItem.isAlive())
            entityItem.remove();
        return true;

    }

    @Override
    public boolean isEnchantable(ItemStack stack)
    {
        return false;
    }

}

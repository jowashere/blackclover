package com.github.jowashere.blackclover.init.spells;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.entities.spells.slash.DeathScytheEntity;
import com.github.jowashere.blackclover.init.ItemInit;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class SwordSpells
{
    public static void registerSpells(BCMRegistry.SpellRegistry spellRegistry, IBCMPlugin plugin)
    {
        spellRegistry.register(new BCMSpell(plugin, "sword_slash", BCMSpell.Type.SWORD_MAGIC, 30F, 10, false, 48, 16, false, ((playerIn, modifier0, modifier1, playerCapability, manaIn) ->
        {
            if (!playerIn.level.isClientSide)
            {
                DeathScytheEntity Slash = new DeathScytheEntity(playerIn.level, playerIn, manaIn);
                Slash.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.6F, 2.5F);
                playerIn.level.addFreshEntity(Slash);
                playerIn.swing(Hand.MAIN_HAND, true);
            }
        })).setExtraSpellChecks(playerIn ->
        {
            ItemStack hand = playerIn.getItemInHand(Hand.MAIN_HAND);
            return (hand.getItem().equals(ItemInit.DEMON_DWELLER.get()));
        }).setCheckFailMsg("You need the Demon Dweller Sword for this!"));
    }
}

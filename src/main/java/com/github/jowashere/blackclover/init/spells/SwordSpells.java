package com.github.jowashere.blackclover.init.spells;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.Beapi;
import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.entities.spells.darkness.AvidyaSlashEntity;
import com.github.jowashere.blackclover.entities.spells.slash.DeathScytheEntity;
import com.github.jowashere.blackclover.init.EntityInit;
import com.github.jowashere.blackclover.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.command.arguments.NBTCompoundTagArgument;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class SwordSpells
{
    public static void registerSpells(BCMRegistry.SpellRegistry spellRegistry, IBCMPlugin plugin)
    {
        spellRegistry.register(new BCMSpell(plugin, "sword_slash",
                BCMSpell.Type.SWORD_MAGIC, 30F, 10, false, 48, 16, false, ((playerIn, modifier0, modifier1, playerCapability, manaIn) ->
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

        spellRegistry.register(new BCMSpell(plugin, "sword_absorption",
                BCMSpell.Type.SWORD_MAGIC, 0F, 10, false, 0, 0, false, ((playerIn, modifier0, modifier1, playerCapability, manaIn) ->
        {
            ItemStack hand = playerIn.getItemInHand(Hand.MAIN_HAND);
            CompoundNBT nbt;
            if (hand.hasTag())
            {
                nbt = hand.getTag();
                if (nbt.getInt("Absorbtion") == 1)
                {
                    if (!playerIn.level.isClientSide) {
                        AvidyaSlashEntity entity = new AvidyaSlashEntity(playerIn.level, playerIn, manaIn);
                        entity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.6F, 2.5F);
                        playerIn.level.addFreshEntity(entity);
                        playerIn.swing(Hand.MAIN_HAND, true);
                    }
                }
                else
                {
                    EntityRayTraceResult trace = (EntityRayTraceResult) Beapi.rayTraceBlocksAndEntities(playerIn.getEntity(), 24, 0.2F);
                    Entity traceEntity = trace.getEntity();
                    traceEntity.remove();
                    nbt.putInt("Absorbtion", 1);
                }
            }
            else
            {
                nbt = new CompoundNBT();
                EntityRayTraceResult trace = (EntityRayTraceResult) Beapi.rayTraceBlocksAndEntities(playerIn.getEntity(), 24, 0.2F);
                Entity traceEntity = trace.getEntity();
                if (traceEntity.equals(EntityInit.ENTITIES))
                {
                    nbt.putInt("Absorbtion", 1);
                    traceEntity.remove();
                }
            }
        }
        )).setExtraSpellChecks(playerIn ->
        {
            ItemStack hand = playerIn.getItemInHand(Hand.MAIN_HAND);
            return (hand.getItem().equals(ItemInit.DEMON_SLAYER.get()));
        }).setCheckFailMsg("you need the demon slayer sword for this!"));
    }
}

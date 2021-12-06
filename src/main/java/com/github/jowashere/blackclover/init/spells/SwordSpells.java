package com.github.jowashere.blackclover.init.spells;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.Beapi;
import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.api.internal.entities.spells.AbstractAntiMagicProjectileEntity;
import com.github.jowashere.blackclover.api.internal.entities.spells.AbstractSpellProjectileEntity;
import com.github.jowashere.blackclover.entities.spells.slash.DeathScytheEntity;
import com.github.jowashere.blackclover.init.ItemInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraftforge.registries.ForgeRegistries;

public class SwordSpells
{
    public static void registerSpells(BCMRegistry.SpellRegistry spellRegistry, IBCMPlugin plugin)
    {
        spellRegistry.register(new BCMSpell(plugin, "sword_slash",
                BCMSpell.Type.SWORD_MAGIC, 30F, 10, false, 48, 16, false, ((playerIn, modifier0, modifier1, playerCapability, manaIn) ->
        {
            if (!playerIn.level.isClientSide)
            {
                DeathScytheEntity Slash = new DeathScytheEntity(playerIn.level, playerIn, manaIn); //TODO make custom entity for this spell
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

                        EntityType type = ForgeRegistries.ENTITIES.getValue(ResourceLocation.tryParse(nbt.getString("StoredSpell")));
                        AbstractSpellProjectileEntity spell = (AbstractSpellProjectileEntity) type.create(playerIn.level);
                        spell.moveTo(playerIn.getX(), playerIn.getEyeY() - (double)0.1F, playerIn.getZ());
                        spell.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.6F, 2.5F);
                        playerIn.level.addFreshEntity(spell);
                        playerIn.swing(Hand.MAIN_HAND, true);
                    }
                    nbt.putInt("Absorbtion", 0);
                }
                else
                {
                    EntityRayTraceResult trace = (EntityRayTraceResult) Beapi.rayTraceBlocksAndEntities(playerIn.getEntity(), 24, 0.2F);
                    Entity traceEntity = trace.getEntity();
                    if (traceEntity instanceof AbstractSpellProjectileEntity && !(traceEntity instanceof AbstractAntiMagicProjectileEntity))
                    {
                        nbt.putInt("Absorbtion", 1);
                        nbt.putString("StoredSpell", traceEntity.getType().getRegistryName().toString());
                        traceEntity.remove();
                    }
                }
            }
            else
            {
                nbt = new CompoundNBT();
                EntityRayTraceResult trace = (EntityRayTraceResult) Beapi.rayTraceBlocksAndEntities(playerIn.getEntity(), 24, 0.2F);
                Entity traceEntity = trace.getEntity();
                if (traceEntity instanceof AbstractSpellProjectileEntity && !(traceEntity instanceof AbstractAntiMagicProjectileEntity))
                {
                    nbt.putInt("Absorbtion", 1);
                    nbt.putString("StoredSpell", traceEntity.getType().getRegistryName().toString());
                    traceEntity.remove();
                }
            }
        }
        )).setExtraSpellChecks(playerIn ->
        {
            ItemStack hand = playerIn.getItemInHand(Hand.MAIN_HAND);
            return (hand.getItem().equals(ItemInit.DEMON_DWELLER.get()));
        }).setCheckFailMsg("you need the demon dweller sword for this!"));
    }
}

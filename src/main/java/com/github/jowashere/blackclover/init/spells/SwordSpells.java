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
        spellRegistry.register(new BCMSpell(plugin, "origin_flash",
                BCMSpell.Type.SWORD_MAGIC, 30F, 30, false, 48, 16, false, ((playerIn, modifier0, modifier1, playerCapability, manaIn) ->
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
        }).setCheckFailMsg("You need the Demon Dweller Sword for this!").setUnlockLevel(1));

        spellRegistry.register(new BCMSpell(plugin, "sword_absorption",
                BCMSpell.Type.SWORD_MAGIC, 0F, 60, false, 0, 0, false, ((playerIn, modifier0, modifier1, playerCapability, manaIn) ->
        {

            ItemStack hand = playerIn.getItemInHand(Hand.MAIN_HAND);
            CompoundNBT nbt;
            if (hand.hasTag())
            {
                nbt = hand.getTag();
                if (nbt.getInt("absorption") == 1)
                {
                    if (!playerIn.level.isClientSide) {

                        EntityType type = ForgeRegistries.ENTITIES.getValue(ResourceLocation.tryParse(nbt.getString("stored_spell")));
                        AbstractSpellProjectileEntity spell = (AbstractSpellProjectileEntity) type.create(playerIn.level);
                        spell.setDamageTier(nbt.getInt("stored_damage_tier"));
                        spell.setBaseDamage(nbt.getFloat("stored_base_damage"));
                        spell.moveTo(playerIn.getX(), playerIn.getEyeY() - (double)0.1F, playerIn.getZ());
                        spell.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.6F, 2.5F);
                        playerIn.level.addFreshEntity(spell);
                        playerIn.swing(Hand.MAIN_HAND, true);
                    }
                    nbt.putInt("absorption", 0);
                }
                else
                {
                    EntityRayTraceResult trace = (EntityRayTraceResult) Beapi.rayTraceBlocksAndEntities(playerIn.getEntity(), 24, 0.2F);
                    Entity traceEntity = trace.getEntity();
                    if (traceEntity instanceof AbstractSpellProjectileEntity && !(traceEntity instanceof AbstractAntiMagicProjectileEntity))
                    {
                        nbt.putInt("absorption", 1);
                        nbt.putString("stored_spell", traceEntity.getType().getRegistryName().toString());
                        nbt.putInt("stored_damage_tier", ((AbstractSpellProjectileEntity) traceEntity).getDamageTier());
                        nbt.putFloat("stored_base_damage", ((AbstractSpellProjectileEntity) traceEntity).getBaseDamage());
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
                    nbt.putInt("absorption", 1);
                    nbt.putString("stored_spell", traceEntity.getType().getRegistryName().toString());
                    nbt.putInt("stored_damage_tier", ((AbstractSpellProjectileEntity) traceEntity).getDamageTier());
                    nbt.putFloat("stored_base_damage", ((AbstractSpellProjectileEntity) traceEntity).getBaseDamage());
                    traceEntity.remove();
                }
            }
        }
        )).setExtraSpellChecks(playerIn ->
        {
            ItemStack hand = playerIn.getItemInHand(Hand.MAIN_HAND);
            return (hand.getItem().equals(ItemInit.DEMON_DWELLER.get()));
        }).setCheckFailMsg("You need the Demon Dweller Sword for this!").setUnlockLevel(20));
    }
}

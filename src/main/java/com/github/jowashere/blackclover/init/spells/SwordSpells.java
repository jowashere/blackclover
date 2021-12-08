package com.github.jowashere.blackclover.init.spells;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.Beapi;
import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.api.internal.entities.spells.AbstractAntiMagicProjectileEntity;
import com.github.jowashere.blackclover.api.internal.entities.spells.AbstractSpellProjectileEntity;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.entities.spells.sword.OriginFlashEntity;
import com.github.jowashere.blackclover.init.ItemInit;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class SwordSpells
{
    public static void registerSpells(BCMRegistry.SpellRegistry spellRegistry, IBCMPlugin plugin)
    {
        spellRegistry.register(new BCMSpell(plugin, "origin_flash",
                BCMSpell.Type.SWORD_MAGIC, 20F, 35, true, 48, 16, false, ((playerIn, modifier0, modifier1, playerCapability, manaIn) ->
        {
            if (!playerIn.level.isClientSide)
            {
                OriginFlashEntity entity = new OriginFlashEntity(playerIn.level, playerIn, manaIn);
                entity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.6F, 2.5F);
                playerIn.level.addFreshEntity(entity);
                playerIn.swing(Hand.MAIN_HAND, true);
            }
        })).setExtraSpellChecks(playerIn ->
        {
            ItemStack hand = playerIn.getItemInHand(Hand.MAIN_HAND);
            return (hand.getItem().equals(ItemInit.DEMON_DWELLER.get()));
        }).setCheckFailMsg("You need the Demon Dweller Sword for this!").setUnlockLevel(1));

        spellRegistry.register(new BCMSpell(plugin, "origin_flash_barrage",
                BCMSpell.Type.SWORD_MAGIC, 120F, 600, false, 48, 16, false, ((playerIn, modifier0, modifier1, playerCapability, manaIn) ->
        {
            if (!playerIn.level.isClientSide)
            {
                OriginFlashEntity entity = new OriginFlashEntity(playerIn.level, playerIn, manaIn);
                entity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.6F, 2.5F);
                playerIn.level.addFreshEntity(entity);
                playerIn.swing(Hand.MAIN_HAND, true);

                for(int i = 0; i < 15; i++) {
                    BCMHelper.waitThen(playerIn.level, i * 2, ()-> {
                        OriginFlashEntity entity2 = new OriginFlashEntity(playerIn.level, playerIn, manaIn);
                        entity2.shoot((float) (playerIn.getLookAngle().x + (Math.random() * 0.45) - 0.275), (float) (playerIn.getLookAngle().y + (Math.random() * 0.4) - 0.25), (float) (playerIn.getLookAngle().z + (Math.random() * 0.45) - 0.275), 1.6F, 0);
                        playerIn.level.addFreshEntity(entity2);
                        playerIn.swing(Hand.MAIN_HAND, true);
                    });
                }
            }
        })).setExtraSpellChecks(playerIn ->
        {
            ItemStack hand = playerIn.getItemInHand(Hand.MAIN_HAND);
            return (hand.getItem().equals(ItemInit.DEMON_DWELLER.get()));
        }).setCheckFailMsg("You need the Demon Dweller Sword for this!").setUnlockLevel(30));

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
                        spell.setOwner(playerIn);
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

        spellRegistry.register(new BCMSpell(plugin, "causality_break_sword", BCMSpell.Type.SWORD_MAGIC, 30F, 200, false, 0, 32, false, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

            LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

            List<LivingEntity> entities= BCMHelper.GetEntitiesNear(playerIn.blockPosition(), playerIn.level, (int) (5 + (player_cap.ReturnMagicLevel() / 10)), LivingEntity.class);
            entities.remove(playerIn);

            EntityRayTraceResult rayTraceResult = BCMHelper.RayTraceEntities(playerIn, 6);

            if(!(rayTraceResult.getEntity() instanceof LivingEntity))
            {
                LivingEntity entity = (LivingEntity) rayTraceResult.getEntity();
                entity.removeAllEffects();
                ((ServerWorld) playerIn.level).sendParticles(ParticleTypes.SPIT, entity.getX(), entity.getY(), entity.getZ(), (int) 25, 0, 0, 0, 0.25);
            }

        }).setExtraSpellChecks((playerIn -> {
            ItemStack mainItem = playerIn.getItemInHand(Hand.MAIN_HAND);

            return (mainItem.getItem().equals(ItemInit.DEMON_DESTROYER.get()));
        })).setCheckFailMsg("You need the Demon Destroyer Sword for this!").setUnlockLevel(16));
    }
}

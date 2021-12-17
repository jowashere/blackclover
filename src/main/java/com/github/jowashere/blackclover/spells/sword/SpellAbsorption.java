package com.github.jowashere.blackclover.spells.sword;

import com.github.jowashere.blackclover.api.Beapi;
import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.api.internal.entities.spells.AbstractAntiMagicProjectileEntity;
import com.github.jowashere.blackclover.api.internal.entities.spells.AbstractSpellProjectileEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.init.ItemInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraftforge.registries.ForgeRegistries;

public class SpellAbsorption extends AbstractSpell {

    public SpellAbsorption(IBCMPlugin plugin) {
        super(plugin, "sword_absorption", AttributeInit.SWORD);

        this.setManaCost(20F);
        this.setCooldown(60);
        this.setUnlockLevel(20);
        this.setUV(96, 48);

        this.action = this::action;
        this.extraCheck = this::extraCheck;
        this.setCheckFailMsg("You need the Demon Dweller Sword for this!");

    }

    private void action(LivingEntity caster, float manaIn) {
        ItemStack hand = caster.getItemInHand(Hand.MAIN_HAND);
        CompoundNBT nbt;
        if (hand.hasTag())
        {
            nbt = hand.getTag();
            if (nbt.getInt("absorption") == 1)
            {
                if (!caster.level.isClientSide) {

                    EntityType type = ForgeRegistries.ENTITIES.getValue(ResourceLocation.tryParse(nbt.getString("stored_spell")));
                    AbstractSpellProjectileEntity spell = (AbstractSpellProjectileEntity) type.create(caster.level);
                    spell.setOwner(caster);
                    spell.setDamageTier(nbt.getInt("stored_damage_tier"));
                    spell.setBaseDamage(nbt.getFloat("stored_base_damage"));
                    spell.moveTo(caster.getX(), caster.getEyeY() - (double)0.1F, caster.getZ());
                    spell.shootFromRotation(caster, caster.xRot, caster.yRot, 0.0F, 1.6F, 2.5F);
                    caster.level.addFreshEntity(spell);
                    caster.swing(Hand.MAIN_HAND, true);
                }
                nbt.putInt("absorption", 0);
            }
            else
            {
                EntityRayTraceResult rayTraceResult = Beapi.rayTraceEntities(caster.getEntity(), 6, 0.2F);
                //Entity traceEntity = trace.getEntity();
                if (rayTraceResult.getEntity() instanceof AbstractSpellProjectileEntity && !(rayTraceResult.getEntity() instanceof AbstractAntiMagicProjectileEntity))
                {
                    nbt.putInt("absorption", 1);
                    nbt.putString("stored_spell", rayTraceResult.getEntity().getType().getRegistryName().toString());
                    nbt.putInt("stored_damage_tier", ((AbstractSpellProjectileEntity) rayTraceResult.getEntity()).getDamageTier());
                    nbt.putFloat("stored_base_damage", ((AbstractSpellProjectileEntity) rayTraceResult.getEntity()).getBaseDamage());
                    rayTraceResult.getEntity().remove();
                }
            }
        }
        else
        {
            nbt = new CompoundNBT();
            EntityRayTraceResult rayTraceResult = Beapi.rayTraceEntities(caster.getEntity(), 6, 0.2F);
            //Entity traceEntity = trace.getEntity();
            if (rayTraceResult.getEntity() instanceof AbstractSpellProjectileEntity && !(rayTraceResult.getEntity() instanceof AbstractAntiMagicProjectileEntity))
            {
                nbt.putInt("absorption", 1);
                nbt.putString("stored_spell", rayTraceResult.getEntity().getType().getRegistryName().toString());
                nbt.putInt("stored_damage_tier", ((AbstractSpellProjectileEntity) rayTraceResult.getEntity()).getDamageTier());
                nbt.putFloat("stored_base_damage", ((AbstractSpellProjectileEntity) rayTraceResult.getEntity()).getBaseDamage());
                rayTraceResult.getEntity().remove();
            }
        }
    }

    private boolean extraCheck(PlayerEntity caster){
        ItemStack hand = caster.getItemInHand(Hand.MAIN_HAND);
        return (hand.getItem().equals(ItemInit.DEMON_DWELLER.get()));
    }
}

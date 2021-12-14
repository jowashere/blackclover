package com.github.jowashere.blackclover.common.spells;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.init.ItemInit;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import com.github.jowashere.blackclover.util.helpers.SpellHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.Explosion;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;

public class EffectSpells {

    public static final int effectDuration = 55;

    public static void ThunderFiendDamage (LivingEntity caster) {
        LazyOptional<IPlayerHandler> capabilities = caster.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());

        List<LivingEntity> list = BCMHelper.GetEntitiesNear(caster.blockPosition(), caster.level, 3, LivingEntity.class);
        list.remove(caster);

        int magicLevel = BCMHelper.getMagicLevel(caster);

        list.forEach(entity ->
        {
            if(caster.canSee(entity))
                BCMHelper.doSpellDamage(caster, entity, SpellHelper.spellDamageCalc(magicLevel, 2, 4));
        });

    }

    public static void BullThrustDamage (LivingEntity caster) {
        LazyOptional<IPlayerHandler> capabilities = caster.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());

        caster.level.explode(caster, caster.getX(), caster.getY() + 1, caster.getZ(), 1, Explosion.Mode.BREAK);

        List<LivingEntity> list = BCMHelper.GetEntitiesNear(caster.blockPosition(), caster.level, 4, LivingEntity.class);
        list.remove(caster);

        int magicLevel = BCMHelper.getMagicLevel(caster);

        ItemStack offHand = caster.getItemInHand(Hand.OFF_HAND);
        boolean offHandEq = offHand.getItem().equals(ItemInit.DEMON_SLAYER.get()) || offHand.getItem().equals(ItemInit.DEMON_DWELLER.get());

        list.forEach(entity ->
        {
            if(caster.canSee(entity))
                BCMHelper.doSpellDamage(caster, entity, SpellHelper.spellDamageCalc(magicLevel, 1, offHandEq ? 6 : 4));

        });
    }

    public static void BlackMeteoriteDamage (LivingEntity caster) {

        caster.level.explode(caster, caster.getX(), caster.getY() + 1, caster.getZ(), 3, Explosion.Mode.BREAK);

        int magicLevel = BCMHelper.getMagicLevel(caster);

        List<LivingEntity> list = BCMHelper.GetEntitiesNear(caster.blockPosition(), caster.level, 6, LivingEntity.class);
        list.remove(caster);

        list.forEach(entity ->
        {
            if(caster.canSee(entity))
                BCMHelper.doSpellDamage(caster, entity, SpellHelper.spellDamageCalc(magicLevel, 3, 8));
        });

    }

}

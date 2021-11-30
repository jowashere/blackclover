package com.github.jowashere.blackclover.common.spells;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.init.ItemInit;
import com.github.jowashere.blackclover.init.ModAttributes;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.PacketToggleInfusionBoolean;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import com.github.jowashere.blackclover.util.helpers.SpellHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import org.w3c.dom.Attr;

import java.util.List;
import java.util.UUID;

public class EffectSpells {

    public static final int effectDuration = 55;

    public static void ThunderFiendDamage (PlayerEntity playerIn) {
        LazyOptional<IPlayerHandler> capabilities = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());

        List<LivingEntity> list = BCMHelper.GetEntitiesNear(playerIn.blockPosition(), playerIn.level, 3, LivingEntity.class);
        list.remove(playerIn);

        list.forEach(entity ->
        {
            if(playerIn.canSee(entity))
                entity.hurt(DamageSource.playerAttack(playerIn), SpellHelper.spellDamageCalcP(playerIn, 2, 4));
        });

    }

    public static void BullThrustDamage (PlayerEntity playerIn) {
        LazyOptional<IPlayerHandler> capabilities = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());

        playerIn.level.explode(playerIn, playerIn.getX(), playerIn.getY() + 1, playerIn.getZ(), 1, Explosion.Mode.BREAK);

        List<LivingEntity> list = BCMHelper.GetEntitiesNear(playerIn.blockPosition(), playerIn.level, 4, LivingEntity.class);
        list.remove(playerIn);

        //ItemStackMixin mainHand = playerIn.getItemInHand(Hand.MAIN_HAND);
        ItemStack offHand = playerIn.getItemInHand(Hand.OFF_HAND);

        boolean offHandEq = offHand.getItem().equals(ItemInit.DEMON_SLAYER.get()) || offHand.getItem().equals(ItemInit.DEMON_DWELLER.get());

        list.forEach(entity ->
        {
            if(playerIn.canSee(entity))
                entity.hurt(DamageSource.playerAttack(playerIn), SpellHelper.spellDamageCalcP(playerIn, 1, offHandEq ? 6 : 4));
        });
    }

    public static void BlackMeteoriteDamage (PlayerEntity playerIn) {
        LazyOptional<IPlayerHandler> capabilities = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());

        playerIn.level.explode(playerIn, playerIn.getX(), playerIn.getY() + 1, playerIn.getZ(), 3, Explosion.Mode.BREAK);

        List<LivingEntity> list = BCMHelper.GetEntitiesNear(playerIn.blockPosition(), playerIn.level, 6, LivingEntity.class);
        list.remove(playerIn);

        list.forEach(entity ->
        {
            if(playerIn.canSee(entity))
                entity.hurt(DamageSource.playerAttack(playerIn), SpellHelper.spellDamageCalcP(playerIn, 3, 8));
        });

    }

    private static AttributeModifier getManaSkinModifier(PlayerEntity playerEntity) {
        LazyOptional<IPlayerHandler> playerInCap = playerEntity.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());
        return new AttributeModifier(UUID.fromString("759f619a-e913-46e7-8c7a-58b846e712a0"), "Mana Skin Modifier"
                , (player_cap.ReturnMagicLevel()/100)*0.45, AttributeModifier.Operation.ADDITION);
    }

    private static AttributeModifier getReinforcementModifier(PlayerEntity playerEntity) {
        LazyOptional<IPlayerHandler> playerInCap = playerEntity.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());
        return new AttributeModifier(UUID.fromString("9ee6a244-30e0-4cf0-9ab6-f94ab6738cc0"), "Reinforcement Modifier",
                3 + (player_cap.ReturnMagicLevel()/15), AttributeModifier.Operation.ADDITION);

    }

    private static AttributeModifier getReinforcementSpeedModifier(PlayerEntity playerEntity) {
        LazyOptional<IPlayerHandler> playerInCap = playerEntity.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());
        return new AttributeModifier(UUID.fromString("c43bdc53-4b8e-4a1e-8bca-6358c9bc210f"), "Manaless Modifier",
                1 + (player_cap.ReturnMagicLevel()/25), AttributeModifier.Operation.ADDITION);

    }

}

package com.github.jowashere.blackclover.spells.antimagic;

import com.github.jowashere.blackclover.api.internal.AbstractToggleSpell;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.init.ModAttributes;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.spells.PacketIntSpellNBTSync;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.UUID;

public class BlackMode extends AbstractToggleSpell {

    public BlackMode() {
        super("black_mode", AttributeInit.ANTI_MAGIC);

        this.setManaCost(0.75F);
        this.setCooldown(12400);
        this.setUnlockLevel(30);
        this.setToggleTimer(2800);
        this.setUV(0, 112);

        this.action = this::action;
        this.onStartEvent = this::onStart;
        this.onCancelEvent = this::onEnd;
        this.extraCheck = this::extraCheck;

        this.setCheckFailMsg("An Anti-Magic Sword needs to be in hand!");
    }

    public void action(LivingEntity caster, float manaIn) {
        caster.fallDistance = 0;
    }

    public void onStart(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {

            if(!caster.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(getBlackModeSpeedModifier(caster)))
                caster.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(getBlackModeSpeedModifier(caster));

            if(!caster.getAttribute(Attributes.ARMOR).hasModifier(getBlackModeModifier(caster)))
                caster.getAttribute(Attributes.ARMOR).addTransientModifier(getBlackModeModifier(caster));

            if(!caster.getAttribute(Attributes.ARMOR_TOUGHNESS).hasModifier(getBlackModeModifier(caster)))
                caster.getAttribute(Attributes.ARMOR_TOUGHNESS).addTransientModifier(getBlackModeModifier(caster));

            if(!caster.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getBlackModeStrengthModifier(caster)))
                caster.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(getBlackModeStrengthModifier(caster));

            if(!caster.getAttribute(ModAttributes.DAMAGE_REDUCTION.get()).hasModifier(getBlackModeDamageResModifier(caster)))
                caster.getAttribute(ModAttributes.DAMAGE_REDUCTION.get()).addTransientModifier(getBlackModeDamageResModifier(caster));

        }
    }

    public void onEnd(LivingEntity caster) {
        caster.getPersistentData().putInt("black_mode_fatigue", 900);
        NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(()-> (ServerPlayerEntity) caster), new PacketIntSpellNBTSync(caster.getId(), "black_mode_fatigue", 900));

        if(caster.getAttribute(Attributes.ARMOR).hasModifier(getBlackModeModifier(caster)))
            caster.getAttribute(Attributes.ARMOR).removeModifier(getBlackModeModifier(caster));

        if(caster.getAttribute(Attributes.ARMOR_TOUGHNESS).hasModifier(getBlackModeModifier(caster)))
            caster.getAttribute(Attributes.ARMOR_TOUGHNESS).removeModifier(getBlackModeModifier(caster));

        if(caster.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(getBlackModeSpeedModifier(caster)))
            caster.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(getBlackModeSpeedModifier(caster));

        if(caster.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getBlackModeStrengthModifier(caster)))
            caster.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(getBlackModeStrengthModifier(caster));

        if(caster.getAttribute(ModAttributes.DAMAGE_REDUCTION.get()).hasModifier(getBlackModeDamageResModifier(caster)))
            caster.getAttribute(ModAttributes.DAMAGE_REDUCTION.get()).removeModifier(getBlackModeDamageResModifier(caster));
    }

    public boolean extraCheck(PlayerEntity caster) {
        ItemStack mainItem = caster.getItemInHand(Hand.MAIN_HAND);
        boolean holdingAnti = (mainItem.getItem() instanceof SwordItem);
        boolean isAnti = mainItem.getOrCreateTag().getBoolean("antimagic");
        return isAnti && holdingAnti;
    }

    private static AttributeModifier getBlackModeModifier(LivingEntity caster) {

        int magicLevel = BCMHelper.getMagicLevel(caster);

        return new AttributeModifier(UUID.fromString("2096d841-4fe1-4678-ba90-80ff10f115b8"), "Black Mode Modifier"
                , 5 + ((float)magicLevel/15), AttributeModifier.Operation.ADDITION);
    }

    private static AttributeModifier getBlackModeSpeedModifier(LivingEntity caster) {

        int magicLevel = BCMHelper.getMagicLevel(caster);

        return new AttributeModifier(UUID.fromString("92ad75d4-778e-4c24-a473-40e7cf3a94c7"), "Black Mode Speed Modifier",
                0.035 * magicLevel, AttributeModifier.Operation.MULTIPLY_BASE);
    }

    private static AttributeModifier getBlackModeDamageResModifier(LivingEntity caster) {

        int magicLevel = BCMHelper.getMagicLevel(caster);

        return new AttributeModifier(UUID.fromString("50a53c0c-5dda-4784-8511-a4f8dbee8218"), "Black Mode Attack Damage Modifier",
                ((float)magicLevel/100)*0.45, AttributeModifier.Operation.ADDITION);

    }

    private static AttributeModifier getBlackModeStrengthModifier(LivingEntity caster) {

        int magicLevel = BCMHelper.getMagicLevel(caster);

        return new AttributeModifier(UUID.fromString("760496d9-3363-4cf7-bba6-2e13efc11a63"), "Black Mode Damage Resistance Modifier",
                5 + ((float)magicLevel/10), AttributeModifier.Operation.ADDITION);

    }
}

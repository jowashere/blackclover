package com.github.jowashere.blackclover.events.passives;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.events.bcevents.MagicLevelChangeEvent;
import com.github.jowashere.blackclover.init.ModAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

public class ManalessPassives {

    @Mod.EventBusSubscriber(modid = Main.MODID)
    public static class CommonEvents
    {
        private static final AttributeModifier STEP_HEIGHT = new AttributeModifier(UUID.fromString("ad388521-c053-4a67-a0d9-ff57379a2c68"), "Step Height Multiplier", 1, AttributeModifier.Operation.ADDITION);
        private static final AttributeModifier FALL_RESISTANCE = new AttributeModifier(UUID.fromString("856390ba-5529-45e5-9988-2a0a980d9d87"), "Fall Resistance", 7, AttributeModifier.Operation.ADDITION);

        @SubscribeEvent
        public static void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
            if (!(event.getEntityLiving() instanceof PlayerEntity))
                return;

            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            World world = player.level;

            if(world.isClientSide)
                return;

            LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());

            if (!playercap.HasManaBoolean())
            {
                if(player.isSprinting()){
                    if(!player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(getSpeedModifier(player)))
                        player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(getSpeedModifier(player));
                }else {
                    if(player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(getSpeedModifier(player)))
                        player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(getSpeedModifier(player));
                }

                if(!player.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getStrengthModifier(player)))
                    player.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(getStrengthModifier(player));

                if(!player.getAttribute(ModAttributes.DAMAGE_REDUCTION.get()).hasModifier(getResistanceModifier(player)))
                    player.getAttribute(ModAttributes.DAMAGE_REDUCTION.get()).addTransientModifier(getResistanceModifier(player));

                if(!player.getAttribute(ModAttributes.STEP_HEIGHT.get()).hasModifier(STEP_HEIGHT))
                    player.getAttribute(ModAttributes.STEP_HEIGHT.get()).addTransientModifier(STEP_HEIGHT);

                if(!player.getAttribute(ModAttributes.FALL_RESISTANCE.get()).hasModifier(FALL_RESISTANCE))
                    player.getAttribute(ModAttributes.FALL_RESISTANCE.get()).addTransientModifier(FALL_RESISTANCE);

                if(!player.getAttribute(Attributes.ARMOR).hasModifier(getArmourModifier(player)))
                    player.getAttribute(Attributes.ARMOR).addTransientModifier(getArmourModifier(player));

                if(!player.getAttribute(Attributes.ARMOR_TOUGHNESS).hasModifier(getArmourModifier(player)))
                    player.getAttribute(Attributes.ARMOR_TOUGHNESS).addTransientModifier(getArmourModifier(player));

                if(playercap.getMagicLevel() >= 55)
                    player.fallDistance = 0;

                if(!player.isCrouching()){
                    if(!player.getAttribute(ModAttributes.JUMP_HEIGHT.get()).hasModifier(getJumpModifier(player)) && !player.isCrouching())
                        player.getAttribute(ModAttributes.JUMP_HEIGHT.get()).addTransientModifier(getJumpModifier(player));
                }else {
                    if(player.getAttribute(ModAttributes.JUMP_HEIGHT.get()).hasModifier(getJumpModifier(player)))
                        player.getAttribute(ModAttributes.JUMP_HEIGHT.get()).removeModifier(getJumpModifier(player));
                }

            } else
                removeAttributes(player);
        }

        @SubscribeEvent
        public static void onLevelChange(MagicLevelChangeEvent event){

            World world = event.getPlayer().level;

            if(world.isClientSide)
                return;

            removeAttributes(event.getPlayer());
        }

        public static void removeAttributes(PlayerEntity player) {
            if(player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(getSpeedModifier(player)))
                player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(getSpeedModifier(player));

            if(player.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getStrengthModifier(player)))
                player.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(getStrengthModifier(player));

            if(player.getAttribute(ModAttributes.DAMAGE_REDUCTION.get()).hasModifier(getResistanceModifier(player)))
                player.getAttribute(ModAttributes.DAMAGE_REDUCTION.get()).removeModifier(getResistanceModifier(player));

            if(player.getAttribute(Attributes.ARMOR).hasModifier(getArmourModifier(player)))
                player.getAttribute(Attributes.ARMOR).removeModifier(getArmourModifier(player));

            if(player.getAttribute(Attributes.ARMOR_TOUGHNESS).hasModifier(getArmourModifier(player)))
                player.getAttribute(Attributes.ARMOR_TOUGHNESS).removeModifier(getArmourModifier(player));

            if(player.getAttribute(ModAttributes.STEP_HEIGHT.get()).hasModifier(STEP_HEIGHT))
                player.getAttribute(ModAttributes.STEP_HEIGHT.get()).removeModifier(STEP_HEIGHT);

            if(player.getAttribute(ModAttributes.FALL_RESISTANCE.get()).hasModifier(FALL_RESISTANCE))
                player.getAttribute(ModAttributes.FALL_RESISTANCE.get()).removeModifier(FALL_RESISTANCE);

            if(player.getAttribute(ModAttributes.JUMP_HEIGHT.get()).hasModifier(getJumpModifier(player)))
                player.getAttribute(ModAttributes.JUMP_HEIGHT.get()).removeModifier(getJumpModifier(player));

        }

        private static AttributeModifier getArmourModifier(PlayerEntity playerEntity) {
            LazyOptional<IPlayerHandler> playerInCap = playerEntity.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());
            return new AttributeModifier(UUID.fromString("2096d841-4fe1-4678-ba90-80ff10f115b8"), "Black Mode Armour Modifier"
                    , 4 + ((float)player_cap.getMagicLevel()/20), AttributeModifier.Operation.ADDITION);
        }

        private static AttributeModifier getResistanceModifier(PlayerEntity playerEntity) {
            LazyOptional<IPlayerHandler> playerInCap = playerEntity.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());
            return new AttributeModifier(UUID.fromString("9b772daa-fed8-4f82-bee3-79879d7ba66c"), "Manaless Resistance Modifier",
                    (player_cap.getMagicLevel()/100)*0.85, AttributeModifier.Operation.ADDITION);
        }

        private static AttributeModifier getStrengthModifier(PlayerEntity playerEntity) {
            LazyOptional<IPlayerHandler> playerInCap = playerEntity.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());
            return new AttributeModifier(UUID.fromString("e87fa53c-ba29-45e7-bda9-6f85b2b6f4f7"), "Manaless Strength Modifier",
                    3 + (player_cap.getMagicLevel()/10), AttributeModifier.Operation.ADDITION);

        }

        private static AttributeModifier getSpeedModifier(PlayerEntity playerEntity) {
            LazyOptional<IPlayerHandler> playerInCap = playerEntity.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());
            return new AttributeModifier(UUID.fromString("c43bdc53-4b8e-4a1e-8bca-6358c9bc210f"), "Manaless Speed Modifier",
                    0.025 * player_cap.getMagicLevel(), AttributeModifier.Operation.MULTIPLY_BASE);

        }

        private static AttributeModifier getJumpModifier(PlayerEntity playerEntity) {
            LazyOptional<IPlayerHandler> playerInCap = playerEntity.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());
            return new AttributeModifier(UUID.fromString("38d0ad64-d927-45c2-9ef7-713fb864d936"), "Manaless Jump Modifier",
                    2 + ((player_cap.getMagicLevel()/100)*3), AttributeModifier.Operation.ADDITION);

        }

    }




}

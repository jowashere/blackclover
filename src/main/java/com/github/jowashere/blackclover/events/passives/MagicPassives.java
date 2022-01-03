package com.github.jowashere.blackclover.events.passives;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.entities.mobs.BCEntity;
import com.github.jowashere.blackclover.events.bcevents.MagicLevelChangeEvent;
import com.github.jowashere.blackclover.init.ModAttributes;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.PacketToggleInfusionBoolean;
import com.github.jowashere.blackclover.networking.packets.mana.PacketManaSync;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.UUID;

public class MagicPassives {

    @Mod.EventBusSubscriber(modid = Main.MODID)
    public static class CommonEvents
    {
        private static final AttributeModifier STEP_HEIGHT = new AttributeModifier(UUID.fromString("ad388521-c053-4a67-a0d9-ff57379a2c68"), "Step Height Multiplier", 1, AttributeModifier.Operation.ADDITION);
        private static final AttributeModifier FALL_RESISTANCE = new AttributeModifier(UUID.fromString("e81580bf-c648-4363-9d80-038b84af2364"), "Fall Resistance", 7, AttributeModifier.Operation.ADDITION);

        @SubscribeEvent
        public static void onPlayerUpdate(LivingEvent.LivingUpdateEvent event)
        {
            if (!(event.getEntityLiving() instanceof PlayerEntity))
                return;

            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            World world = player.level;

            if(world.isClientSide)
                return;

            LazyOptional<IPlayerHandler> capabilities = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler playercap = capabilities.orElse(new PlayerCapability());

            if (playercap.HasManaBoolean())
            {
                if(playercap.returnManaSkinToggled()){
                    if(!player.getAttribute(ModAttributes.DAMAGE_REDUCTION.get()).hasModifier(getResistanceModifier(player)))
                        player.getAttribute(ModAttributes.DAMAGE_REDUCTION.get()).addTransientModifier(getResistanceModifier(player));

                    if(!player.getAttribute(ModAttributes.FALL_RESISTANCE.get()).hasModifier(FALL_RESISTANCE))
                        player.getAttribute(ModAttributes.FALL_RESISTANCE.get()).addTransientModifier(FALL_RESISTANCE);

                    if(!player.getAttribute(Attributes.ARMOR).hasModifier(getArmourModifier(player)))
                        player.getAttribute(Attributes.ARMOR).addTransientModifier(getArmourModifier(player));

                    if(!player.getAttribute(Attributes.ARMOR_TOUGHNESS).hasModifier(getArmourModifier(player)))
                        player.getAttribute(Attributes.ARMOR_TOUGHNESS).addTransientModifier(getArmourModifier(player));

                    if(playercap.getMagicLevel() >= 55)
                        player.fallDistance = 0;

                    float manaNeeded = (float) (0.2 + (0.2 * Math.sqrt(playercap.getMagicLevel() / 2)));

                    if (playercap.returnMana() > manaNeeded) {
                        playercap.addMana(-manaNeeded);
                        NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketManaSync(playercap.returnMana()));
                    } else {
                        playercap.setManaSkinToggled(false);
                        NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketToggleInfusionBoolean(1, true,false, player.getId()));
                        player.displayClientMessage(new TranslationTextComponent("spell." + Main.MODID + ".message.notenoughmana"), true);
                    }

                }else {
                    removeManaSkinAttributes(player);
                }


                if(playercap.returnReinforcementToggled()){
                    if(player.isSprinting()){
                        if(!player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(getSpeedModifier(player)))
                            player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(getSpeedModifier(player));
                    }else{
                        if(player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(getSpeedModifier(player)))
                            player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(getSpeedModifier(player));
                    }

                    if(!player.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getStrengthModifier(player)))
                        player.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(getStrengthModifier(player));

                    if(!player.getAttribute(ModAttributes.STEP_HEIGHT.get()).hasModifier(STEP_HEIGHT))
                        player.getAttribute(ModAttributes.STEP_HEIGHT.get()).addTransientModifier(STEP_HEIGHT);

                    if(!player.isCrouching()){
                        if(!player.getAttribute(ModAttributes.JUMP_HEIGHT.get()).hasModifier(getJumpModifier(player)) && !player.isCrouching())
                            player.getAttribute(ModAttributes.JUMP_HEIGHT.get()).addTransientModifier(getJumpModifier(player));
                    }else {
                        if(player.getAttribute(ModAttributes.JUMP_HEIGHT.get()).hasModifier(getJumpModifier(player)))
                            player.getAttribute(ModAttributes.JUMP_HEIGHT.get()).removeModifier(getJumpModifier(player));
                    }

                    float manaNeeded = (float) (0.2 + (0.2 * Math.sqrt(playercap.getMagicLevel() / 2)));

                    if (playercap.returnMana() > manaNeeded) {
                        playercap.addMana(-manaNeeded);
                        NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketManaSync(playercap.returnMana()));
                    } else {
                        playercap.setReinforcementToggled(false);
                        NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketToggleInfusionBoolean(2, true,false, player.getId()));
                        player.displayClientMessage(new TranslationTextComponent("spell." + Main.MODID + ".message.notenoughmana"), true);
                    }
                }else {
                    removeReinforcementAttributes(player);
                }
            } else {
                removeReinforcementAttributes(player);
                removeManaSkinAttributes(player);
            }
        }

        @SubscribeEvent
        public static void onLevelChange(MagicLevelChangeEvent event){

            World world = event.getPlayer().level;

            if(world.isClientSide)
                return;

            removeManaSkinAttributes(event.getPlayer());
            removeReinforcementAttributes(event.getPlayer());
        }

        public static void removeManaSkinAttributes(PlayerEntity player) {

            if(player.getAttribute(ModAttributes.DAMAGE_REDUCTION.get()).hasModifier(getResistanceModifier(player)))
                player.getAttribute(ModAttributes.DAMAGE_REDUCTION.get()).removeModifier(getResistanceModifier(player));

            if(player.getAttribute(ModAttributes.FALL_RESISTANCE.get()).hasModifier(FALL_RESISTANCE))
                player.getAttribute(ModAttributes.FALL_RESISTANCE.get()).removeModifier(FALL_RESISTANCE);

            if(player.getAttribute(Attributes.ARMOR).hasModifier(getArmourModifier(player)))
                player.getAttribute(Attributes.ARMOR).removeModifier(getArmourModifier(player));

            if(player.getAttribute(Attributes.ARMOR_TOUGHNESS).hasModifier(getArmourModifier(player)))
                player.getAttribute(Attributes.ARMOR_TOUGHNESS).removeModifier(getArmourModifier(player));

        }

        public static void removeReinforcementAttributes(PlayerEntity player) {

            if(player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(getSpeedModifier(player)))
                player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(getSpeedModifier(player));

            if(player.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getStrengthModifier(player)))
                player.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(getStrengthModifier(player));

            if(player.getAttribute(ModAttributes.JUMP_HEIGHT.get()).hasModifier(getJumpModifier(player)))
                player.getAttribute(ModAttributes.JUMP_HEIGHT.get()).removeModifier(getJumpModifier(player));

            if(player.getAttribute(ModAttributes.STEP_HEIGHT.get()).hasModifier(STEP_HEIGHT))
                player.getAttribute(ModAttributes.STEP_HEIGHT.get()).removeModifier(STEP_HEIGHT);

        }

        @SubscribeEvent
        public static void onEntityUpdate(LivingEvent.LivingUpdateEvent event)
        {
            if (!(event.getEntityLiving() instanceof BCEntity))
                return;

            BCEntity entity = (BCEntity) event.getEntityLiving();
            World world = entity.level;

            if(world.isClientSide)
                return;

            //Mana Skin Boosts

            if(!entity.getAttribute(ModAttributes.DAMAGE_REDUCTION.get()).hasModifier(getResistanceModifier(entity)))
                entity.getAttribute(ModAttributes.DAMAGE_REDUCTION.get()).addTransientModifier(getResistanceModifier(entity));

            if(!entity.getAttribute(ModAttributes.FALL_RESISTANCE.get()).hasModifier(FALL_RESISTANCE))
                entity.getAttribute(ModAttributes.FALL_RESISTANCE.get()).addTransientModifier(FALL_RESISTANCE);

            if(!entity.getAttribute(Attributes.ARMOR).hasModifier(getArmourModifier(entity)))
                entity.getAttribute(Attributes.ARMOR).addTransientModifier(getArmourModifier(entity));

            //Reinforcement Boosts

            if(entity.isSprinting()){
                if(!entity.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(getSpeedModifier(entity)))
                    entity.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(getSpeedModifier(entity));
            }else{
                if(entity.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(getSpeedModifier(entity)))
                    entity.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(getSpeedModifier(entity));
            }

            if(!entity.getAttribute(Attributes.ARMOR_TOUGHNESS).hasModifier(getArmourModifier(entity)))
                entity.getAttribute(Attributes.ARMOR_TOUGHNESS).addTransientModifier(getArmourModifier(entity));

            if(!entity.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getStrengthModifier(entity)))
                entity.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(getStrengthModifier(entity));

            if(!entity.getAttribute(ModAttributes.STEP_HEIGHT.get()).hasModifier(STEP_HEIGHT))
                entity.getAttribute(ModAttributes.STEP_HEIGHT.get()).addTransientModifier(STEP_HEIGHT);

            if(!entity.isCrouching()){
                if(!entity.getAttribute(ModAttributes.JUMP_HEIGHT.get()).hasModifier(getJumpModifier(entity)) && !entity.isCrouching())
                    entity.getAttribute(ModAttributes.JUMP_HEIGHT.get()).addTransientModifier(getJumpModifier(entity));
            }else {
                if(entity.getAttribute(ModAttributes.JUMP_HEIGHT.get()).hasModifier(getJumpModifier(entity)))
                    entity.getAttribute(ModAttributes.JUMP_HEIGHT.get()).removeModifier(getJumpModifier(entity));
            }

            if(BCMHelper.getMagicLevel(entity) >= 55)
                entity.fallDistance = 0;

        }


        private static AttributeModifier getArmourModifier(LivingEntity entity) {

            int magicLevel = BCMHelper.getMagicLevel(entity);

            return new AttributeModifier(UUID.fromString("33280017-3519-4ed6-8a9e-b6ac952b6cd5"), "Mana Skin Armour Modifier"
                    , 3 + ((float)magicLevel/20), AttributeModifier.Operation.ADDITION);
        }

        private static AttributeModifier getResistanceModifier(LivingEntity entity) {

            int magicLevel = BCMHelper.getMagicLevel(entity);

            return new AttributeModifier(UUID.fromString("44599c7d-fbdf-4863-b89f-bc53f23707ff"), "Mana Skin Resistance Modifier"
                    , (magicLevel/100)*0.75, AttributeModifier.Operation.ADDITION);
        }

        private static AttributeModifier getStrengthModifier(LivingEntity entity) {

            int magicLevel = BCMHelper.getMagicLevel(entity);

            return new AttributeModifier(UUID.fromString("4d54f651-cf58-4157-8138-4a206129f023"), "Reinforcement Strength Modifier",
                    1 + (magicLevel/20), AttributeModifier.Operation.ADDITION);

        }

        private static AttributeModifier getSpeedModifier(LivingEntity entity) {

            int magicLevel = BCMHelper.getMagicLevel(entity);

            return new AttributeModifier(UUID.fromString("6bec2c8f-a85b-4955-9043-a473d59031b3"), "Reinforcement Speed Modifier",
                    0.02 * magicLevel, AttributeModifier.Operation.MULTIPLY_BASE);
        }

        private static AttributeModifier getJumpModifier(LivingEntity entity) {

            int magicLevel = BCMHelper.getMagicLevel(entity);

            return new AttributeModifier(UUID.fromString("1f08e9ed-f825-4fa3-a3e1-dd7cdf32aa3a"), "Reinforcement Jump Modifier",
                    1 + ((magicLevel/100)*3), AttributeModifier.Operation.ADDITION);

        }

    }




}

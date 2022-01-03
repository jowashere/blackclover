package com.github.jowashere.blackclover.events.passives;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.events.bcevents.MagicLevelChangeEvent;
import com.github.jowashere.blackclover.init.ModAttributes;
import com.github.jowashere.blackclover.init.RaceInit;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

public class RacePassives {

    @Mod.EventBusSubscriber(modid = Main.MODID)
    public static class CommonEvents
    {
        private static final AttributeModifier ELF_BOOST = new AttributeModifier(UUID.fromString("4e0299fa-fa5b-454b-81e2-62c263a17519"), "Elf Boost", 1.3, AttributeModifier.Operation.MULTIPLY_BASE);
        private static final AttributeModifier HUMAN_BOOST = new AttributeModifier(UUID.fromString("b139c43f-34da-48d5-a388-b04ecfdd302b"), "Human Boost", 1.5, AttributeModifier.Operation.MULTIPLY_BASE);
        private static final AttributeModifier DWARF_BOOST = new AttributeModifier(UUID.fromString("5780e612-6418-4429-aad6-ad762904e15d"), "Dwarf Boost", 1.3, AttributeModifier.Operation.MULTIPLY_BASE);

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

            if(playercap.returnRace().equals(RaceInit.ELF)){
                if(!player.getAttribute(ModAttributes.MANA_STAT.get()).hasModifier(ELF_BOOST))
                    player.getAttribute(ModAttributes.MANA_STAT.get()).addTransientModifier(ELF_BOOST);
            }else {
                removeElfStats(player);
            }

            if(playercap.returnRace().equals(RaceInit.HUMAN)){
                if(!player.getAttribute(ModAttributes.MANA_CONTROL_STAT.get()).hasModifier(HUMAN_BOOST))
                    player.getAttribute(ModAttributes.MANA_CONTROL_STAT.get()).addTransientModifier(HUMAN_BOOST);
            }else {
                removeHumanStats(player);
            }

            if(playercap.returnRace().equals(RaceInit.DWARF)){
                if(!player.getAttribute(ModAttributes.PHYSICAL_STAT.get()).hasModifier(DWARF_BOOST))
                    player.getAttribute(ModAttributes.PHYSICAL_STAT.get()).addTransientModifier(DWARF_BOOST);
            }else {
                removeDwarfStats(player);
            }
        }

        @SubscribeEvent
        public static void onLevelChange(MagicLevelChangeEvent event){

            World world = event.getPlayer().level;

            if(world.isClientSide)
                return;

            removeElfStats(event.getPlayer());
        }

        public static void removeElfStats(PlayerEntity player) {

            if(player.getAttribute(ModAttributes.MANA_STAT.get()).hasModifier(ELF_BOOST))
                player.getAttribute(ModAttributes.MANA_STAT.get()).removeModifier(ELF_BOOST);

        }

        public static void removeHumanStats(PlayerEntity player) {

            if(player.getAttribute(ModAttributes.MANA_CONTROL_STAT.get()).hasModifier(ELF_BOOST))
                player.getAttribute(ModAttributes.MANA_CONTROL_STAT.get()).removeModifier(ELF_BOOST);

        }

        public static void removeDwarfStats(PlayerEntity player) {

            if(player.getAttribute(ModAttributes.PHYSICAL_STAT.get()).hasModifier(DWARF_BOOST))
                player.getAttribute(ModAttributes.PHYSICAL_STAT.get()).removeModifier(DWARF_BOOST);

        }

    }




}

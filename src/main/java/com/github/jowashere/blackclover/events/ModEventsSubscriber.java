package com.github.jowashere.blackclover.events;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.commands.impl.MagicAttributeCommand;
import com.github.jowashere.blackclover.commands.impl.MagicLevelCommand;
import com.github.jowashere.blackclover.entities.spells.wind.WindHawkEntity;
import com.github.jowashere.blackclover.init.EntityInit;
import com.github.jowashere.blackclover.init.ModAttributes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventsSubscriber {


    @SubscribeEvent
    public static void onEntityConstruct(EntityAttributeModificationEvent event)
    {
        event.getTypes().forEach(entityType -> {
            event.add(entityType, ModAttributes.FALL_RESISTANCE.get());
            event.add(entityType, ModAttributes.JUMP_HEIGHT.get());
            event.add(entityType, ModAttributes.REGEN_RATE.get());
            event.add(entityType, ModAttributes.STEP_HEIGHT.get());
            event.add(entityType, ModAttributes.DAMAGE_REDUCTION.get());
            event.add(entityType, ModAttributes.SPECIAL_DAMAGE_REDUCTION.get());
            event.add(entityType, ModAttributes.ATTACK_RANGE.get());

        });
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(EntityInit.WIND_HAWK.get(), WindHawkEntity.createAttributes().build());
    }

    @Mod.EventBusSubscriber(modid = com.github.jowashere.blackclover.Main.MODID)
    public static class Main {

        @SubscribeEvent
        public static void serverStarting(final FMLServerStartingEvent event) {
            MagicAttributeCommand.register(event.getServer().getCommands().getDispatcher());
            MagicLevelCommand.register(event.getServer().getCommands().getDispatcher());
        }
    }
}

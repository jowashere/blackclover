package com.github.jowashere.blackclover.events;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.entities.summons.WindHawkEntity;
import com.github.jowashere.blackclover.init.EntityInit;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventsSubscriber {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(EntityInit.WIND_HAWK.get(), WindHawkEntity.createAttributes().build());
    }
}

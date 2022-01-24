package com.github.jowashere.blackclover.particles;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.init.ParticleInit;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleUtil {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticles(ParticleFactoryRegisterEvent event)
    {
        Minecraft.getInstance().particleEngine.register(ParticleInit.LIGHT_PARTICLE.get(), LightParticle.Factory::new);
    }
}

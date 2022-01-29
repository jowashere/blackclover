package com.github.jowashere.blackclover.particles;

import com.github.jowashere.blackclover.particles.light.LightParticleData;
import com.github.jowashere.blackclover.particles.light.LightParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class StartupCommonParticle {
    public static ParticleType<LightParticleData> lightParticleType;

    @SubscribeEvent
    public static void onIParticleTypeRegistration(RegistryEvent.Register<ParticleType<?>> iParticleTypeRegisterEvent) {
        lightParticleType = new LightParticleType();
        lightParticleType.setRegistryName("blackclover:light_particle");
        iParticleTypeRegisterEvent.getRegistry().register(lightParticleType);
    }
}

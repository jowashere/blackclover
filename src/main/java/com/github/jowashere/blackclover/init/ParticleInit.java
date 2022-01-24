package com.github.jowashere.blackclover.init;

import com.github.jowashere.blackclover.Main;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticleInit {


    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Main.MODID);

    //TODO add texture and play with it
    public static final RegistryObject<BasicParticleType> LIGHT_PARTICLE = PARTICLES.register("light_particle", () -> new BasicParticleType(true));
}

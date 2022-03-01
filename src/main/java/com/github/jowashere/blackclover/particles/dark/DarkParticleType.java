package com.github.jowashere.blackclover.particles.dark;

import com.mojang.serialization.Codec;
import net.minecraft.particles.ParticleType;

public class DarkParticleType extends ParticleType<DarkParticleData> {
    private static boolean ALWAYS_SHOW_REGARDLESS_OF_DISTANCE_FROM_PLAYER = false;
    public DarkParticleType() {
        super(ALWAYS_SHOW_REGARDLESS_OF_DISTANCE_FROM_PLAYER, DarkParticleData.DESERIALIZER);
    }

    // get the Codec used to
    // a) convert a LightParticleData to a serialised format
    // b) construct a LightParticleData object from the serialised format


    public Codec<DarkParticleData> codec() {
        return DarkParticleData.CODEC;
    }
}

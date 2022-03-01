package com.github.jowashere.blackclover.particles.light;

import com.mojang.serialization.Codec;
import net.minecraft.particles.ParticleType;

public class LightParticleType extends ParticleType<LightParticleData>
{
    private static boolean ALWAYS_SHOW_REGARDLESS_OF_DISTANCE_FROM_PLAYER = false;
    public LightParticleType() {
        super(ALWAYS_SHOW_REGARDLESS_OF_DISTANCE_FROM_PLAYER, LightParticleData.DESERIALIZER);
    }

    // get the Codec used to
    // a) convert a LightParticleData to a serialised format
    // b) construct a LightParticleData object from the serialised format


    public Codec<LightParticleData> codec() {
        return LightParticleData.CODEC;
    }
}

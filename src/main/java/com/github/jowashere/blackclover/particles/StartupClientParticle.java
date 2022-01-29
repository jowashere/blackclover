package com.github.jowashere.blackclover.particles;

import com.github.jowashere.blackclover.particles.light.LightParticleFactory;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class StartupClientParticle
{
    @SubscribeEvent
    public static void onParticleFactoryRegistration(ParticleFactoryRegisterEvent event) {

        // beware - there are two registerFactory methods with different signatures.
        // If you use the wrong one it will put Minecraft into an infinite loading loop with no console errors
        Minecraft.getInstance().particleEngine.register(StartupCommonParticle.lightParticleType, sprite -> new LightParticleFactory(sprite));
        //  This lambda may not be obvious: its purpose is:
        //  the registerFactory method creates an IAnimatedSprite, then passes it to the constructor of FlameParticleFactory

        //  General rule of thumb:
        // If you are creating a TextureParticle with a corresponding json to specify textures which will be stitched into the
        //    particle texture sheet, then use the 1-parameter constructor method
        // If you're supplying the render yourself, or using a texture from the block sheet, use the 0-parameter constructor method
        //   (examples are MobAppearanceParticle, DiggingParticle).  See ParticleManager::registerFactories for more.
    }
}

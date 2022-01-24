package com.github.jowashere.blackclover.particles;

import com.github.jowashere.blackclover.events.WorldEvents;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class LightParticle extends SpriteTexturedParticle {

    protected LightParticle(ClientWorld world, double xCord, double yCord, double zCord, double xSpeed, double ySpeed, double zSpeed)
    {
        super(world, xCord, yCord, zCord, xSpeed, ySpeed, zSpeed);

        float f = this.random.nextFloat() * 1.0f;
        this.rCol = f;
        this.gCol = f;
        this.bCol = f;

        this.setSize(0.02f, 0.02f);
        this.quadSize *= this.random.nextFloat() * 1.1F;
        this.xd *= (double) 0.02f;
        this.yd *= (double) 0.02f;
        this.zd *= (double) 0.02f;

        this.lifetime = (int) (20.0D / (Math.random() * 1.0D));
    }


    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.yd -= 0.04D * (double)this.gravity;
            this.move(this.xd, this.yd, this.zd);
            this.xd *= (double)0.98F;
            this.yd *= (double)0.98F;
            this.zd *= (double)0.98F;
            if (this.onGround) {
                this.xd *= (double)0.7F;
                this.zd *= (double)0.7F;
            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType>
    {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite sprite)
        {
            this.spriteSet = sprite;
        }


        @Override
        public Particle createParticle(BasicParticleType type, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
        {
            LightParticle lightParticle = new LightParticle(world, x, y, z, xSpeed, ySpeed, zSpeed);
            lightParticle.setColor(0f, 0f, 0f);
            lightParticle.pickSprite(this.spriteSet);
            return lightParticle;
        }
    }
}

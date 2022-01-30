package com.github.jowashere.blackclover.particles.dark;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.world.ClientWorld;

import java.awt.*;

public class DarkParticle extends SpriteTexturedParticle {
    private final IAnimatedSprite sprites;

    /**
     * Construct a new LightParticle at the given [x,y,z] position, with the given initial velocity, the given color, and the
     *   given diameter.
     *   We also supply sprites so that you can change the sprite texture in the tick() method (although not needed for this example)
     */
    public DarkParticle(ClientWorld world, double x, double y, double z,
                         double xd, double yd, double zd,
                         Color tint, double diameter,
                         IAnimatedSprite sprites)
    {
        super(world, x, y, z, xd, yd, zd);
        this.sprites = sprites;
        this.setSpriteFromAge(sprites);

        setColor(tint.getRed()/255.0F, tint.getGreen()/255.0F, tint.getBlue()/255.0F);
        setSize((float)diameter, (float)diameter);    // the size (width, height) of the collision box.

        final float PARTICLE_SCALE_FOR_ONE_METRE = 0.5F; //  if the particleScale is 0.5, the texture will be rendered as 1 metre high
        quadSize = PARTICLE_SCALE_FOR_ONE_METRE * (float)diameter; // sets the rendering size of the particle for a TexturedParticle.

        lifetime = 5;  // lifetime in ticks: 100 ticks = 5 seconds

        final float ALPHA_VALUE = 1.0F;
        this.alpha = ALPHA_VALUE;

        //the vanilla Particle constructor added random variation to our starting velocity.  Undo it!
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;

        this.hasPhysics = true;  // the move() method will check for collisions with scenery
    }



    // ---- methods used by TexturedParticle.renderParticle() method to find out how to render your particle
    //  the base method just renders a quad, rotated to directly face the player

    // can be used to change the skylight+blocklight brightness of the rendered Particle.
    @Override
    protected int getLightColor(float partialTick)
    {
        final int BLOCK_LIGHT = 15;  // maximum brightness
        final int SKY_LIGHT = 15;    // maximum brightness
        final int FULL_BRIGHTNESS_VALUE = LightTexture.pack(BLOCK_LIGHT, SKY_LIGHT);
        return FULL_BRIGHTNESS_VALUE;

        // if you want the brightness to be the local illumination (from block light and sky light) you can just use
        //  the Particle.getBrightnessForRender() base method, which contains:
        //    BlockPos blockPos = new BlockPos(this.posX, this.posY, this.posZ);
        //    return this.world.isBlockLoaded(blockPos) ? WorldRenderer.getCombinedLight(this.world, blockPos) : 0;
    }

    // Choose the appropriate render type for your particles:
    // There are several useful predefined types:
    // PARTICLE_SHEET_TRANSLUCENT semi-transparent (translucent) particles
    // PARTICLE_SHEET_OPAQUE    opaque particles
    // TERRAIN_SHEET            particles drawn from block or item textures
    // PARTICLE_SHEET_LIT       appears to be the same as OPAQUE.  Not sure of the difference.  In previous versions of minecraft,
    //                          "lit" particles changed brightness depending on world lighting i.e. block light + sky light
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    /**
     * call once per tick to update the Particle position, calculate collisions, remove when max lifetime is reached, etc
     */
    @Override
    public void tick()
    {
        // if you want to change the texture as the particle gets older, you can use
        // selectSpriteWithAge(sprites);

        this.xo = x;
        this.yo = y;
        this.zo = z;

        this.move(xd, yd, zd);  // simple linear motion.  You can change speed by changing motionX, motionY,
        // motionZ every tick.  For example - you can make the particle accelerate downwards due to gravity by
        // final double GRAVITY_ACCELERATION_PER_TICK = -0.02;
        // motionY += GRAVITY_ACCELERATION_PER_TICK;
        // calling move() also calculates collisions with other objects

        // collision with a block makes the ball disappear.  But does not collide with entities
        if (onGround) {  // onGround is only true if the particle collides while it is moving downwards...
            this.remove();
        }

        if (yo == y && yd > 0) {  // detect a collision while moving upwards (can't move up at all)
            this.remove();
        }

        if (this.age++ >= this.lifetime) {
            this.remove();
        }
    }

    // contains a list of textures; choose one using either
    // newParticle.selectSpriteRandomly(sprites); or newParticle.selectSpriteWithAge(sprites);
}

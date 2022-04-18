package com.github.jowashere.blackclover.spells.light;

import com.github.jowashere.blackclover.api.internal.AbstractToggleSpell;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.init.ItemInit;
import com.github.jowashere.blackclover.particles.dark.DarkParticleData;
import com.github.jowashere.blackclover.particles.light.LightParticleData;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnParticlePacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class LightSword extends AbstractToggleSpell {
    LightParticleData lightParticleData = new LightParticleData(new Color(255, 255, 0, 1), 0.1);

    public LightSword() {
        super("light_sword", AttributeInit.LIGHT);

        this.setSkillSpell(true);
        this.setManaCost(0.2F);
        this.setCooldown(30);
        this.setUnlockLevel(1);
        this.setUV(80, 32);

        this.action = this::action;

        this.onStartEvent = this::onStart;

    }

    public void action(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {
            ItemStack item = caster.getItemInHand(Hand.MAIN_HAND);
            if(item.getItem() instanceof SwordItem){

                double random = Math.random() * (0.3 - 0.1) + 0.1;
                double yawRightHandDirection = Math.toRadians(-1 * caster.yBodyRot- 45);
                double x = 0.5 * Math.sin(yawRightHandDirection) + caster.getX();
                double y = caster.getY() + 1.2 ;
                double z = 0.5 * Math.cos(yawRightHandDirection) + caster.getZ() ;
                IPacket<?> ipacket = new SSpawnParticlePacket(lightParticleData, true, x + random * Math.sin(yawRightHandDirection) * 0.5, caster.getY() + 0.65 + random, z + random * Math.cos(yawRightHandDirection) * 0.5, 0, 0.1f, 0, 0.01f, 2);
                IPacket<?> ipacket2 = new SSpawnParticlePacket(lightParticleData, true, x + 0.2 * random * Math.sin(yawRightHandDirection) * 0.5 ,  caster.getY() + 0.7 + random, z + 0.2 * random * Math.cos(yawRightHandDirection) * 0.5, 0, 0.1f, 0, 0.01f, 2);
                IPacket<?> ipacket3 = new SSpawnParticlePacket(lightParticleData, true, x + 0.3 * random * Math.sin(yawRightHandDirection) * 0.5 ,  caster.getY() + 0.75 + random, z + 3 * random * Math.cos(yawRightHandDirection) * 0.5, 0, 0.1f, 0, 0.01f, 2);
                IPacket<?> ipacket4 = new SSpawnParticlePacket(lightParticleData, true, x + 0.4 * random * Math.sin(yawRightHandDirection) * 0.5 ,  caster.getY() + 0.8 + random, z + 0.4 * random * Math.cos(yawRightHandDirection) * 0.5, 0, 0.1f, 0, 0.01f, 2);
                IPacket<?> ipacket5 = new SSpawnParticlePacket(lightParticleData, true, x + 0.5 * random * Math.sin(yawRightHandDirection) * 0.5 ,  caster.getY() + 0.85 + random, z + 0.5 * random * Math.cos(yawRightHandDirection) * 0.5, 0, 0.1f, 0, 0.01f, 2);


                for (int j = 0; j < caster.level.players().size(); ++j)
                {
                    ServerPlayerEntity player = (ServerPlayerEntity) caster.level.players().get(j);
                    BlockPos blockpos = new BlockPos(player.getX(), player.getY(), player.getZ());
                    if (blockpos.closerThan(new Vector3d(caster.getX(), caster.getY(), caster.getZ()), 512))
                    {
                        player.connection.send(ipacket);
                        player.connection.send(ipacket2);
                        player.connection.send(ipacket3);
                        player.connection.send(ipacket4);
                        player.connection.send(ipacket5);
                    }
                }
            }
        }
    }
    public void onStart(LivingEntity caster, float manaIn) {
        BCMHelper.GiveItem(caster, new ItemStack(ItemInit.LIGHT_SWORD.get()));
    }
}
